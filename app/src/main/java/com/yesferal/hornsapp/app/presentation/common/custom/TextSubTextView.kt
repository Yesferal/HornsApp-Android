package com.yesferal.hornsapp.app.presentation.common.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.yesferal.hornsapp.app.R
import kotlinx.android.synthetic.main.custom_image_text_view.view.*

class TextSubTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        val inflater =  context
                .getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE
                ) as LayoutInflater
        inflater.inflate(R.layout.custom_text_sub_text_view, this, true)
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
            subtitleTextView.text = it
        } ?: kotlin.run {
            subtitleTextView.visibility = View.GONE
        }
    }
}