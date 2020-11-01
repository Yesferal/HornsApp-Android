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

    enum class Key(val value: Int) {
        TITLE(1),
        NEWEST(2),
        MAIN(3)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            Key.TITLE.value -> {
                NewestTitleViewHolder(parent)
            }
            Key.MAIN.value -> {
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
                Key.TITLE.value
            }
            is ConcertViewData -> {
                Key.MAIN.value
            }
            else -> {
                Key.NEWEST.value
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