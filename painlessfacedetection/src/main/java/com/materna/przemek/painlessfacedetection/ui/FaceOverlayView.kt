package com.materna.przemek.painlessfacedetection.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.google.android.gms.vision.face.Face


class FaceOverlayView @JvmOverloads constructor(context: Context,
                                                attrs: AttributeSet? = null,
                                                defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    private lateinit var mBitmap: Bitmap
    private lateinit var mFaces: List<Face>

    private var mDrawingType = DrawingType.FACE_BOX

    internal fun init(bitmap: Bitmap,
             faces: List<Face>,
             drawingType: DrawingType = DrawingType.FACE_BOX) {

        this.mBitmap = bitmap
        this.mFaces = faces
        this.mDrawingType = drawingType
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (!this::mBitmap.isInitialized) {
            return
        }

        val scale = drawBitmap(canvas)
        when (mDrawingType) {
            DrawingType.FACE_BOX -> drawFaceBox(canvas, scale)
            DrawingType.FACE_LANDSCAPES -> drawFaceLandmarks(canvas, scale)
        }
    }

    private fun drawBitmap(canvas: Canvas): Double? {
        val vWidth = canvas.width.toDouble()
        val vHeight = canvas.height.toDouble()

        val iWidth = mBitmap.width.toDouble()
        val iHeight = mBitmap.height.toDouble()

        val scale = Math.min(vWidth / iWidth, vHeight / iHeight)
        val bounds = Rect(0, 0, (iWidth * scale).toInt(), (iHeight * scale).toInt())
        canvas.drawBitmap(mBitmap, null, bounds, null)
        return scale
    }


    private fun drawFaceBox(canvas: Canvas, scale: Double?) {
        if (scale == null) return

        val paint = Paint().apply {
            color = Color.GREEN
            style = Paint.Style.STROKE
            strokeWidth = 5.0f
        }

        var left: Float
        var top: Float
        var right: Float
        var bottom: Float

        for (i in 0 until mFaces.count()) {

            val face = mFaces[i]

            left = (face.position.x * scale).toFloat()
            top = (face.position.y * scale).toFloat()

            right = scale.toFloat() * (face.position.x + face.width)
            bottom = scale.toFloat() * (face.position.y + face.height)

            canvas.drawRect(left, top, right, bottom, paint)
        }
    }

    private fun drawFaceLandmarks(canvas: Canvas, scale: Double?) {
        if (scale == null) return

        val paint = Paint().apply {
            color = Color.GREEN
            style = Paint.Style.STROKE
            strokeWidth = 5.0f
        }

        for (i in 0 until mFaces.count()) {
            val face = mFaces[i]

            for (landmark in face.landmarks) {
                val cx = (landmark.position.x * scale).toFloat()
                val cy = (landmark.position.y * scale).toFloat()
                canvas.drawCircle(cx, cy, 10f, paint)
            }
        }
    }

}