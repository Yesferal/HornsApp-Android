package com.yesferal.hornsapp.app.presentation.ui.concert.detail

import android.view.View
import androidx.annotation.StringRes
import com.google.android.gms.ads.AdView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.ParcelableViewData
import com.yesferal.hornsapp.app.presentation.common.base.ViewData
import com.yesferal.hornsapp.app.presentation.common.base.ViewState
import com.yesferal.hornsapp.app.presentation.common.multitype.BaseViewHolder
import com.yesferal.hornsapp.app.presentation.common.multitype.ViewHolderBinding
import com.yesferal.hornsapp.app.presentation.ui.concert.detail.adapter.BandViewHolder
import com.yesferal.hornsapp.domain.entity.Venue
import java.net.URI

data class ConcertViewState(
    val concert: ConcertViewData? = null,
    val bands: List<BandViewData>? = null,
    val adView: AdView? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessageId: Int? = null
) : ViewState

data class ConcertViewData(
    val id: String,
    val name: String?,
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
) : ViewData

data class BandViewData(
    val id: String,
    val name: String?,
    val membersImage: String?,
    val genre: String?
) : ViewHolderBinding {
    fun asParcelable(): ParcelableViewData {
        return ParcelableViewData(
            id,
            name
        )
    }

    interface Listener: ViewHolderBinding.Listener {
        fun onClick(bandViewData: BandViewData)
    }

    override val layout = R.layout.item_band

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(
        itemView: View,
        listener: ViewHolderBinding.Listener
    ): BaseViewHolder<ViewHolderBinding> {
        return BandViewHolder(itemView, listener) as BaseViewHolder<ViewHolderBinding>
    }
}