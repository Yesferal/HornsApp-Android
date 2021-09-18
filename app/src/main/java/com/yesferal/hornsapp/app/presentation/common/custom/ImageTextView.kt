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

class ImageTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var itemImageView: ImageView
    private var titleTextView: TextView
    private var subtitleTextView: TextView

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.custom_image_text_view, this, true)

        itemImageView = findViewById(R.id.itemImageView)
        titleTextView = findViewById(R.id.titleTextView)
        subtitleTextView = findViewById(R.id.subtitleTextView)

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
