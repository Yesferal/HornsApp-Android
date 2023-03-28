/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.navigator

import android.content.Intent
import android.provider.CalendarContract
import androidx.fragment.app.Fragment
import com.yesferal.hornsapp.app.presentation.common.base.ExternalNavViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.detail.ConcertViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.detail.VenueViewData
import com.yesferal.hornsapp.app.presentation.ui.profile.MessageViewData
import com.yesferal.hornsapp.core.domain.abstraction.Logger
import com.yesferal.hornsapp.core.domain.navigator.Direction
import com.yesferal.hornsapp.core.domain.navigator.Navigator
import com.yesferal.hornsapp.core.domain.navigator.ScreenType

class ExternalNavigator(
    private val logger: Logger,
    private val navigator: Navigator<Fragment>? = null
) : Navigator<Fragment> {
    private val MAP_PACKAGE = "com.google.android.apps.maps"
    private val FACEBOOK_PACKAGE = "com.facebook.katana"
    private val CALENDAR_ACTION = "vnd.android.cursor.item/event"

    override fun navigate(view: Fragment, direction: Direction) {
        val intent = when (direction.to) {
            ScreenType.WEB_VIEW -> navigateToWebView(direction)
            ScreenType.CALENDAR -> navigateToCalendar(direction)
            ScreenType.MAP -> navigateToMap(direction)
            ScreenType.MESSAGE -> navigateToMessage(direction)
            else -> null
        }

        intent?.let {
            try {
                logger.d("navigate from: $view to external view: ${direction.to}")
                view.startActivity(intent)
            } catch (e: Exception) {
                val parameterId = direction.parameter?.id.orEmpty()
                val message = "Navigator Error. Parameter Id: $parameterId. Exception: $e."
                logger.e(message)
            }
        }?: kotlin.run {
            navigator?.navigate(view, direction)
        }
    }

    private fun navigateToWebView(direction: Direction): Intent? {
        val navViewData = direction.parameter
        if (navViewData !is ExternalNavViewData) {
            return null
        }

        logger.d("navigate with uri: ${navViewData.androidUri}")
        return Intent(Intent.ACTION_VIEW, navViewData.androidUri)
    }

    private fun navigateToCalendar(direction: Direction): Intent? {
        val navViewData = direction.parameter
        if (navViewData !is ConcertViewData) {
            return null
        }

        val intent = Intent(Intent.ACTION_EDIT)
        intent.type = CALENDAR_ACTION
        intent.putExtra(CalendarContract.Events.TITLE, navViewData.concert.name)
        intent.putExtra("beginTime", navViewData.beginTime)
        intent.putExtra("endTime", navViewData.endTime)
        intent.putExtra(CalendarContract.Events.DESCRIPTION, navViewData.concert.description)
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, navViewData.concert.venue?.name)

        return intent
    }

    private fun navigateToMap(direction: Direction): Intent? {
        val navViewData = direction.parameter
        if (navViewData !is VenueViewData) {
            return null
        }

        return Intent(Intent.ACTION_VIEW, navViewData.androidUri)
    }

    private fun navigateToMessage(direction: Direction): Intent? {
        val navViewData = direction.parameter
        if (navViewData !is MessageViewData) {
            return null
        }

        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, navViewData.message)
        intent.type = "text/plain"

        return intent
    }
}
