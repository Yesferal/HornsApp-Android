package com.yesferal.hornsapp.app.presentation.ui.filters

import android.view.View
import androidx.core.content.ContextCompat
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.extension.setUpWith
import com.yesferal.hornsapp.domain.entity.CategoryKey
import com.yesferal.hornsapp.multitype.abstraction.DelegateListener
import com.yesferal.hornsapp.multitype.delegate.ViewDelegate
import kotlinx.android.synthetic.main.item_category.view.*

data class CategoryViewData(
    val categoryKey: CategoryKey,
    val name: String?,
    val isSelected: Boolean = false
) : ViewDelegate<CategoryViewData.Listener>() {

    interface Listener: DelegateListener {
        fun onClick(categoryViewData: CategoryViewData)
    }

    override fun onBindViewDelegate(view: View, listener: Listener) {
        view.nameTextView.setUpWith(name)

        if (isSelected) {
            val selectedColor = ContextCompat.getColorStateList(view.nameTextView.context, R.color.accent)
            view.nameTextView.setTextColor(selectedColor)
            view.nameTextView.backgroundTintList = selectedColor
        } else {
            val unselectedColor = ContextCompat.getColorStateList(view.nameTextView.context, R.color.secondaryText)
            view.nameTextView.setTextColor(unselectedColor)
            view.nameTextView.backgroundTintList = unselectedColor
        }

        view.containerLayout.setOnClickListener {
            listener.onClick(this)
        }
    }

    override val layout: Int
        get() = R.layout.item_category
}
