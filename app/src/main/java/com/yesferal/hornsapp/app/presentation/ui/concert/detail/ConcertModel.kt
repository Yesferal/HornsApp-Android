package com.yesferal.hornsapp.app.presentation.ui.concert.detail

import android.view.View
import androidx.annotation.StringRes
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.framework.adMob.AdViewData
import com.yesferal.hornsapp.app.presentation.common.base.Parcelable
import com.yesferal.hornsapp.app.presentation.common.base.ParcelableViewData
import com.yesferal.hornsapp.app.presentation.common.extension.load
import com.yesferal.hornsapp.app.presentation.common.extension.setAllCornersRounded
import com.yesferal.hornsapp.domain.entity.Venue
import com.yesferal.hornsapp.multitype.BaseViewHolder
import com.yesferal.hornsapp.multitype.model.ViewHolderBinding
import kotlinx.android.synthetic.main.item_band.view.*
import java.net.URI

data class ConcertViewState(
    val concert: ConcertViewData? = null,
    val bands: List<BandViewData>? = null,
    val adViewData: AdViewData? = null,
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
) : ViewHolderBinding, Parcelable {

    override val layout = R.layout.item_band

    override fun asParcelable(): ParcelableViewData {
        return ParcelableViewData(id, name)
    }

    interface Listener: ViewHolderBinding.Listener {
        fun onClick(bandViewData: BandViewData)
    }

    override fun onCreateViewHolder(
        itemView: View,
        listener: ViewHolderBinding.Listener
    ) = object : BaseViewHolder<BandViewData>(itemView) {
        override fun bind(model: BandViewData) {
            itemView.itemTextView.text = model.name
            itemView.itemImageView.setAllCornersRounded()
            itemView.itemImageView.load(model.membersImage)

            itemView.itemImageView.setOnClickListener {
                (listener as Listener).onClick(model)
            }
        }
    }
}