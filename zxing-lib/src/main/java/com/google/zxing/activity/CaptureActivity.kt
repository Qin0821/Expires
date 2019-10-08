package com.google.zxing.activity

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Vibrator
import android.text.TextUtils
import android.view.SurfaceHolder
import android.view.SurfaceHolder.Callback
import android.view.SurfaceView
import android.view.View
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.ChecksumException
import com.google.zxing.DecodeHintType
import com.google.zxing.FormatException
import com.google.zxing.NotFoundException
import com.google.zxing.R
import com.google.zxing.Result
import com.google.zxing.camera.CameraManager
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.decoding.CaptureActivityHandler
import com.google.zxing.decoding.InactivityTimer
import com.google.zxing.decoding.RGBLuminanceSource
import com.google.zxing.qrcode.QRCodeReader
import com.google.zxing.util.BitmapUtil
import com.google.zxing.util.Constant
import com.google.zxing.view.ViewfinderView
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.activity_scanner.*

import java.io.IOException
import java.util.Hashtable
import java.util.Vector


/**
 * Initial the camera
 *
 * @author Ryan.Tang
 */
class CaptureActivity : AppCompatActivity(), Callback {

    private var handler: CaptureActivityHandler? = null
    var viewfinderView: ViewfinderView? = null
        private set
    private var isFlashOn = false
    private var hasSurface: Boolean = false
    private var decodeFormats: Vector<BarcodeFormat>? = null
    private var characterSet: String? = null
    private var inactivityTimer: InactivityTimer? = null
    private var mediaPlayer: MediaPlayer? = null
    private var playBeep: Boolean = false
    private var vibrate: Boolean = false
    private var mProgress: ProgressDialog? = null
    private var scanBitmap: Bitmap? = null

    private val albumOnClick = View.OnClickListener {
        //打开手机中的相册
        val innerIntent = Intent(Intent.ACTION_GET_CONTENT) //"android.intent.action.GET_CONTENT"
        innerIntent.type = "image/*"
        startActivityForResult(innerIntent, REQUEST_CODE_SCAN_GALLERY)
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private val beepListener = OnCompletionListener { mediaPlayer -> mediaPlayer.seekTo(0) }

    /**
     * 闪光灯开关按钮
     */
    private val flashListener = View.OnClickListener {
        try {
            val isSuccess = CameraManager.get().setFlashLight(!isFlashOn)
            if (!isSuccess) {
                Toast.makeText(
                    this@CaptureActivity,
                    R.string.note_no_flashlight,
                    Toast.LENGTH_SHORT
                ).show()
                return@OnClickListener
            }
            isFlashOn = if (isFlashOn) {
                // 关闭闪光灯
                btFlash?.setImageResource(R.drawable.flash_off)
                tvFlash.setText(R.string.layout_turn_light_on)
                false
            } else {
                // 开启闪光灯
                btFlash?.setImageResource(R.drawable.flash_on)
                tvFlash.setText(R.string.layout_turn_light_off)
                true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        ImmersionBar.with(this)
            .transparentBar()
            .statusBarDarkFont(true)
            .init()

        CameraManager.init(application)
        viewfinderView = findViewById(R.id.vvFinder)
        ibBack.setOnClickListener { finish() }
        btFlash.setOnClickListener(flashListener)
        ibAlbum.setOnClickListener(albumOnClick)

        hasSurface = false
        inactivityTimer = InactivityTimer(this)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_SCAN_GALLERY -> handleAlbumPic(data!!)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * 处理选择的图片
     *
     * @param data
     */
    private fun handleAlbumPic(data: Intent) {
        //获取选中图片的路径
        val uri = data.data

        mProgress = ProgressDialog(this@CaptureActivity)
        mProgress!!.setMessage("正在扫描...")
        mProgress!!.setCancelable(false)
        mProgress!!.show()
        runOnUiThread {
            val result = scanningImage(uri)
            mProgress!!.dismiss()
            if (result != null) {
                val resultIntent = Intent()
                var bundle = intent.extras
                if (bundle == null) {
                    bundle = Bundle()
                }
                bundle.putString(Constant.INTENT_EXTRA_KEY_QR_SCAN, result.text)

                resultIntent.putExtras(bundle)
                this@CaptureActivity.setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(
                    this@CaptureActivity,
                    R.string.note_identify_failed,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    /**
     * 扫描二维码图片的方法
     *
     * @param uri
     * @return
     */
    fun scanningImage(uri: Uri?): Result? {
        if (uri == null) {
            return null
        }
        val hints = Hashtable<DecodeHintType, String>()
        hints[DecodeHintType.CHARACTER_SET] = "UTF8" //设置二维码内容的编码

        scanBitmap = BitmapUtil.decodeUri(this, uri, 500, 500)
        val source = RGBLuminanceSource(scanBitmap!!)
        val bitmap1 = BinaryBitmap(HybridBinarizer(source))
        val reader = QRCodeReader()
        try {
            return reader.decode(bitmap1, hints)
        } catch (e: NotFoundException) {
            e.printStackTrace()
        } catch (e: ChecksumException) {
            e.printStackTrace()
        } catch (e: FormatException) {
            e.printStackTrace()
        }

        return null
    }

    override fun onResume() {
        super.onResume()
        val surfaceView = findViewById<SurfaceView>(R.id.scanner_view)
        val surfaceHolder = surfaceView.holder
        if (hasSurface) {
            initCamera(surfaceHolder)
        } else {
            surfaceHolder.addCallback(this)
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
        }
        decodeFormats = null
        characterSet = null

        playBeep = true
        val audioService = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        if (audioService.ringerMode != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false
        }
        initBeepSound()
        vibrate = true

    }

    override fun onPause() {
        super.onPause()
        if (handler != null) {
            handler!!.quitSynchronously()
            handler = null
        }
        CameraManager.get().closeDriver()
    }

    override fun onDestroy() {
        inactivityTimer!!.shutdown()
        super.onDestroy()
    }

    /**
     * Handler scan result
     *
     * @param result
     * @param barcode
     */
    fun handleDecode(result: Result, barcode: Bitmap) {
        inactivityTimer!!.onActivity()
        playBeepSoundAndVibrate()
        val resultString = result.text
        if (TextUtils.isEmpty(resultString)) {
            Toast.makeText(this@CaptureActivity, R.string.note_scan_failed, Toast.LENGTH_SHORT)
                .show()
        } else {
            val resultIntent = Intent()
            var bundle = intent.extras
            if (bundle == null) {
                bundle = Bundle()
            }
            bundle.putString(Constant.INTENT_EXTRA_KEY_QR_SCAN, resultString)
            resultIntent.putExtras(bundle)
            this.setResult(Activity.RESULT_OK, resultIntent)
        }
        this@CaptureActivity.finish()
    }

    private fun initCamera(surfaceHolder: SurfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder)
        } catch (ioe: IOException) {
            return
        } catch (e: RuntimeException) {
            return
        }

        if (handler == null) {
            handler = CaptureActivityHandler(
                this, decodeFormats,
                characterSet
            )
        }
    }

    override fun surfaceChanged(
        holder: SurfaceHolder, format: Int, width: Int,
        height: Int
    ) {

    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        if (!hasSurface) {
            hasSurface = true
            initCamera(holder)
        }

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        hasSurface = false

    }

    fun getHandler(): Handler? {
        return handler
    }

    fun drawViewfinder() {
        viewfinderView!!.drawViewfinder()

    }

    private fun initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            volumeControlStream = AudioManager.STREAM_MUSIC
            mediaPlayer = MediaPlayer()
            mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mediaPlayer!!.setOnCompletionListener(beepListener)

            val file = resources.openRawResourceFd(
                R.raw.beep
            )
            try {
                mediaPlayer!!.setDataSource(
                    file.fileDescriptor,
                    file.startOffset, file.length
                )
                file.close()
                mediaPlayer!!.setVolume(BEEP_VOLUME, BEEP_VOLUME)
                mediaPlayer!!.prepare()
            } catch (e: IOException) {
                mediaPlayer = null
            }

        }
    }

    private fun playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer!!.start()
        }
        if (vibrate) {
            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(VIBRATE_DURATION)
        }
    }

    companion object {

        private val REQUEST_CODE_SCAN_GALLERY = 100
        private val BEEP_VOLUME = 0.10f

        private val VIBRATE_DURATION = 200L
    }
}