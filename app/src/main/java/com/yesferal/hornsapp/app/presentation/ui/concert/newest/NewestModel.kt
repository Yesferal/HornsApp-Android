package com.yesferal.hornsapp.app.presentation.ui.concert.newest

import android.view.View
import androidx.annotation.StringRes
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.Parcelable
import com.yesferal.hornsapp.app.presentation.common.base.ParcelableViewData
import com.yesferal.hornsapp.app.presentation.common.extension.setUpWith
import com.yesferal.hornsapp.multitype.abstraction.Delegate
import com.yesferal.hornsapp.multitype.abstraction.DelegateListener
import com.yesferal.hornsapp.multitype.delegate.NonInteractiveViewDelegate
import com.yesferal.hornsapp.multitype.delegate.ViewDelegate
import kotlinx.android.synthetic.main.custom_date_text_view.view.*
import kotlinx.android.synthetic.main.item_newest.view.*
import kotlinx.android.synthetic.main.item_newest_title.view.titleTextView

data class NewestViewState(
    val items: List<Delegate>? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null
)

data class TitleViewData(
    val name: String?
) : NonInteractiveViewDelegate() {

    override fun onBindViewDelegate(view: View, listener: DelegateListener) {
        view.titleTextView.setUpWith(name)
    }

    override val layout = R.layout.item_newest_title
}

data class NewestViewData(
    val id: String,
    val name: String?,
    val day: String?,
    val month: String?,
    val ticketingHostName: String?
) : ViewDelegate<NewestViewData.Listener>(), Parcelable {

    override val layout = R.layout.item_newest

    override fun asParcelable(): ParcelableViewData {
        return ParcelableViewData(id, name)
    }

    interface Listener : DelegateListener {
        fun onClick(newestViewData: NewestViewData)
    }

    override fun onBindViewDelegate(view: View, listener: Listener) {
        view.containerLayout.setOnClickListener {
            listener.onClick(this)
        }

        view.titleTextView.setUpWith(name)
        view.subtitleTextView.setUpWith(ticketingHostName)

        view.dayTextView.setUpWith(day)
        view.monthTextView.setUpWith(month)
    }
}
