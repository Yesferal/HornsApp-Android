/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.navigator

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.yesferal.hornsapp.app.presentation.common.custom.HornsBottomSheetFragment
import com.yesferal.hornsapp.app.presentation.ui.band.BandBottomSheetFragment
import com.yesferal.hornsapp.app.presentation.ui.concert.detail.EXTRA_PARAM_PARCELABLE
import com.yesferal.hornsapp.app.presentation.ui.profile.ProfileBottomSheetFragment
import com.yesferal.hornsapp.core.domain.abstraction.Logger
import com.yesferal.hornsapp.core.domain.entity.render.NavigatorRender
import com.yesferal.hornsapp.core.domain.entity.render.ScreenRender
import com.yesferal.hornsapp.core.domain.navigator.Navigator

class DialogNavigator(
    private val logger: Logger,
    private val fragmentNavigator: FragmentNavigator? = null
) : FragmentNavigator {

    override fun navigate(view: Fragment, navigator: Navigator) {
        val to = navigator.to

        val hornsBottomSheetFragment = when (to) {
            ScreenRender.Type.PROFILE_SCREEN -> getDirectionToProfile()
            ScreenRender.Type.BAND_DETAIL_SCREEN -> getDirectionToBandDetail(navigator.navigatorRender)
            else -> null
        }

        hornsBottomSheetFragment?.let {
            logger.d("navigate from: $view to dialog: $to")
            it.show(view.childFragmentManager, view.tag)
        }?: kotlin.run {
            fragmentNavigator?.navigate(view, navigator)
        }
    }

    private fun getDirectionToProfile(
    ): HornsBottomSheetFragment {
        return ProfileBottomSheetFragment.newInstance(Bundle())
    }

    private fun getDirectionToBandDetail(
        navigatorRender: NavigatorRender?
    ): HornsBottomSheetFragment? {
        val parcelable = navigatorRender?.get<ParcelableViewData>(FragmentNavigator.PARAM_PARCELABLE_VIEW_DATA)

        return if (parcelable is ParcelableViewData) {
                val bundle = Bundle()
                bundle.putParcelable(EXTRA_PARAM_PARCELABLE, parcelable)

                BandBottomSheetFragment.newInstance(bundle)
            } else {
                null
            }
    }
}
