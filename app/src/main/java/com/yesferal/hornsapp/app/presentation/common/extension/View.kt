package com.yesferal.hornsapp.app.presentation.common.extension

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View

fun View.fadeIn() {
    alpha = 0f
    visibility = View.VISIBLE

    animate()
        .alpha(1f)
        .setDuration(50)
        .setListener(null)
}

fun View.fadeOut() {
    animate()
        .alpha(0f)
        .setDuration(50)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                visibility = View.GONE
            }
        })
}