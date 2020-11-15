package com.yesferal.hornsapp.app.presentation.ui.filters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.ui.custom.RecyclerViewHorizontalDecorator
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.FiltersViewData
import kotlinx.android.synthetic.main.item_filters.view.*

class FiltersViewHolder(
    itemView: View,
    private val listener: Listener
) : RecyclerView.ViewHolder(itemView) {

    private val filtersAdapter by lazy {
        CategoriesAdapter(instanceConcertsAdapterListener())
    }

    interface Listener {
        fun onClick(categoryViewData: CategoryViewData)
    }

    constructor(
        parent: ViewGroup,
        listener: Listener
    ) : this(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_filters, parent, false),
        listener
    ) {
        val linearLayoutManager = LinearLayoutManager(
            parent.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        itemView.filtersRecyclerView.also {
            it.adapter = filtersAdapter
            it.layoutManager = linearLayoutManager
            it.addItemDecoration(RecyclerViewHorizontalDecorator(padding = 4))
        }
    }

    fun bind(viewData: FiltersViewData) {
        filtersAdapter.setItems(viewData.categories)
    }

    private fun instanceConcertsAdapterListener() =
        object : CategoryViewHolder.Listener {
            override fun onClick(textViewData: CategoryViewData) {
                listener.onClick(textViewData)
            }
        }
}