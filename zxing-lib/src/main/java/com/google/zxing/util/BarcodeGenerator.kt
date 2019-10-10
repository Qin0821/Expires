package com.google.zxing.util

import com.google.zxing.WriterException
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Color.BLACK
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix
import android.opengl.ETC1.getWidth
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.util.HashMap
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth




object BarcodeGenerator {

    fun getBarcodeImage(data: String, width: Int, height: Int): Bitmap? {
        return if (data.isEmpty() || width <= 0 || height <= 0) {
            null
        } else try {
            val hintsMap = HashMap<EncodeHintType, Any>(3)
            hintsMap[EncodeHintType.CHARACTER_SET] = "utf-8"
            hintsMap[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
            hintsMap[EncodeHintType.MARGIN] = 0

            val bitMatrix =
                MultiFormatWriter().encode(data, BarcodeFormat.CODE_128, width, height, hintsMap)
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

            val pixels = IntArray(bitMatrix.width * bitMatrix.height)
            for (y in 0 until height) {
                val offset = y * width
                for (x in 0 until width) {
                    pixels[offset + x] = if (bitMatrix.get(x, y)) BLACK else 0
                }
            }
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height)

//            for (i in 0 until width) {
//                for (j in 0 until height) {
//                    bitmap.setPixel(i, j, if (bitMatrix.get(i, j)) Color.BLACK else Color.WHITE)
//                }
//            }
            bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }
}