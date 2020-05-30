package com.yesferal.hornsapp.app.presentation.concerts.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.domain.entity.Concert

class ConcertAdapter (
    private val listener: Listener
) : RecyclerView.Adapter<ConcertViewHolder>() {
    private var list: List<Concert> = listOf()

    interface Listener {
        fun onConcertItemClick(
            concert: Concert,
            concertImageView: ImageView)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConcertViewHolder {
        return ConcertViewHolder(parent, listener)
    }

    override fun onBindViewHolder(
        holder: ConcertViewHolder,
        position: Int
    ) {
        holder.bind(list[position])
    }

    fun setItem(list: List<Concert>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size
}