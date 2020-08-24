package com.yesferal.hornsapp.app.util

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs
import kotlin.math.max

class PageTransformation
    : ViewPager2.PageTransformer {
    override fun transformPage(
        page: View,
        position: Float
    ) {
        val absPos = abs(position)
        page.apply {
            translationY = absPos * 400f
            scaleX = 1f
            scaleY = 1f
        }

        when {
            position < -1 ->page.alpha = 0.1f
            position <= 1 -> {
                page.alpha = max(0.2f, 1 - abs(position))
            }
            else -> page.alpha = 0.1f
        }
    }
}