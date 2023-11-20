/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.concert.newest

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.imageview.ShapeableImageView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.framework.adMob.AbstractViewFactory
import com.yesferal.hornsapp.app.framework.adMob.AdUnitIds
import com.yesferal.hornsapp.app.presentation.common.base.Parcelable
import com.yesferal.hornsapp.app.presentation.common.base.ParcelableViewData
import com.yesferal.hornsapp.app.presentation.common.extension.addBottomView
import com.yesferal.hornsapp.app.presentation.common.extension.load
import com.yesferal.hornsapp.app.presentation.common.extension.setAllCornersRounded
import com.yesferal.hornsapp.app.presentation.common.extension.setUpCTA
import com.yesferal.hornsapp.app.presentation.common.extension.setUpWith
import com.yesferal.hornsapp.core.domain.navigator.Parameters
import com.yesferal.hornsapp.delegate.abstraction.DelegateListener
import com.yesferal.hornsapp.delegate.delegate.InteractiveDelegate
import com.yesferal.hornsapp.delegate.delegate.NonInteractiveDelegate

data class TitleViewData(
    val title: String?,
    val subtitle: String?,
    val navigation: Parameters?,
    val icon: String?
) : InteractiveDelegate<TitleViewData.Listener> {

    override val layout = R.layout.item_newest_title

    interface Listener : DelegateListener {
        fun onClick(parameters: Parameters)
    }

    override fun onBindViewDelegate(view: View, listener: Listener) {
        view.findViewById<TextView>(R.id.titleTextView).setUpWith(title)
        view.findViewById<TextView>(R.id.subtitleTextView).setUpWith(subtitle)
        view.findViewById<ImageView>(R.id.titleImageView).setImageIcon(icon)
        navigation?.let {
            view.findViewById<TextView>(R.id.seeMoreTextView).visibility = View.VISIBLE
            view.findViewById<ImageView>(R.id.arrowView).visibility = View.VISIBLE
            view.setOnClickListener {
                listener.onClick(navigation)
            }
        } ?: kotlin.run {
            view.findViewById<TextView>(R.id.seeMoreTextView).visibility = View.GONE
            view.findViewById<ImageView>(R.id.arrowView).visibility = View.GONE
            view.setOnClickListener { }
        }
    }
}

data class IconHomeCardViewData(
    val title: String?,
    val subtitle: String?,
    val backgroundColor: String?,
    val textColor: String?,
    val navigation: Parameters?,
    val icon: String?
) : InteractiveDelegate<IconHomeCardViewData.Listener> {

    override val layout = R.layout.item_home_card

    interface Listener : DelegateListener {
        fun onClick(parameters: Parameters)
    }

    override fun onBindViewDelegate(view: View, listener: Listener) {
        backgroundColor?.let { backgroundColorNotNull ->
            try {
                view.setBackgroundColor(Color.parseColor(backgroundColorNotNull))
            } catch (e: Exception) { }
        }?: kotlin.run {
            view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.primaryText))
        }
        view.findViewById<TextView>(R.id.goNowTicketsTextView).also { goNowTicketsTextView ->
            backgroundColor?.let { backgroundColorNotNull ->
                try {
                    goNowTicketsTextView.setTextColor(Color.parseColor(backgroundColorNotNull))
                } catch (e: Exception) { }
            }?: kotlin.run {
                goNowTicketsTextView.setTextColor(ContextCompat.getColor(view.context, R.color.primaryText))
            }
            textColor?.let { textColorNotNull ->
                try {
                    goNowTicketsTextView.background.setTint(
                        Color.parseColor(
                            textColorNotNull
                        )
                    )
                } catch (e: Exception) { }
            }?: kotlin.run {
                goNowTicketsTextView.background.setTint(ContextCompat.getColor(view.context, R.color.background))
            }
        }
        view.findViewById<TextView>(R.id.titleTextView).also { titleTextView ->
            titleTextView.setUpWith(title)
            try {
                textColor?.let {
                    titleTextView.setTextColor(Color.parseColor(it))
                }?: kotlin.run {
                    titleTextView.setTextColor(ContextCompat.getColor(view.context, R.color.background))
                }
            } catch (e: Exception) { }
        }
        view.findViewById<TextView>(R.id.subtitleTextView).also { subtitleTextView ->
            subtitleTextView.setUpWith(subtitle)
            try {
                textColor?.let {
                    subtitleTextView.setTextColor(Color.parseColor(it))
                }?: kotlin.run {
                    subtitleTextView.setTextColor(ContextCompat.getColor(view.context, R.color.background))
                }
            } catch (e: Exception) { }
        }
        view.findViewById<ImageView>(R.id.appImageView).also { appImageView ->
            appImageView.setImageIcon(icon)
            try {
                textColor?.let {
                    appImageView.setColorFilter(Color.parseColor(it))
                }?: kotlin.run {
                    appImageView.setColorFilter(ContextCompat.getColor(view.context, R.color.background))
                }
            } catch (e: Exception) { }
        }
        navigation?.let {
            view.findViewById<TextView>(R.id.goNowTicketsTextView).visibility = View.VISIBLE
            view.setOnClickListener {
                listener.onClick(navigation)
            }
        }?: kotlin.run {
            view.findViewById<TextView>(R.id.goNowTicketsTextView).visibility = View.GONE
            view.setOnClickListener { }
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

fun ImageView.setImageIcon(icon: String?) {
    val DRAWABLE_TYPE = "drawable"
    val DEF_ICON = "music_note"
    val PREFIX = "ic_"

    val defaultIcon = icon ?: DEF_ICON
    val iconIdentifier = resources.getIdentifier(PREFIX + defaultIcon, DRAWABLE_TYPE, context.packageName)
    setImageResource(iconIdentifier)
}

data class AdViewData(
    val abstractViewFactory: AbstractViewFactory,
    val height: Int?,
    val type: AdUnitIds.Type?
) : NonInteractiveDelegate {
    val BANNER_SIZE = 50

    override val layout = R.layout.item_ad_view

    override fun onBindViewDelegate(view: View) {
        if (height == null || type == null) {
            return
        }

        val safeHeight: Int = if (height != 50 && height != 100 && height != 250) {
            BANNER_SIZE
        } else {
            height
        }

        if (view is ViewGroup) {
            view.addBottomView(abstractViewFactory, type, safeHeight)
        }
    }
}

data class ImageHomeCardViewData(
    //val title: String?,
    //val subtitle: String?,
    val description: String?,
    val navigation: Parameters?,
    val image: String?
) : InteractiveDelegate<ImageHomeCardViewData.Listener> {

    override val layout = R.layout.item_image_home_card

    interface Listener : DelegateListener {
        fun onClick(parameters: Parameters)
    }

    override fun onBindViewDelegate(view: View, listener: Listener) {
        view.findViewById<TextView>(R.id.descriptionTextView).setUpWith(description)
        navigation?.let {
            view.findViewById<TextView>(R.id.seeMoreTextView).visibility = View.VISIBLE
            view.setOnClickListener {
                listener.onClick(navigation)
            }
        }?: kotlin.run {
            view.findViewById<TextView>(R.id.seeMoreTextView).visibility = View.GONE
            view.setOnClickListener { }
        }

        val concertImageView = view.findViewById<ShapeableImageView>(R.id.concertImageView)
        concertImageView.setAllCornersRounded()
        concertImageView.load(image)
    }
}
