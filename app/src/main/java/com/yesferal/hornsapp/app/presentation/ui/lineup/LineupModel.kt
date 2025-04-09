/* Copyright © 2025 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.lineup

import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.framework.navigator.FragmentNavigator
import com.yesferal.hornsapp.app.presentation.common.extension.setUpWith
import com.yesferal.hornsapp.core.domain.entity.render.ScreenRender
import com.yesferal.hornsapp.core.domain.navigator.NavViewData
import com.yesferal.hornsapp.core.domain.navigator.Parameters
import com.yesferal.hornsapp.delegate.abstraction.DelegateListener
import com.yesferal.hornsapp.delegate.delegate.InteractiveDelegate
import com.yesferal.hornsapp.delegate.delegate.NonInteractiveDelegate

data class LineupPerformanceViewData(
    val title: String?,
    val subtitle: String?,
    val startTime: Long?,
    val duration: Int?,
    var isFavorite: Boolean
) : InteractiveDelegate<LineupPerformanceViewData.Listener>, NavViewData {

    override val layout = R.layout.item_lineup_performance

    interface Listener : DelegateListener {
        fun onClick(parameters: Parameters)
    }

    override fun onBindViewDelegate(view: View, listener: Listener) {
        val titleTextView = view.findViewById<TextView>(R.id.titleTextView)
        titleTextView.setUpWith(title)
        val subtitleTextView = view.findViewById<TextView>(R.id.subtitleTextView)
        subtitleTextView.setUpWith(subtitle)
        val titleImageView = view.findViewById<ImageView>(R.id.titleImageView)

        val time = System.currentTimeMillis() - (5 * 60 * 60 * 1000)
        if (time < (startTime ?: 0) || time > (startTime?.plus(((duration?.times(60) ?: 0) * 1000))
                ?: 0)
        ) {
            // TODO: Create an Enum for icons if it does not exist
            val textColor = ContextCompat.getColor(view.context, R.color.primaryText)

            titleImageView.setColorFilter(textColor)
            titleTextView.setTextColor(textColor)
            subtitleTextView.setTextColor(
                ContextCompat.getColor(
                    view.context,
                    R.color.secondaryText
                )
            )
            view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.background))
        } else {
            // TODO: Set Button style to this view instead
            val textColor = Color.WHITE
            titleImageView.setColorFilter(textColor)
            titleTextView.setTextColor(textColor)
            subtitleTextView.setTextColor(textColor)
            view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.accent))
        }

        view.updateLayoutParams {
            height = view.getLineupHeight(duration)
        }
        view.setOnClickListener {
            listener.onClick(toMap())
        }
    }

    override fun toMap(): Parameters {
        return Parameters(ScreenRender.Type.CALENDAR_SCREEN.name).apply {
            // TODO: Create a HornsApp Calendar object
            // Duplicated code in ConcertModel
            if (title != null && startTime != null && duration != null) {
                val beginTime = startTime + getLimaTime()

                put(FragmentNavigator.PARAM_TITLE, title)
                put(FragmentNavigator.PARAM_BEGIN_TIME, beginTime)
                put(FragmentNavigator.PARAM_END_TIME, beginTime + (duration * 60 * 1000))
            }
        }
    }

    // TODO: USE A CORRECT TIME
    // THIS JUST WORK FOR LIMA
    fun getLimaTime(): Long {
        return (5 * 60 * 60 * 1000)
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
        view.updateLayoutParams {
            height = view.getLineupHeight(duration)
        }
    }
}

fun View.getLineupHeight(duration: Int?): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        (duration?.toFloat() ?: 0F) * 2.5F,
        this.context?.resources?.displayMetrics
    ).toInt()
}
