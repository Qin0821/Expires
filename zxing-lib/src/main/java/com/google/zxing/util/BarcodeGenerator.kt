package com.google.zxing.util

import com.google.zxing.WriterException
import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix
import android.opengl.ETC1.getWidth
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.util.HashMap


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
            for (i in 0 until width) {
                for (j in 0 until height) {
                    bitmap.setPixel(i, j, if (bitMatrix.get(i, j)) Color.BLACK else Color.WHITE)
                }
            }
            bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }
}