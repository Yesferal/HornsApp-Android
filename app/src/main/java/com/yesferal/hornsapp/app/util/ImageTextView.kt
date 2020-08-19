package com.yesferal.hornsapp.app.util

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.yesferal.hornsapp.app.R
import kotlinx.android.synthetic.main.custom_image_text_view.view.*

class ImageTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        val inflater =  context
                .getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE
                ) as LayoutInflater
        inflater.inflate(R.layout.custom_image_text_view, this, true)
    }

    fun setImageView(@DrawableRes id: Int) {
        itemImageView.setImageResource(id)
    }

    fun setText(text: String?) {
        text?.let {
            visibility = View.VISIBLE
            itemTextView.text = text
        } ?: kotlin.run { visibility = View.GONE }

    }
}