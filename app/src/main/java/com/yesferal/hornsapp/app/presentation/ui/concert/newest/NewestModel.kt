package com.yesferal.hornsapp.app.presentation.ui.concert.newest

import android.view.View
import android.widget.ImageView
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

data class TitleViewData(
    val id: String?,
    val title: String?,
    val subtitle: String?,
    val deeplink: String?
) : InteractiveDelegate<TitleViewData.Listener>, Parcelable {

    override val layout = R.layout.item_newest_title

    override fun asParcelable(): ParcelableViewData {
        return ParcelableViewData(id.orEmpty(), title)
    }

    interface Listener : DelegateListener {
        fun onClick(titleViewData: TitleViewData)
    }

    override fun onBindViewDelegate(view: View, listener: Listener) {
        view.findViewById<TextView>(R.id.titleTextView).setUpWith(title)
        view.findViewById<TextView>(R.id.subtitleTextView).setUpWith(subtitle)
        deeplink?.let {
            view.findViewById<TextView>(R.id.seeMoreTextView).visibility = View.VISIBLE
            view.findViewById<ImageView>(R.id.arrowView).visibility = View.VISIBLE
        } ?: kotlin.run {
            view.findViewById<TextView>(R.id.seeMoreTextView).visibility = View.GONE
            view.findViewById<ImageView>(R.id.arrowView).visibility = View.GONE
        }

        view.setOnClickListener {
            listener.onClick(this)
        }
    }
}

data class HomeCardViewData(
    val id: String?,
    val title: String?,
    val subtitle: String?,
    val deeplink: String?
) : InteractiveDelegate<HomeCardViewData.Listener>, Parcelable {

    override val layout = R.layout.item_home_card

    interface Listener : DelegateListener {
        fun onClick(homeCardViewData: HomeCardViewData)
    }

    override fun asParcelable(): ParcelableViewData {
        return ParcelableViewData(id.orEmpty(), title)
    }

    override fun onBindViewDelegate(view: View, listener: Listener) {
        view.findViewById<TextView>(R.id.titleTextView).setUpWith(title)
        view.findViewById<TextView>(R.id.subtitleTextView).setUpWith(subtitle)

        deeplink?.let {
            view.findViewById<TextView>(R.id.goNowTicketsTextView).visibility = View.VISIBLE
        }?: kotlin.run {
            view.findViewById<TextView>(R.id.goNowTicketsTextView).visibility = View.GONE
        }

        view.setOnClickListener {
            listener.onClick(this)
        }
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
