package com.yesferal.hornsapp.app.presentation.ui.concert.newest

import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.Parcelable
import com.yesferal.hornsapp.app.presentation.common.base.ParcelableViewData
import com.yesferal.hornsapp.app.presentation.common.extension.setUpWith
import com.yesferal.hornsapp.multitype.abstraction.Delegate
import com.yesferal.hornsapp.multitype.abstraction.DelegateListener
import com.yesferal.hornsapp.multitype.delegate.NonInteractiveViewDelegate
import com.yesferal.hornsapp.multitype.delegate.ViewDelegate

data class NewestViewState(
    val items: List<Delegate>? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null
)

data class TitleViewData(
    val name: String?
) : NonInteractiveViewDelegate() {

    override val layout = R.layout.item_newest_title

    override fun onBindViewDelegate(view: View, listener: DelegateListener) {
        view.findViewById<TextView>(R.id.titleTextView).setUpWith(name)
    }
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
        view.setOnClickListener {
            listener.onClick(this)
        }

        view.findViewById<TextView>(R.id.titleTextView).setUpWith(name)
        view.findViewById<TextView>(R.id.subtitleTextView).setUpWith(ticketingHostName)

        view.findViewById<TextView>(R.id.dayTextView).setUpWith(day)
        view.findViewById<TextView>(R.id.monthTextView).setUpWith(month)
    }
}
