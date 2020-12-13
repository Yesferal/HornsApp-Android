package com.yesferal.hornsapp.app.presentation.ui.filters

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.yesferal.hornsapp.app.presentation.common.custom.RecyclerViewHorizontalDecorator
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.FiltersViewData
import com.yesferal.hornsapp.multitype.BaseViewHolder
import kotlinx.android.synthetic.main.item_filters.view.*

class FiltersViewHolder(
    itemView: View,
    private val listener: FiltersViewData.Listener
) : BaseViewHolder<FiltersViewData>(itemView) {

    private val filtersAdapter by lazy {
        CategoriesAdapter(instanceConcertsAdapterListener())
    }

    init {
        val linearLayoutManager = LinearLayoutManager(
            itemView.filtersRecyclerView.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        itemView.filtersRecyclerView.also {
            it.adapter = filtersAdapter
            it.layoutManager = linearLayoutManager
            it.addItemDecoration(RecyclerViewHorizontalDecorator(padding = 4))
        }
    }

    override fun bind(model: FiltersViewData) {
        filtersAdapter.setItems(model.categories)
    }

    private fun instanceConcertsAdapterListener() =
        object : CategoryViewHolder.Listener {
            override fun onClick(textViewData: CategoryViewData) {
                listener.onClick(textViewData)
            }
        }
}