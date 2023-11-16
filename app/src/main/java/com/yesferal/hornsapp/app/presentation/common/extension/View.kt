package com.yesferal.hornsapp.app.presentation.common.extension

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.yesferal.hornsapp.app.framework.adMob.AbstractViewFactory
import com.yesferal.hornsapp.app.framework.adMob.AdUnitIds

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

fun ViewGroup.addBottomView(viewFactory: AbstractViewFactory, type: AdUnitIds.Type, size: Int) {
    removeAllViews()
    val viewBackground = View(this.context)
    viewBackground.layoutParams = LinearLayout.LayoutParams(width, parseIntToDp(size.toFloat()))
    addView(viewBackground)
    addView(viewFactory.drawView(context, type, size))
}

fun View.parseIntToDp(dp: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        resources.displayMetrics
    ).toInt()
}