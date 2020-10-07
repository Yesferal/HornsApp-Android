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

        itemImageView.visibility = View.GONE
        subtitleTextView.visibility = View.GONE
    }

    fun setImageView(@DrawableRes id: Int) {
        itemImageView.visibility = View.VISIBLE
        itemImageView.setImageResource(id)
    }

    fun setText(title: String?) {
        title?.let {
            visibility = View.VISIBLE
            titleTextView.text = it
        } ?: kotlin.run { visibility = View.GONE }
    }

    fun setText(
        title: String?,
        description: String?
    ) {
        setText(title)
        description?.let {
            subtitleTextView.visibility = View.VISIBLE
            subtitleTextView.text = description
        } ?: kotlin.run {
            subtitleTextView.visibility = View.GONE
        }
    }
}