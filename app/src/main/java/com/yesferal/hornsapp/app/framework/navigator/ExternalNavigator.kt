/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.navigator

import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import androidx.fragment.app.Fragment
import com.yesferal.hornsapp.core.domain.abstraction.Logger
import com.yesferal.hornsapp.core.domain.navigator.Navigator
import com.yesferal.hornsapp.core.domain.navigator.ScreenType

class ExternalNavigator(
    private val logger: Logger,
    private val fragmentNavigator: FragmentNavigator? = null
) : FragmentNavigator {
    private val MAP_PACKAGE = "com.google.android.apps.maps"
    private val FACEBOOK_PACKAGE = "com.facebook.katana"
    private val CALENDAR_ACTION = "vnd.android.cursor.item/event"

    override fun navigate(view: Fragment, navigator: Navigator) {
        val intent = when (navigator.to) {
            ScreenType.WEB_VIEW -> navigateToWebView(navigator)
            ScreenType.CALENDAR -> navigateToCalendar(navigator)
            ScreenType.MAP -> navigateToWebView(navigator)
            ScreenType.MESSAGE -> navigateToMessage(navigator)
            else -> null
        }

        intent?.let {
            try {
                logger.d("navigate from: $view to external view: ${navigator.to}")
                view.startActivity(intent)
            } catch (e: Exception) {
                val destination = navigator.to
                val message = "Navigator Error. Destination: $destination. Exception: $e."
                logger.e(message)
            }
        }?: kotlin.run {
            fragmentNavigator?.navigate(view, navigator)
        }
    }

    private fun navigateToWebView(navigator: Navigator): Intent? {
        val params = navigator.parameters
        val uri = params?.getString(FragmentNavigator.PARAM_ANDROID_URI)
        if (uri.isNullOrEmpty()) {
            return null
        }
        val androidUri = Uri.parse(uri)
        logger.d("navigate with uri: $androidUri")

        return Intent(Intent.ACTION_VIEW, androidUri)
    }

    private fun navigateToCalendar(navigator: Navigator): Intent? {
        val params = navigator.parameters
        val title = params?.getString(FragmentNavigator.PARAM_TITLE)
        val beginTime = params?.getLong(FragmentNavigator.PARAM_BEGIN_TIME)
        val endTime = params?.getLong(FragmentNavigator.PARAM_END_TIME)

        if (params == null || title.isNullOrEmpty() || beginTime == null || endTime == null) {
            return null
        }

        val intent = Intent(Intent.ACTION_EDIT)
        intent.type = CALENDAR_ACTION
        intent.putExtra(CalendarContract.Events.TITLE, title)
        intent.putExtra("beginTime", beginTime)
        intent.putExtra("endTime", endTime)
        intent.putExtra(
            CalendarContract.Events.DESCRIPTION,
            params.getString(FragmentNavigator.PARAM_DESCRIPTION)
        )
        intent.putExtra(
            CalendarContract.Events.EVENT_LOCATION,
            params.getString(FragmentNavigator.PARAM_EVENT_LOCATION)
        )

        return intent
    }

    private fun navigateToMessage(navigator: Navigator): Intent? {
        val params = navigator.parameters
        val message = params?.getString(FragmentNavigator.PARAM_MESSAGE)
        if (message.isNullOrEmpty()) {
            return null
        }

        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, message)
        intent.type = "text/plain"

        return intent
    }
}
