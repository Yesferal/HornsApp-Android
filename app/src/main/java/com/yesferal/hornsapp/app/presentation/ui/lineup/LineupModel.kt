/* Copyright © 2025 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.lineup

import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.updateLayoutParams
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.extension.setUpWith
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.setImageIcon
import com.yesferal.hornsapp.core.domain.entity.Stage
import com.yesferal.hornsapp.core.domain.navigator.Parameters
import com.yesferal.hornsapp.delegate.abstraction.DelegateListener
import com.yesferal.hornsapp.delegate.delegate.InteractiveDelegate
import com.yesferal.hornsapp.delegate.delegate.NonInteractiveDelegate

data class LineupViewState(
    val day: String? = null,
    val headers: List<String>? = null,
    val stages: List<Stage>? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessageId: Int? = null
)

data class LineupPerformanceViewData(
    val title: String?,
    val subtitle: String?,
    val duration: Int?,
    val navigation: Parameters?,
    val icon: String?
) : InteractiveDelegate<LineupPerformanceViewData.Listener> {

    override val layout = R.layout.item_lineup_performance

    interface Listener : DelegateListener {
        fun onClick(parameters: Parameters)
    }

    override fun onBindViewDelegate(view: View, listener: Listener) {
        view.findViewById<TextView>(R.id.titleTextView).setUpWith(title)
        view.findViewById<TextView>(R.id.subtitleTextView).setUpWith(subtitle)
        view.findViewById<ImageView>(R.id.titleImageView).setImageIcon(icon)
        navigation?.let {
            view.findViewById<ImageView>(R.id.arrowView).visibility = View.VISIBLE
            view.setOnClickListener {
                listener.onClick(navigation)
            }
        } ?: kotlin.run {
            view.findViewById<ImageView>(R.id.arrowView).visibility = View.GONE
            view.setOnClickListener { }
        }
        val heightDP = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            (duration?.toFloat() ?: 0F) * 2.5F,
            view.context?.resources?.displayMetrics
        ).toInt()

        view.updateLayoutParams {
            height = heightDP
        }
    }
}


data class LineupEmptyViewData(
    val duration: Int?,
) : NonInteractiveDelegate {

    override val layout = R.layout.item_lineup_empty

    interface Listener : DelegateListener {
        fun onClick(parameters: Parameters)
    }
    override fun onBindViewDelegate(view: View) {
        val heightDP = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            (duration?.toFloat() ?: 0F) * 2.5F,
            view.context?.resources?.displayMetrics
        ).toInt()

        view.updateLayoutParams {
            height = heightDP
        }
    }
}
