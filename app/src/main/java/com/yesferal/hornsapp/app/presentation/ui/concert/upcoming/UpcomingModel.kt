package com.yesferal.hornsapp.app.presentation.ui.concert.upcoming

import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.Parcelable
import com.yesferal.hornsapp.app.presentation.common.base.ParcelableViewData
import com.yesferal.hornsapp.app.presentation.common.extension.load
import com.yesferal.hornsapp.app.presentation.common.extension.setAllCornersRounded
import com.yesferal.hornsapp.app.presentation.common.extension.setUpWith
import com.yesferal.hornsapp.multitype.abstraction.Delegate
import com.yesferal.hornsapp.multitype.abstraction.DelegateListener
import com.yesferal.hornsapp.multitype.delegate.NonInteractiveViewDelegate
import com.yesferal.hornsapp.multitype.delegate.ViewDelegate
import kotlinx.android.synthetic.main.custom_date_text_view.view.*
import kotlinx.android.synthetic.main.custom_error.view.*
import kotlinx.android.synthetic.main.item_upcoming.view.*
import kotlinx.android.synthetic.main.item_upcoming.view.containerLayout

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
) : ViewDelegate<UpcomingViewData.Listener>(), Parcelable {

    override val layout = R.layout.item_upcoming

    override fun asParcelable(): ParcelableViewData {
        return ParcelableViewData(id, name)
    }

    interface Listener: DelegateListener {
        fun onClick(upcomingViewData: UpcomingViewData)
    }

    override fun onBindViewDelegate(view: View, listener: Listener) {
        year?.let {
            view.tagTextView.setUpWith("#$it")
        }
        view.titleTextView.setUpWith(name)
        view.dayTextView.setUpWith(day)
        view.monthTextView.setUpWith(month)
        view.timeTextView.setUpWith(time)
        view.genreTextView.setUpWith(genre)

        view.concertImageView.setAllCornersRounded()
        view.concertImageView.load(image)

        view.containerLayout.setOnClickListener {
            listener.onClick(this)
        }
    }
}

data class ErrorViewData(
    @DrawableRes val imageId: Int,
    @StringRes val errorMessage: Int
) : NonInteractiveViewDelegate() {

    override val layout = R.layout.custom_error

    override fun onBindViewDelegate(view: View, listener: DelegateListener) {
        view.errorTextView.let {
            it.setUpWith(it.context.getString(errorMessage))
        }
    }
}
