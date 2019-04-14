package com.gg.compassproject.extensions

import android.view.View
import android.view.ViewPropertyAnimator

fun ViewPropertyAnimator.rotateClockwise(view: View, toDegree: Float): ViewPropertyAnimator {
    val actualRotation = view.rotation

    if (actualRotation in 0f..90f && toDegree in 270f..360f) {
        view.rotation = 360f + actualRotation
    } else if (actualRotation in 270f..360f && toDegree in 0f..90f) {
        view.rotation = actualRotation - 360f
    }

    return rotation(toDegree)
}