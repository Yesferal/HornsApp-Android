package com.yesferal.hornsapp.app.presentation.ui.concert.detail

import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import com.google.android.material.imageview.ShapeableImageView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.Parcelable
import com.yesferal.hornsapp.app.presentation.common.base.ParcelableViewData
import com.yesferal.hornsapp.app.presentation.common.extension.dateTimeFormatted
import com.yesferal.hornsapp.app.presentation.common.extension.dayFormatted
import com.yesferal.hornsapp.app.presentation.common.extension.load
import com.yesferal.hornsapp.app.presentation.common.extension.monthFormatted
import com.yesferal.hornsapp.app.presentation.common.extension.setAllCornersRounded
import com.yesferal.hornsapp.core.domain.entity.Band
import com.yesferal.hornsapp.core.domain.entity.Concert
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
) {
    val dateTime: String? = concert.timeInMillis.dateTimeFormatted()
    val day: String? = concert.timeInMillis.dayFormatted()
    val month: String? = concert.timeInMillis.monthFormatted()
    val beginTime = concert.timeInMillis
        ?.minus(TimeZone.getDefault().rawOffset + TimeZone.getDefault().dstSavings)
        ?: 0
    val endTime = beginTime + (2 * 60 * 60 * 1000)
}

data class BandViewData(
    val band: Band
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

        val itemImageView = view.findViewById<ShapeableImageView>(R.id.itemImageView)
        itemImageView.setAllCornersRounded()
        itemImageView.load(band.membersImage)

        view.setOnClickListener {
            listener.onClick(this)
        }
    }
}
