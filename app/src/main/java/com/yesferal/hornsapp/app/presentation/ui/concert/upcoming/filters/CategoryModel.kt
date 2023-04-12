/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.filters

import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
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
        val bigMargin = 16F
        val dpWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            bigMargin,
            view.context?.resources?.displayMetrics
        ).toInt()

        val nameTextView = view.findViewById<TextView>(R.id.nameTextView)
        val crossImageView = view.findViewById<ImageView>(R.id.crossImageView)

        if (isSelected) {
            crossImageView.visibility = View.VISIBLE
            nameTextView.setPadding(dpWidth, nameTextView.paddingTop, 0, nameTextView.paddingBottom)
            nameTextView.setUpWith(name)
            val selectedColor = ContextCompat.getColorStateList(view.context, R.color.accent)
            nameTextView.setTextColor(selectedColor)
            view.backgroundTintList = selectedColor
        } else {
            crossImageView.visibility = View.GONE
            nameTextView.setPadding(dpWidth, nameTextView.paddingTop, dpWidth, nameTextView.paddingBottom)
            nameTextView.setUpWith(name)
            val unselectedColor =
                ContextCompat.getColorStateList(view.context, R.color.secondaryText)
            nameTextView.setTextColor(unselectedColor)
            view.backgroundTintList = unselectedColor
        }

        view.setOnClickListener {
            listener.onClick(this)
        }
    }
}
