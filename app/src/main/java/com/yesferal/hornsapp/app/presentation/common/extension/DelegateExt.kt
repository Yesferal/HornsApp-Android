package com.yesferal.hornsapp.app.presentation.common.extension

import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.delegate.abstraction.Delegate
import com.yesferal.hornsapp.delegate.delegate.DividerDelegate

fun MutableList<Delegate>.addVerticalDivider(height: Int, background: Int = R.color.divider) {
    this.add(DividerDelegate(height = height, width = Int.MAX_VALUE, background = background))
}


fun MutableList<Delegate>.safeInsert(i: Int, delegate: Delegate) {
    if (i <= size) {
        this.add(i, delegate)
    }
}
