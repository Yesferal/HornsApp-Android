package com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.filters

import android.text.Html
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.extension.setUpWith
import com.yesferal.hornsapp.delegate.abstraction.DelegateListener
import com.yesferal.hornsapp.delegate.delegate.InteractiveDelegate

data class CategoryViewData(
    val categoryKey: String,
    val name: String,
    val isSelected: Boolean = false
) : InteractiveDelegate<CategoryViewData.Listener> {

    override val layout = R.layout.item_category

    interface Listener : DelegateListener {
        fun onClick(categoryViewData: CategoryViewData)
    }

    override fun onBindViewDelegate(view: View, listener: Listener) {
        val nameTextView = view.findViewById<TextView>(R.id.nameTextView)

        if (isSelected) {
            val closeIconAsHtml = "&#10006"
            val closeIconAsString = Html.fromHtml(closeIconAsHtml, HtmlCompat.FROM_HTML_MODE_LEGACY)
            nameTextView.setUpWith("$name   $closeIconAsString")
            val selectedColor = ContextCompat.getColorStateList(view.context, R.color.accent)
            nameTextView.setTextColor(selectedColor)
            nameTextView.backgroundTintList = selectedColor
        } else {
            nameTextView.setUpWith(name)
            val unselectedColor =
                ContextCompat.getColorStateList(view.context, R.color.secondaryText)
            nameTextView.setTextColor(unselectedColor)
            nameTextView.backgroundTintList = unselectedColor
        }

        view.setOnClickListener {
            listener.onClick(this)
        }
    }
}
