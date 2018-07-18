package com.materna.przemek.painlessfacedetection.core.cameraStream

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.graphics.YuvImage
import java.io.ByteArrayOutputStream

interface CameraPreviewToBitmap {
    fun convertToBitmap(data: ByteArray, params: CameraPreviewParams): Bitmap
}

data class CameraPreviewParams(val previewFormat: Int,
                               val previewSizeWidth: Int,
                               val previewSizeHeight: Int)

class CameraPreviewToBitmapImpl : CameraPreviewToBitmap {

    override fun convertToBitmap(data: ByteArray, params: CameraPreviewParams): Bitmap {
        val out = ByteArrayOutputStream()

        YuvImage(data, params.previewFormat, params.previewSizeWidth, params.previewSizeHeight, null).apply {
            compressToJpeg(Rect(0, 0, params.previewSizeWidth, params.previewSizeHeight), 90, out)
        }

        val bytes = out.toByteArray()
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

        out.apply {
            flush()
            close()
        }

        return bitmap
    }

}