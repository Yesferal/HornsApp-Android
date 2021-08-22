package com.yesferal.hornsapp.app.presentation.ui.filters

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.extension.setUpWith
import com.yesferal.hornsapp.domain.entity.CategoryKey
import com.yesferal.hornsapp.multitype.abstraction.DelegateListener
import com.yesferal.hornsapp.multitype.delegate.ViewDelegate

data class CategoryViewData(
    val categoryKey: CategoryKey,
    val name: String?,
    val isSelected: Boolean = false
) : ViewDelegate<CategoryViewData.Listener>() {

    override val layout = R.layout.item_category

    interface Listener : DelegateListener {
        fun onClick(categoryViewData: CategoryViewData)
    }

    override fun onBindViewDelegate(view: View, listener: Listener) {
        val nameTextView = view.findViewById<TextView>(R.id.nameTextView)
        nameTextView.setUpWith(name)

        if (isSelected) {
            val selectedColor = ContextCompat.getColorStateList(view.context, R.color.accent)
            nameTextView.setTextColor(selectedColor)
            nameTextView.backgroundTintList = selectedColor
        } else {
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
