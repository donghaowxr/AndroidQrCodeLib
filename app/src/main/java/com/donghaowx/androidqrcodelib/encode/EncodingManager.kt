package com.donghaowx.androidqrcodelib.encode

import android.graphics.Bitmap
import android.graphics.Matrix
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.util.*

/**
 * @Author: donghao
 * @Date: 1/6/21 11:41 AM
 * @License: Copyright © 2021 Ysten
 * @Description:
 */
class EncodingManager {
    companion object {
        private const val UTF_8: String = "utf-8"
        private const val IMAGE_HALFWIDTH = 50

        /**
         * 创建常规二维码
         */
        @Throws(WriterException::class)
        fun createQRCode(str: String, size: Int, codeColor: Int): Bitmap {
            val hints = Hashtable<EncodeHintType, String>()
            hints[EncodeHintType.CHARACTER_SET] = UTF_8
            val matrix = MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, size, size, hints)
            val matWidth = matrix.width
            val matHeight = matrix.height
            val pixels = IntArray(matWidth * matHeight)
            for (y in 0 until matHeight) {
                for (x in 0 until matWidth) {
                    if (matrix[x, y]) {
                        pixels[y * matWidth + x] = codeColor
                    }
                }
            }
            val bitmap = Bitmap.createBitmap(matWidth, matHeight, Bitmap.Config.ARGB_8888)
            bitmap.setPixels(pixels, 0, matWidth, 0, 0, matWidth, matHeight)
            return bitmap
        }

        /**
         * 创建带logo的二维码
         */
        @Throws(WriterException::class)
        fun createQRCodeWithLogo(str: String, size: Int, codeColor: Int, mBitmap: Bitmap): Bitmap {
            val halfSize = size / 10
            val hints = Hashtable<EncodeHintType, Any>()
            hints[EncodeHintType.CHARACTER_SET] = UTF_8
            hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
            val bitMatrix = QRCodeWriter().encode(str, BarcodeFormat.QR_CODE, size, size, hints)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val halfW = width / 2
            val halfH = height / 2
            val matrix = Matrix()
            val sx = 2.toFloat() * IMAGE_HALFWIDTH / mBitmap.width
            val sy = 2.toFloat() * IMAGE_HALFWIDTH / mBitmap.height
            matrix.setScale(sx, sy)
            val bitmapScale = Bitmap.createBitmap(
                mBitmap, 0, 0,
                mBitmap.width, mBitmap.height, matrix, false
            )
            val pixels = IntArray(size * size)
            for (y in 0 until size) {
                for (x in 0 until size) {
                    if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH && y > halfH - IMAGE_HALFWIDTH && y < halfH + IMAGE_HALFWIDTH) {
                        //该位置用于存放图片信息
                        //记录图片每个像素信息
                        pixels[y * width + x] = bitmapScale.getPixel(
                            x - halfW
                                    + IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH
                        )
                    } else {
                        if (bitMatrix[x, y]) {
                            pixels[y * size + x] = codeColor
                        }
                    }
                }
            }
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
            return bitmap
        }
    }
}