package com.yesferal.hornsapp.app.presentation.ui.concert.detail

import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import com.google.android.material.imageview.ShapeableImageView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.Parcelable
import com.yesferal.hornsapp.app.presentation.common.base.ParcelableViewData
import com.yesferal.hornsapp.app.presentation.common.extension.load
import com.yesferal.hornsapp.app.presentation.common.extension.setAllCornersRounded
import com.yesferal.hornsapp.delegate.abstraction.DelegateListener
import com.yesferal.hornsapp.delegate.delegate.InteractiveDelegate
import com.yesferal.hornsapp.domain.entity.Venue
import java.net.URI

data class ConcertViewState(
    val concert: ConcertViewData? = null,
    val bands: List<BandViewData>? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessageId: Int? = null
)

data class ConcertViewData(
    val id: String,
    val name: String?,
    val headlinerImage: String?,
    val description: String?,
    val timeInMillis: Long?,
    val dateTime: String?,
    val day: String?,
    val month: String?,
    val trailerUrl: URI?,
    val facebookUrl: URI?,
    var isFavorite: Boolean,
    val genre: String?,
    val ticketingHost: String? = null,
    val ticketingUrl: URI? = null,
    val venue: Venue? = null,
)

data class BandViewData(
    val id: String,
    val name: String?,
    val membersImage: String?,
    val genre: String?
) : InteractiveDelegate<BandViewData.Listener>, Parcelable {

    override val layout = R.layout.item_band

    override fun asParcelable(): ParcelableViewData {
        return ParcelableViewData(id, name)
    }

    interface Listener : DelegateListener {
        fun onClick(bandViewData: BandViewData)
    }

    override fun onBindViewDelegate(view: View, listener: Listener) {
        view.findViewById<TextView>(R.id.itemTextView).text = name

        val itemImageView = view.findViewById<ShapeableImageView>(R.id.itemImageView)
        itemImageView.setAllCornersRounded()
        itemImageView.load(membersImage)

        view.setOnClickListener {
            listener.onClick(this)
        }
    }
}
