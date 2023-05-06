/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.concert.detail

import android.net.Uri
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import com.google.android.material.imageview.ShapeableImageView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.framework.navigator.FragmentNavigator
import com.yesferal.hornsapp.app.presentation.common.base.Parcelable
import com.yesferal.hornsapp.app.presentation.common.base.ParcelableViewData
import com.yesferal.hornsapp.app.presentation.common.extension.dateTimeFormatted
import com.yesferal.hornsapp.app.presentation.common.extension.dayFormatted
import com.yesferal.hornsapp.app.presentation.common.extension.load
import com.yesferal.hornsapp.app.presentation.common.extension.monthFormatted
import com.yesferal.hornsapp.app.presentation.common.extension.setAllCornersRounded
import com.yesferal.hornsapp.core.domain.entity.Band
import com.yesferal.hornsapp.core.domain.entity.Concert
import com.yesferal.hornsapp.core.domain.entity.Venue
import com.yesferal.hornsapp.core.domain.navigator.NavViewData
import com.yesferal.hornsapp.core.domain.navigator.Parameters
import com.yesferal.hornsapp.delegate.abstraction.DelegateListener
import com.yesferal.hornsapp.delegate.delegate.InteractiveDelegate
import java.util.*

data class ConcertViewState(
    val concert: ConcertViewData? = null,
    val bands: List<BandViewData>? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessageId: Int? = null
)

data class ConcertViewData(
    val concert: Concert
): NavViewData {
    val dateTime: String? = concert.timeInMillis.dateTimeFormatted()
    val day: String? = concert.timeInMillis.dayFormatted()
    val month: String? = concert.timeInMillis.monthFormatted()
    private val beginTime = concert.timeInMillis
        ?.minus(TimeZone.getDefault().rawOffset + TimeZone.getDefault().dstSavings)
        ?: 0
    private val endTime = beginTime + (2 * 60 * 60 * 1000)

    override fun toMap(): Parameters {
        return Parameters().apply {
            put(FragmentNavigator.PARAM_TITLE, concert.name.orEmpty())
            put(FragmentNavigator.PARAM_BEGIN_TIME, beginTime)
            put(FragmentNavigator.PARAM_END_TIME, endTime)
            put(FragmentNavigator.PARAM_DESCRIPTION, concert.description.orEmpty())
            put(FragmentNavigator.PARAM_EVENT_LOCATION, concert.venue?.name.orEmpty())
        }
    }
}

data class VenueViewData(
    val venue: Venue
) : NavViewData {
    private val query = Uri.encode(venue.name)
    private val uri = StringBuilder()
        .append("geo:")
        .append(venue.latitude.orEmpty())
        .append(",")
        .append(venue.longitude.orEmpty())
        .append("?q=")
        .append(query.orEmpty())
        .toString()

    override fun toMap(): Parameters {
        return Parameters().apply {
            if (venue.name != null && venue.latitude != null && venue.longitude != null) {
                put(FragmentNavigator.PARAM_ANDROID_URI, uri)
            }
        }
    }
}

data class BandViewData(
    val band: Band,
    val position: String,
    val total: String
) : InteractiveDelegate<BandViewData.Listener>, Parcelable {

    override val layout = R.layout.item_band

    override fun asParcelable(): ParcelableViewData {
        return ParcelableViewData(band.id, band.name)
    }

    interface Listener : DelegateListener {
        fun onClick(bandViewData: BandViewData)
    }

    override fun onBindViewDelegate(view: View, listener: Listener) {
        view.findViewById<TextView>(R.id.itemTextView).text = band.name
        view.findViewById<TextView>(R.id.countTextView).text = StringBuilder()
            .append(position)
            .append("/")
            .append(total)
            .toString()

        val itemImageView = view.findViewById<ShapeableImageView>(R.id.itemImageView)
        itemImageView.setAllCornersRounded()
        itemImageView.load(band.membersImage)

        view.setOnClickListener {
            listener.onClick(this)
        }
    }
}
