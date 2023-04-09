/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.navigator

import androidx.fragment.app.Fragment
import com.yesferal.hornsapp.core.domain.navigator.Navigator

interface FragmentNavigator {
    companion object {
        const val PARAM_TITLE = "param_title"
        const val PARAM_BEGIN_TIME = "param_begin_time"
        const val PARAM_END_TIME = "param_end_time"
        const val PARAM_DESCRIPTION = "param_description"
        const val PARAM_EVENT_LOCATION = "param_event_location"

        const val PARAM_ANDROID_URI = "param_android_uri"
        const val PARAM_MESSAGE = "param_message"

        const val PARAM_PARCELABLE_VIEW_DATA = "param_parcelable_view_data"
    }

    fun navigate(view: Fragment, navigator: Navigator)
}
