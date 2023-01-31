package com.yesferal.hornsapp.app.presentation.ui.concert.newest

import android.view.View
import android.widget.TextView
import com.google.android.material.imageview.ShapeableImageView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.Parcelable
import com.yesferal.hornsapp.app.presentation.common.base.ParcelableViewData
import com.yesferal.hornsapp.app.presentation.common.extension.load
import com.yesferal.hornsapp.app.presentation.common.extension.setUpCTA
import com.yesferal.hornsapp.app.presentation.common.extension.setUpWith
import com.yesferal.hornsapp.delegate.abstraction.DelegateListener
import com.yesferal.hornsapp.delegate.delegate.InteractiveDelegate
import com.yesferal.hornsapp.delegate.delegate.NonInteractiveDelegate

data class TitleViewData(
    val title: String?,
    val subtitle: String?
) : NonInteractiveDelegate {

    override val layout = R.layout.item_newest_title

    override fun onBindViewDelegate(view: View) {
        view.findViewById<TextView>(R.id.titleTextView).setUpWith(title)
        view.findViewById<TextView>(R.id.subtitleTextView).setUpWith(subtitle)
    }
}

data class HomeCardViewData(
    val title: String?,
    val subtitle: String?
) : NonInteractiveDelegate {

    override val layout = R.layout.item_home_card

    override fun onBindViewDelegate(view: View) {
        view.findViewById<TextView>(R.id.titleTextView).setUpWith(title)
        view.findViewById<TextView>(R.id.subtitleTextView).setUpWith(subtitle)
    }
}

data class CarouselViewData(
    val id: String,
    val name: String?,
    val image: String?,
    val time: String?,
    val genre: String?,
    val ticketingUrl: String?,
    val ticketingHost: String?
) : InteractiveDelegate<CarouselViewData.Listener>, Parcelable {

    override val layout = R.layout.item_carousel

    override fun asParcelable(): ParcelableViewData {
        return ParcelableViewData(id, name)
    }

    interface Listener : DelegateListener {
        fun onClick(carouselViewData: CarouselViewData)
        fun onTicketingClick(ticketingUrl: String)
    }

    override fun onBindViewDelegate(view: View, listener: Listener) {
        view.findViewById<TextView>(R.id.titleTextView).setUpWith(name)
        view.findViewById<TextView>(R.id.timeTextView).setUpWith(time)
        view.findViewById<TextView>(R.id.genreTextView).setUpWith(genre)

        val concertImageView = view.findViewById<ShapeableImageView>(R.id.concertImageView)
        concertImageView.load(image)

        val buyTicketsTextView = view.findViewById<TextView>(R.id.buyTicketsTextView)

        buyTicketsTextView.setUpCTA(ticketingHost, ticketingUrl) {
            ticketingUrl?.let { listener.onTicketingClick(it) }
        }

        view.setOnClickListener {
            listener.onClick(this)
        }
    }
}

data class NewestViewData(
    val id: String,
    val name: String?,
    val day: String?,
    val month: String?,
    val ticketingHostName: String?
) : InteractiveDelegate<NewestViewData.Listener>, Parcelable {

    override val layout = R.layout.item_newest

    override fun asParcelable(): ParcelableViewData {
        return ParcelableViewData(id, name)
    }

    interface Listener : DelegateListener {
        fun onClick(newestViewData: NewestViewData)
    }

    override fun onBindViewDelegate(view: View, listener: Listener) {
        view.setOnClickListener {
            listener.onClick(this)
        }

        view.findViewById<TextView>(R.id.titleTextView).setUpWith(name)
        view.findViewById<TextView>(R.id.subtitleTextView).setUpWith(ticketingHostName)

        view.findViewById<TextView>(R.id.dayTextView).setUpWith(day)
        view.findViewById<TextView>(R.id.monthTextView).setUpWith(month)
    }
}
