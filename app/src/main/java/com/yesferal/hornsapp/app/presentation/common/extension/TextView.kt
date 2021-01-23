package com.yesferal.hornsapp.app.presentation.common.extension

import android.view.View
import android.widget.TextView

fun TextView.setUpWith(string: String?) {
    string?.let {
        this.visibility = View.VISIBLE
        this.text = it
    }?: kotlin.run {
        this.visibility = View.GONE
    }
}