/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.common.base

import android.os.Parcelable
import com.yesferal.hornsapp.app.framework.navigator.FragmentNavigator
import com.yesferal.hornsapp.core.domain.entity.render.NavigatorRender
import com.yesferal.hornsapp.core.domain.navigator.NavViewData
import kotlinx.parcelize.Parcelize

@Parcelize
class ParcelableViewData(
        val id: String?,
        val name: String?
) : Parcelable, NavViewData {
        override fun toMap(): NavigatorRender {
                return NavigatorRender().apply {
                        put(FragmentNavigator.PARAM_PARCELABLE_VIEW_DATA, this@ParcelableViewData)
                }
        }
}
