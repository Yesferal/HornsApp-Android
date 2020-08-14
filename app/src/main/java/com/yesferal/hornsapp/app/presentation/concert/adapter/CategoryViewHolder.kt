package com.yesferal.hornsapp.app.presentation.concert.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.util.load
import com.yesferal.hornsapp.app.util.setTopCornersRounded
import com.yesferal.hornsapp.domain.entity.Category
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryViewHolder (
    itemView: View,
    private val listener: CategoryAdapter.Listener
) : RecyclerView.ViewHolder(itemView)  {

    constructor(
        parent: ViewGroup,
        listener: CategoryAdapter.Listener
    ) : this(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_category, parent, false),
        listener
    )

    fun bind(category: Category) {
        itemView.categoryTextView.text = category.name
        itemView.categoryImageView.setTopCornersRounded(dp = 16)
        itemView.categoryImageView.load(category.imageUrl)

        itemView.containerView.setOnClickListener {
            listener.openCategory(category._id)
        }
    }
}