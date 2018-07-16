package com.example.t_rex.facedetection3.core

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.graphics.YuvImage
import android.hardware.Camera
import java.io.ByteArrayOutputStream

interface CameraPreviewToBitmap {
    fun convertToBitmap(data: ByteArray, camera: Camera) : Bitmap
}

class CameraPreviewToBitmapImpl : CameraPreviewToBitmap {

    override fun convertToBitmap(data: ByteArray, camera: Camera) : Bitmap {
        val params = camera.parameters
        val out = ByteArrayOutputStream()
        val yuv = YuvImage(data, params.previewFormat, params.previewSize.width, params.previewSize.height, null).apply {
            compressToJpeg(Rect(0, 0, params.previewSize.width, params.previewSize.height), 90, out)
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