package com.yesferal.hornsapp.app.presentation.common.extension

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.yesferal.hornsapp.app.R

fun TextView.setUpWith(string: String?) {
    string?.let {
        this.visibility = View.VISIBLE
        this.text = it
    }?: kotlin.run {
        this.visibility = View.GONE
    }
}

fun TextView.setUpCTA(label: String?, url: String?, cta: () -> Unit) {
    if (url.isNullOrEmpty()) {
        if(label.isNullOrEmpty()) {
            this.visibility = View.INVISIBLE
        } else {
            this.setUpWith(label)
            this.backgroundTintList = ContextCompat.getColorStateList(this.context, R.color.secondaryText)
        }
    } else {
        if(label.isNullOrEmpty()) {
            this.setUpWith(this.context.getString(R.string.buy_here))
        } else {
            this.setUpWith(label)
        }
        this.setOnClickListener {
            cta()
        }
    }
}
