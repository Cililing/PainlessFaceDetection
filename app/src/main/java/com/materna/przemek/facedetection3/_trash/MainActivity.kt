package com.materna.przemek.facedetection3._trash

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.materna.przemek.painlessfacedetection.core.detectors.DetectorImpl
import com.materna.przemek.painlessfacedetection.core.detectors.DetectorProviderImpl
import com.materna.przemek.facedetection3.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bitmap = resources.openRawResource(R.raw.face).let { BitmapFactory.decodeStream(it) }

        val detectorProvider = DetectorProviderImpl(this)
        val detector = DetectorImpl(detectorProvider)

        doAsync {
            runOnUiThread {
                detector.detect(bitmap, face_overlay)
            }
        }

    }
}
