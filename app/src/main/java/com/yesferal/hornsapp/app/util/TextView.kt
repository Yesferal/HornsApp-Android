package com.yesferal.hornsapp.app.util

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