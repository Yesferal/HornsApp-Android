package com.yesferal.hornsapp.app.presentation.ui.filters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.ui.custom.setUpWith
import kotlinx.android.synthetic.main.item_filter.view.*

class CategoryViewHolder(
    itemView: View,
    private val listener: Listener
) : RecyclerView.ViewHolder(itemView) {

    interface Listener {
        fun onClick(textViewData: CategoryViewData)
    }

    private var selectedColor: ColorStateList? = null
    private var color: ColorStateList? = null

    constructor(
        parent: ViewGroup,
        listener: Listener
    ) : this(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_filter, parent, false),
        listener
    ) {
        selectedColor = ContextCompat.getColorStateList(parent.context, R.color.accent)
        color = ContextCompat.getColorStateList(parent.context, R.color.secondaryText)
    }

    fun bind(viewData: CategoryViewData) {
        itemView.nameTextView.setUpWith(viewData.name)

        if (viewData.isSelected) {
            itemView.nameTextView.backgroundTintList = selectedColor
        } else {
            itemView.nameTextView.backgroundTintList = color
        }

        itemView.containerLayout.setOnClickListener {
            listener.onClick(viewData)
        }
    }
}