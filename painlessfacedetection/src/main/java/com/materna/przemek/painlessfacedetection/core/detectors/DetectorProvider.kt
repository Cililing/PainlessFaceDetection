package com.materna.przemek.painlessfacedetection.core.detectors

import android.content.Context
import com.google.android.gms.vision.face.FaceDetector

interface DetectorProvider {
    fun getDetector(): FaceDetector
}

class DetectorProviderImpl(private val context: Context): DetectorProvider {

    private val detectorInstance by lazy {
        FaceDetector.Builder(context)
                .setTrackingEnabled(true)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setMode(FaceDetector.ACCURATE_MODE)
                .build()
    }

    override fun getDetector(): FaceDetector {
        return detectorInstance
    }
}