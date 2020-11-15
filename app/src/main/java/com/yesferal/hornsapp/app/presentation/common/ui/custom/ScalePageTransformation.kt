package com.yesferal.hornsapp.app.presentation.common.ui.custom

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class ScalePageTransformation
    : ViewPager2.PageTransformer {
    override fun transformPage(
        page: View,
        position: Float
    ) {
        val r = 1 - kotlin.math.abs(position)
        page.scaleY = 0.85F + r * 0.15F
    }
}