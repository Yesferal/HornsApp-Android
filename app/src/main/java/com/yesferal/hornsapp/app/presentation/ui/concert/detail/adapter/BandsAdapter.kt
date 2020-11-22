package com.yesferal.hornsapp.app.presentation.ui.concert.detail.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.presentation.ui.concert.detail.BandViewData

class BandsAdapter (
    private val listener: Listener
) : RecyclerView.Adapter<BandViewHolder>() {
    private var list: List<BandViewData> = listOf()

    interface Listener {
        fun onClick(bandViewData: BandViewData)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BandViewHolder {
        return BandViewHolder(parent, listener)
    }

    fun setItem(list: List<BandViewData>?) {
        this.list = list?: listOf()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(
        holder: BandViewHolder,
        position: Int
    ) {
        holder.bind(list[position])
    }
}