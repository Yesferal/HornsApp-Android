package com.yesferal.hornsapp.app.presentation.concert.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.util.load
import com.yesferal.hornsapp.app.util.setAllCornersRounded
import com.yesferal.hornsapp.app.util.setUpWith
import com.yesferal.hornsapp.app.util.tintWith
import com.yesferal.hornsapp.domain.entity.Concert
import kotlinx.android.synthetic.main.item_concert.view.*

class ConcertViewHolder constructor(
    itemView: View,
    private val listener: ConcertAdapter.Listener
) : RecyclerView.ViewHolder(itemView) {

    constructor(
        parent: ViewGroup,
        listener: ConcertAdapter.Listener
    ) : this(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_concert, parent, false),
        listener
    )

    fun bind(concert: Concert) {
        itemView.titleTextView.setUpWith(concert.name)
        itemView.timeTextView.setUpWith(concert.time)

        itemView.concertImageView.setAllCornersRounded(dp = 16)
        itemView.concertImageView.load(concert.posterImage)

        initFavoriteButtonFor(concert)
        initFacebookButton(concert)
        initTrailerButton(concert)

        itemView.containerView.setOnClickListener {
            listener.onConcertItemClick(concert)
        }

        itemView.facebookImageView.setOnClickListener {
            concert.facebookUrl?.let {
                listener.onFacebookButtonClick(it)
            }
        }

        itemView.youtubeImageView.setOnClickListener {
            concert.trailerUrl?.let {
                listener.onYoutubeButtonClick(it)
            }
        }

        itemView.favoriteImageView.setOnClickListener {
            concert.isFavorite = !concert.isFavorite
            initFavoriteButtonFor(concert)
            listener.onFavoriteButtonClick(concert)
        }
    }

    private fun initFavoriteButtonFor(concert: Concert) {
        if (concert.isFavorite) {
            itemView.favoriteImageView.setImageResource(R.drawable.ic_favorite_selected)
            itemView.favoriteImageView.tintWith(R.color.accent)
        } else {
            itemView.favoriteImageView.setImageResource(R.drawable.ic_favorite)
            itemView.favoriteImageView.tintWith(R.color.secondaryText)
        }
    }

    private fun initFacebookButton(concert: Concert) {
        concert.facebookUrl?.let {
            itemView.facebookImageView.visibility = View.VISIBLE
        }?: kotlin.run {
            itemView.facebookImageView.visibility = View.GONE
        }
    }

    private fun initTrailerButton(concert: Concert) {
        concert.trailerUrl?.let {
            itemView.youtubeImageView.visibility = View.VISIBLE
        }?: kotlin.run {
            itemView.youtubeImageView.visibility = View.GONE
        }
    }
}