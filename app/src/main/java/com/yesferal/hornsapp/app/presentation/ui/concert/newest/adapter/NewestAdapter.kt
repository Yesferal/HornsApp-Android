package com.yesferal.hornsapp.app.presentation.ui.concert.newest.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.presentation.common.TitleViewData
import com.yesferal.hornsapp.app.presentation.common.ViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.search.adapter.ConcertAdapter
import com.yesferal.hornsapp.app.presentation.ui.concert.search.adapter.ConcertViewHolder
import com.yesferal.hornsapp.app.presentation.ui.concert.search.ConcertViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.*

class NewestAdapter (
    private val listener: ConcertAdapter.Listener,
    private val list: MutableList<ViewData> = mutableListOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TITLE = 0
    private val UPCOMING = 1
    private val MAIN = 2

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            TITLE -> {
                NewestTitleViewHolder(parent)
            }
            MAIN -> {
                ConcertViewHolder(parent, listener)
            }
            else -> {
                NewestViewHolder(parent, listener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(list[position]) {
            is TitleViewData -> {
                TITLE
            }
            is ConcertViewData -> {
                MAIN
            }
            else -> {
                UPCOMING
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (val view = list[position]) {
            is TitleViewData -> {
                (holder as NewestTitleViewHolder).bind(view)
            }
            is ConcertViewData -> {
                (holder as ConcertViewHolder).bind(view)
            }
            is NewestViewData -> {
                (holder as NewestViewHolder).bind(view)
            }
        }
    }

    fun setItem(list: List<ViewData>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size
}