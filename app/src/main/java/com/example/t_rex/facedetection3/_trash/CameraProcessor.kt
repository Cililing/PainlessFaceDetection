package com.example.t_rex.facedetection3._trash

import android.graphics.ImageFormat
import android.hardware.camera2.*
import android.media.ImageReader
import android.util.Size
import android.view.Surface

class CameraProcessor(private val cameraManager: CameraManager,
                      private val cameraDevice: CameraDevice,
                      private val orientation: Int,
                      private var width: Int = 720,
                      private var height: Int = 320) {

    fun init() {

        val characteristics = cameraManager.getCameraCharacteristics(cameraDevice.id)
        var size: Array<Size>? = null

        if (characteristics != null) {
            size = characteristics
                    .get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
                    .getOutputSizes(ImageFormat.JPEG)
        }

        if (size != null && size.count() > 0) {
            width = size[0].width
            height = size[0].height
        }

        val reader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 1)
        val outputSurfaces = mutableListOf<Surface>()

        outputSurfaces.apply {
            add(reader.surface)
        }

        val captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE).apply {
            addTarget(reader.surface)
            set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)
            set(CaptureRequest.JPEG_ORIENTATION, orientation)
        }
    }
}