package com.yesferal.hornsapp.app.presentation.common.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.yesferal.hornsapp.app.R

class TextSubTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var titleTextView: TextView
    private var subtitleTextView: TextView

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.custom_text_sub_text_view, this, true)

        titleTextView = findViewById(R.id.titleTextView)
        subtitleTextView = findViewById(R.id.subtitleTextView)
    }

    private fun setText(title: String?) {
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
