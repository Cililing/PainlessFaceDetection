package com.example.t_rex.facedetection3.core

import android.util.Log
import com.google.android.gms.vision.face.Face

fun logFace(face: Face) {
    Log.d("Faces", "#Face ${face.id} detected at position ${face.position}#\n" +
            "smiling:\t${face.isSmilingProbability}\n" +
            "leftEyeOpen:\t${face.isLeftEyeOpenProbability}\n" +
            "rightEyeOpen:\t${face.isRightEyeOpenProbability}\n" +
            "eulerY:\tn${face.eulerY}\n" +
            "eulerZ:\t${face.eulerZ}\n")
}

fun logFace(face: Face, logger: (String, String) -> Unit) {
    logger.invoke("Faces", "#Face ${face.id} detected at position ${face.position}#\n" +
            "smiling:\t${face.isSmilingProbability}\n" +
            "leftEyeOpen:\t${face.isLeftEyeOpenProbability}\n" +
            "rightEyeOpen:\t${face.isRightEyeOpenProbability}\n" +
            "eulerY:\tn${face.eulerY}\n" +
            "eulerZ:\t${face.eulerZ}\n")
}