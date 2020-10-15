package com.yesferal.hornsapp.app.presentation.ui.concert.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.custom.setUpWith
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

    fun bind(selectedCategoryId: String, category: Category) {
        itemView.titleTextView.setUpWith(category.name)
        itemView.titleSelectedTextView.setUpWith(category.name)

        if (category._id == selectedCategoryId) {
            itemView.titleTextView.visibility = View.GONE
            itemView.titleSelectedTextView.visibility = View.VISIBLE
            itemView.dotSelectedView.visibility = View.VISIBLE
        } else {
            itemView.titleTextView.visibility = View.VISIBLE
            itemView.titleSelectedTextView.visibility = View.INVISIBLE
            itemView.dotSelectedView.visibility = View.INVISIBLE
        }

        itemView.titleTextView.setOnClickListener {
            listener.onClick(category)
        }
    }
}