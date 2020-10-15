package com.yesferal.hornsapp.app.presentation.common.custom

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.yesferal.hornsapp.app.R

class CheckBoxImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    var isChecked: Boolean = false
        set(value) {
            if (value) {
                setImageResource(R.drawable.ic_favorite_selected)
                tintWith(R.color.accent)
            } else {
                setImageResource(R.drawable.ic_favorite)
                tintWith(R.color.primaryText)
            }
            field = value
        }

    fun setOnCheckedChangeListener(func: (Boolean) -> Unit) {
        setOnClickListener {
            isChecked = !isChecked
            func(isChecked)
        }
    }
}