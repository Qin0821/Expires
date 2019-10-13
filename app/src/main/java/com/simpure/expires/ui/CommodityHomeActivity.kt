package com.simpure.expires.ui

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.simpure.expires.data.*
import com.simpure.expires.databinding.ActivityHomeBinding
import androidx.recyclerview.widget.LinearLayoutManager


import com.google.zxing.util.Constant.REQ_QR_CODE
import com.google.zxing.activity.CaptureActivity
import android.content.Intent
import android.graphics.Rect
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.orhanobut.dialogplus.DialogPlus
import com.simpure.expires.api.SignInApiService
import com.simpure.expires.data.entity.UserEntity
import com.simpure.expires.ui.commodity.CommodityAdapter
import com.simpure.expires.ui.commodity.CommodityHolder
import com.simpure.expires.utilities.toast
import com.simpure.expires.viewmodel.UserViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import android.util.Log
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.lifecycle.*
import com.google.zxing.util.BarcodeGenerator
import com.simpure.expires.BR
import com.simpure.expires.R
import com.simpure.expires.data.entity.CommodityEntity
import com.simpure.expires.ui.commodity.InventoryAdapter
import com.simpure.expires.viewmodel.CommodityDetailViewModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.dialog_commodity.view.*
import kotlinx.android.synthetic.main.item_dialog_commodity_consuming.view.*
import kotlin.concurrent.thread


class CommodityHomeActivity : BaseActivity() {


    override fun initData() {

    }

    override fun initView() {

    }

    private lateinit var mBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        initPlaceNameList()

        initCommodityList(savedInstanceState)

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        initBottomSheet()
    }


    private fun executeAnimation(height: Int, llt: LinearLayout) {
        if (height < 0) return
        var p: Int = 0
        var s: Int = 0
        if (llt.height != 0) {
            p = height
            s = 0
        } else {
            s = height
            p = 0
        }

        val lp = llt.layoutParams as LinearLayout.LayoutParams
        val animator = ValueAnimator.ofInt(p, s)

        // 此方法会随用户的点击而调用, 所以不要用内部类的形式创建, 我这里只是节省代码量, 增加阅读性(捂脸)
        animator.addUpdateListener { animation ->
            val animatedValue: Int = animation.animatedValue as Int
            lp.height = animatedValue
            llt.layoutParams = lp
        }

        animator.duration = 500
        animator.start()
    }

    var lastX = 0f
    var lastY = 0f

    private fun initBottomSheet() {
        if (!::bottomSheetBehavior.isInitialized) {
            viewShadow.setOnClickListener {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                viewShadow.visibility = View.GONE
            }
            bottomSheetBehavior = BottomSheetBehavior.from(itemCommodity)
            bottomSheetBehavior.setBottomSheetCallback(object :
                BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    setCommodityHeight(slideOffset)
                }

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    // Check Logs to see how bottom sheets behaves
                    when (newState) {
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            Log.e(
                                "Bottom Sheet Behaviour",
                                "STATE_COLLAPSED"
                            )
                            viewShadow.visibility = View.VISIBLE

                            if (collapsedHeight == 0) {
                                val rect = Rect()
                                itemCommodity.getLocalVisibleRect(rect)
                                collapsedHeight = rect.bottom
                            }
                            setCommodityHeight(0f)

                            itemCommodity.itemConsuming.layoutConsume.visibility = View.GONE
                        }
                        BottomSheetBehavior.STATE_DRAGGING -> {
                            Log.e(
                                "Bottom Sheet Behaviour",
                                "STATE_DRAGGING"
                            )

                            itemCommodity.itemConsuming.layoutConsume.visibility = View.VISIBLE

//                                val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1.0f)

                            viewShadow.visibility = View.VISIBLE

                        }
                        BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                            Log.e(
                                "Bottom Sheet Behaviour",
                                "STATE_HALF_EXPANDED"
                            )
                        }
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            Log.e(
                                "Bottom Sheet Behaviour",
                                "STATE_EXPANDED"
                            )
                            viewShadow.visibility = View.VISIBLE

                            viewShadow.visibility = View.VISIBLE
//                            val lp = LinearLayout.LayoutParams(
//                                LinearLayout.LayoutParams.MATCH_PARENT,
//                                0,
//                                1.0f
//                            )
//                            itemCommodity.llCommDetail.layoutParams = lp


                        }
                        BottomSheetBehavior.STATE_HIDDEN -> {
                            Log.e(
                                "Bottom Sheet Behaviour",
                                "STATE_HIDDEN"
                            )
                            viewShadow.visibility = View.GONE
                        }
                        BottomSheetBehavior.STATE_SETTLING -> {
                            Log.e(
                                "Bottom Sheet Behaviour",
                                "STATE_SETTLING"
                            )
//                            itemCommodity.itemConsuming.vsConsumingTitle.visibility = View.GONE
                            viewShadow.visibility = View.VISIBLE
                        }
                    }
                }

            })
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

            /*itemCommodity.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        lastX = event.x
                        lastY = event.y
                    }
                    MotionEvent.ACTION_MOVE -> {

                        val yDistance = event.y - lastY

                        Log.e(javaClass.simpleName, itemCommodity.height.toString())

//                        val dx = event.rawX - lastX
//                        val dy = event.rawY - lastY
//
//                        val left = (v.left + dx).toInt()
//                        val top = (v.top + dy).toInt()
//                        val right = (v.right + dx).toInt()
//                        val bottom = (v.bottom + dy).toInt()
//
//                        v.layout(left, top, right, bottom)
//                        lastX = event.rawX
//                        lastY = event.rawY

//                        val localRect = Rect()
//                        v.getLocalVisibleRect(localRect)
//
//                        val h = localRect.bottom - ConvertUtils.dp2px(224f)
                        setCommodityHeight(yDistance)

                        *//*val imgRect = Rect();
                        val focusItemParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        selected.getGlobalVisibleRect(imgRect);

                        focusItemParams.leftMargin = imgRect.left;
                        focusItemParams.topMargin = imgRect.top;
                        focusItemParams.width = imgRect.width();
                        focusItemParams.height = imgRect.height();
                        selected.getLocationInWindow(viewPosition);
                        focusView.setLayoutParams(focusItemParams);//focusView为你需要设置位置的VIEW*//*
                    }
                    MotionEvent.ACTION_UP -> {
                    }
                    else -> {

                    }
                }
                return@setOnTouchListener true
            }*/
        }
        viewModel = ViewModelProvider(this).get(CommodityDetailViewModel::class.java)

        viewModel.commodityDetail.observe(this, Observer {
            if (null == it) return@Observer

            mBinding.setVariable(BR.commodityDetail, it)

            showInventories(it)
            showBarcode(it)
        })

    }

    var collapsedHeight = 0

    private fun setCommodityHeight(slideOffset: Float) {
        if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) return

        val extensibleHeight = itemCommodity.height - collapsedHeight

        /*val minHeight = ConvertUtils.dp2px(100f)
        val height =
            if (slideOffset > 0) {
                (slideOffset * extensibleHeight).toInt() + minHeight
            } else minHeight
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            height
        )
        itemCommodity.llCommDetail.layoutParams = lp*/


        val marginBottomHeight = if (slideOffset > 0) {
            ((1 - slideOffset) * extensibleHeight).toInt()
        } else extensibleHeight
        val lp = RelativeLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        lp.setMargins(0, 0, 0, marginBottomHeight + 40)
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        itemCommodity.itemNavigation.layoutParams = lp

        Log.e(javaClass.simpleName, "h: $marginBottomHeight")
//        Log.e(javaClass.simpleName, "itemCommodity.height: ${itemCommodity.height}")
        val rect = Rect()
        itemCommodity.getLocalVisibleRect(rect)
        Log.e(javaClass.simpleName, "rect: $rect")
        Log.e(javaClass.simpleName, "slideOffset: $slideOffset")
    }

    override fun onStart() {
        super.onStart()
        initViewModel()
    }

    val userId = 1398762
    private val mObservableUsers: MediatorLiveData<List<UserEntity>> = MediatorLiveData()
    private fun initViewModel() {
        val viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        subscribeUi(viewModel.commodities)
    }

    private fun subscribeUi(liveData: LiveData<List<UserEntity>>) {
        liveData.observe(this,
            Observer { user ->
                if (!user.isNullOrEmpty()) {
                    toast(user[0].name)
                } else {
                    toast("user is empty")
                }
            })
    }


    private fun initCommodityList(savedInstanceState: Bundle?) {

        if (savedInstanceState == null) {
            val fragment = PlaceFragment()

            supportFragmentManager.beginTransaction()
                .add(R.id.fcCommodity, fragment, fragment.TAG).commit()
        }
    }

    private fun showInventories(it: CommodityEntity) {
        if (!it.inventories.isNullOrEmpty()) {
            with(mBinding.itemCommodity.itemInventories.rvInventories) {
                if (null == layoutManager)
                    layoutManager = LinearLayoutManager(this@CommodityHomeActivity)
                if (null == adapter)
                    adapter = InventoryAdapter()

                (adapter as InventoryAdapter).setInventoryList(it.inventories)
            }
        }
    }

    private fun showBarcode(it: CommodityEntity) {
        if (it.barcode.isNotEmpty()) {
            thread {
                with(mBinding.itemCommodity.itemBarcode.ivBarcode) {
                    val barcodeBitmap =
                        BarcodeGenerator.getBarcodeImage(it.barcode, this.width, this.height)
                    runOnUiThread {
                        // 上面的width和height会莫名其妙变成0导致这里报错
                        // 先不处理
                        // 如果app在其他地方必须用到sd卡读取权限
                        // 这里做sd卡的缓存
                        // 如果没有必要的sd卡权限，这里做成存数据库
                        // http://ddrv.cn/a/119750
//                        if (null == barcodeBitmap) {
//                            parent!!.context.toast("条形码解析失败")
//                        }
                        this.setImageBitmap(barcodeBitmap)
                    }
                }
            }

        }
    }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var viewModel: CommodityDetailViewModel

    /** Shows the product detail fragment  */
    fun showCommodityDetail(commodityId: Int, sheetVersion: Boolean = true) {

        if (sheetVersion) {
            viewModel.setCommodityId(commodityId)
            bottomSheetBehavior.state = when (bottomSheetBehavior.state) {
                BottomSheetBehavior.STATE_HIDDEN -> {
                    BottomSheetBehavior.STATE_COLLAPSED
                }
                BottomSheetBehavior.STATE_COLLAPSED or BottomSheetBehavior.STATE_EXPANDED -> BottomSheetBehavior.STATE_HIDDEN
                else -> BottomSheetBehavior.STATE_COLLAPSED
            }
        } else {
            val adapter = CommodityAdapter(this, commodityId)

            val dialog = DialogPlus.newDialog(this)
                .setAdapter(adapter)
                .setContentHolder(CommodityHolder())
                .setOnClickListener { dialog, view -> }
                .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                .create()
            dialog.show()
        }
    }

    private fun initPlaceNameList() {
        /**
         * 1. 获取place列表
         * 2. 根据placeList创建CommodityListFragment
         */
        val fridgeList = arrayListOf(
            Commodity(10L, "薯条", 10, "根", 1),
            Commodity(11L, "雪碧", 2, "瓶", 1),
            Commodity(12L, "鸡腿", 2, "根", 1),
            Commodity(13L, "排骨", 1, "斤", 1),
            Commodity(14L, "酸奶", 2, "瓶", 1),
            Commodity(15L, "西瓜", 1, "个", 1)
        )

        val makeUpsList = arrayListOf(
            Commodity(20L, "dior", 2, "支", 2),
            Commodity(21L, "乳液", 1, "瓶", 2),
            Commodity(22L, "爽肤水", 1, "瓶", 2),
            Commodity(23L, "项链", 2, "个", 2),
            Commodity(24L, "手链", 1, "个", 2),
            Commodity(25L, "tf", 1, "支", 2)
        )

        val otherList = arrayListOf(
            Commodity(30L, "dior", 2, "支", 3),
            Commodity(31L, "乳液", 1, "瓶", 3),
            Commodity(32L, "爽肤水", 1, "瓶", 3),
            Commodity(33L, "项链", 2, "个", 3),
            Commodity(34L, "手链", 1, "个", 3),
            Commodity(35L, "tf", 1, "支", 3)
        )

        val commodityList = fridgeList + makeUpsList + otherList

        val placeList = arrayListOf(
            Place("All", commodityList, 0),
            Place("Fridge", fridgeList, 1),
            Place("makeUpsList", makeUpsList, 2),
            Place("Snacks", otherList, 3),
            Place("Icebox", otherList, 4),
            Place("Tea table", otherList, 5),
            Place("Night table", otherList, 6),
            Place("Chest", otherList, 7),
            Place("Safe", otherList, 8),
            Place("Mirror cabinet", otherList, 9),
            Place("Shoe rack", otherList, 10)
        )
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        mBinding.rvLabels.layoutManager = layoutManager
        mBinding.rvLabels.adapter = PlaceNameAdapter(this, placeList)
    }


    fun goInventories(view: View) {
        PermissionUtils.permission(PermissionConstants.CAMERA)
            .rationale { shouldRequest -> ToastUtils.showShort(shouldRequest.toString()) }
            .callback(object : PermissionUtils.FullCallback {
                override fun onGranted(permissionsGranted: List<String>) {
                    LogUtils.d(permissionsGranted)
                    val intent = Intent(this@CommodityHomeActivity, CaptureActivity::class.java)
                    startActivityForResult(intent, REQ_QR_CODE)
                }

                override fun onDenied(
                    permissionsDeniedForever: List<String>,
                    permissionsDenied: List<String>
                ) {
                    LogUtils.d(permissionsDeniedForever, permissionsDenied)
                    if (permissionsDeniedForever.isNotEmpty()) {
                        ToastUtils.showShort("showOpenAppSettingDialog")
                        return
                    }
                    ToastUtils.showShort("requestCamera")
                }
            })
            .theme { activity -> ScreenUtils.setFullScreen(activity) }
            .request()

    }

    val signInApiService by lazy {
        SignInApiService.create()
    }
    var disposable: Disposable? = null

    fun goConsuming(view: View) {
        disposable =
            signInApiService.scanQRCode(50)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        this.toast(result.toString())
                    },
                    { error ->
                        this.toast(error.toString())
                    }
                )

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 扫描结果回调
        if (requestCode == REQ_QR_CODE && resultCode == RESULT_OK) {
//            val bundle = data?.extras
//            val scanResult = bundle?.getString(Constant.INTENT_EXTRA_KEY_QR_SCAN)
//            mBinding.btInventories.text = scanResult
        }
    }
}
