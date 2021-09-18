package com.yesferal.hornsapp.app.presentation.ui.concert.upcoming

import android.view.View
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.google.android.material.imageview.ShapeableImageView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.Parcelable
import com.yesferal.hornsapp.app.presentation.common.base.ParcelableViewData
import com.yesferal.hornsapp.app.presentation.common.extension.load
import com.yesferal.hornsapp.app.presentation.common.extension.setAllCornersRounded
import com.yesferal.hornsapp.app.presentation.common.extension.setUpWith
import com.yesferal.hornsapp.multitype.abstraction.Delegate
import com.yesferal.hornsapp.multitype.abstraction.DelegateListener
import com.yesferal.hornsapp.multitype.delegate.InteractiveDelegate
import com.yesferal.hornsapp.multitype.delegate.NonInteractiveDelegate

data class UpcomingViewState(
    val items: List<Delegate>? = null,
    val isLoading: Boolean = false
)

data class UpcomingViewData(
    val id: String,
    val name: String?,
    val image: String?,
    val day: String?,
    val month: String?,
    val year: String?,
    val time: String?,
    val genre: String?
) : InteractiveDelegate<UpcomingViewData.Listener>, Parcelable {

    override val layout = R.layout.item_upcoming

    override fun asParcelable(): ParcelableViewData {
        return ParcelableViewData(id, name)
    }

    interface Listener : DelegateListener {
        fun onClick(upcomingViewData: UpcomingViewData)
    }

    override fun onBindViewDelegate(view: View, listener: Listener) {
        year?.let {
            val tagText = StringBuilder().append("#").append(it).toString()
            view.findViewById<TextView>(R.id.tagTextView).setUpWith(tagText)
        }
        view.findViewById<TextView>(R.id.titleTextView).setUpWith(name)
        view.findViewById<TextView>(R.id.dayTextView).setUpWith(day)
        view.findViewById<TextView>(R.id.monthTextView).setUpWith(month)
        view.findViewById<TextView>(R.id.timeTextView).setUpWith(time)
        view.findViewById<TextView>(R.id.genreTextView).setUpWith(genre)

        val concertImageView = view.findViewById<ShapeableImageView>(R.id.concertImageView)
        concertImageView.setAllCornersRounded()
        concertImageView.load(image)

        view.setOnClickListener {
            listener.onClick(this)
        }
    }
}

data class ErrorViewData(
    @DrawableRes val imageId: Int,
    @StringRes val errorMessage: Int
) : NonInteractiveDelegate {

    override val layout = R.layout.custom_error

    override fun onBindViewDelegate(view: View, listener: DelegateListener) {
        view.findViewById<TextView>(R.id.errorTextView)
            .setUpWith(view.context.getString(errorMessage))
    }
}
