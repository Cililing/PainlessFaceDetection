package com.example.t_rex.facedetection3.core

import android.graphics.Bitmap
import com.example.t_rex.facedetection3.ui.DrawingType
import com.example.t_rex.facedetection3.ui.FaceOverlayView
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.face.Face
import org.jetbrains.anko.collections.asSequence

interface Detector {
    fun detect(bitmap: Bitmap, view: FaceOverlayView? = null, drawingType: DrawingType = DrawingType.FACE_LANDSCAPES): List<Face>?
}

class DetectorImpl(private val detectorProvider: DetectorProvider) : Detector {

    private val detector by lazy {
        detectorProvider.getDetector()
    }

    override fun detect(bitmap: Bitmap,
                        view: FaceOverlayView?,
                        drawingType: DrawingType): List<Face>? {

        if (detector.isOperational) {
            val frame = Frame.Builder().setBitmap(bitmap).build()
            val faces = detector.detect(frame)
            detector.release()

            return faces.asSequence().toList().apply {
                if (view != null) setSurface(bitmap, this, view, drawingType)
            }
        }

        return null
    }

    private fun setSurface(bitmap: Bitmap,
                           faces: List<Face>,
                           view: FaceOverlayView,
                           drawingType: DrawingType) {
        view.init(bitmap, faces, drawingType)
    }
}