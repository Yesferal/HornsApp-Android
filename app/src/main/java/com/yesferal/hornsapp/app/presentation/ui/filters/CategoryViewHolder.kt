package com.yesferal.hornsapp.app.presentation.ui.filters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.ui.custom.setUpWith
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryViewHolder(
    itemView: View,
    private val listener: Listener
) : RecyclerView.ViewHolder(itemView) {

    interface Listener {
        fun onClick(textViewData: CategoryViewData)
    }

    constructor(
        parent: ViewGroup,
        listener: Listener
    ) : this(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_category, parent, false),
        listener
    )

    fun bind(viewData: CategoryViewData) {
        itemView.nameTextView.setUpWith(viewData.name)

        if (viewData.isSelected) {
            val selectedColor = ContextCompat.getColorStateList(itemView.nameTextView.context, R.color.accent)
            itemView.nameTextView.setTextColor(selectedColor)
            itemView.nameTextView.backgroundTintList = selectedColor
        } else {
            val unselectedColor = ContextCompat.getColorStateList(itemView.nameTextView.context, R.color.secondaryText)
            itemView.nameTextView.setTextColor(unselectedColor)
            itemView.nameTextView.backgroundTintList = unselectedColor
        }

        itemView.containerLayout.setOnClickListener {
            listener.onClick(viewData)
        }
    }
}