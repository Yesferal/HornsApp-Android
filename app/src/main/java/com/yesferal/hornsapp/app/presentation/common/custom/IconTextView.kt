/* Copyright © 2025 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.common.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.extension.setUpWith
import com.yesferal.hornsapp.app.presentation.common.extension.tintWithColor

class IconTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var iconImageView: ImageView
    private var iconTextView: TextView

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.custom_icon_text_view, this, true)

        iconImageView = findViewById(R.id.iconImageView)
        iconTextView = findViewById(R.id.iconTextView)
    }

    fun setImageView(@DrawableRes id: Int) {
        iconImageView.visibility = View.VISIBLE
        iconImageView.setImageResource(id)
    }

    fun setText(
        title: String?
    ) {
        iconTextView.setUpWith(title)
    }

    fun setTint(color: Int) {
        iconImageView.tintWithColor(color)
        iconTextView.setTextColor(color)
    }
}
