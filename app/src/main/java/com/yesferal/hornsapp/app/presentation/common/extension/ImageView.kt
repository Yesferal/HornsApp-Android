package com.yesferal.hornsapp.app.presentation.common.extension

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.load(url: String?) {
    if (url.isNullOrEmpty()) {
        return
    }

    Picasso.get()
        .load(url)
        .into(this)
}