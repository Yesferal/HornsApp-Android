/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.navigator

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.yesferal.hornsapp.app.presentation.common.base.ParcelableViewData
import com.yesferal.hornsapp.app.presentation.common.extension.getParcelableViewData
import com.yesferal.hornsapp.app.presentation.ui.home.HomeFragment
import com.yesferal.hornsapp.app.presentation.ui.home.HomeFragmentDirections
import com.yesferal.hornsapp.app.presentation.ui.splash.SplashFragmentDirections
import com.yesferal.hornsapp.core.domain.abstraction.Logger
import com.yesferal.hornsapp.core.domain.entity.render.NavigatorRender
import com.yesferal.hornsapp.core.domain.entity.render.ScreenRender
import com.yesferal.hornsapp.core.domain.navigator.Navigator

class AppNavigator(private val logger: Logger, private val fragmentNavigator: FragmentNavigator? = null) :
    FragmentNavigator {
    override fun navigate(view: Fragment, navigator: Navigator) {
        val to = navigator.to

        val navDirections = when (to) {
            ScreenRender.Type.HOME_SCREEN -> getDirectionToHome(view, type = ScreenRender.Type.HOME_SCREEN)
            ScreenRender.Type.ON_BOARDING_SCREEN -> getDirectionToOnBoarding()
            ScreenRender.Type.SETTING_SCREEN -> getDirectionToSettings()
            ScreenRender.Type.CONCERT_DETAIL_SCREEN -> getDirectionToDetail(navigator.navigatorRender) {
                getDirectionToConcertDetail(it)
            }
            ScreenRender.Type.UPCOMING_SCREEN -> getDirectionToHome(view, type = ScreenRender.Type.UPCOMING_SCREEN)
            ScreenRender.Type.FAVORITE_SCREEN -> getDirectionToHome(view, type = ScreenRender.Type.FAVORITE_SCREEN)
            ScreenRender.Type.RENDER_SCREEN -> getDirectionToDetail(navigator.navigatorRender) {
                getDirectionToScreenRender(it)
            }
            ScreenRender.Type.LINEUP_SCREEN -> getDirectionToDetail(navigator.navigatorRender) {
                getDirectionToLineup(it)
            }
            else -> null
        }
        navDirections?.let {
            logger.d("navigate from: $view to fragment: $to")
            navigator.popBackStackId?.let { popBackStackId ->
                view.findNavController().popBackStack(popBackStackId, true)
            }
            view.findNavController().navigate(it)
        } ?: kotlin.run {
            fragmentNavigator?.navigate(view, navigator)
        }
    }

    private fun getDirectionToHome(view: Fragment, type: ScreenRender.Type): NavDirections? {
        return if (view.parentFragment is HomeFragment) {
            logger.d("Its parent is HomeFragment, so we will navigate into a specific TAB $type")
            (view.parentFragment as HomeFragment).navigateToTab(type)
            null
        } else {
            logger.d("Its parent is NOT HomeFragment, so we will navigate into HOME")
            SplashFragmentDirections.actionToHome()
        }
    }

    private fun getDirectionToOnBoarding(): NavDirections {
        return SplashFragmentDirections.actionToOnBoarding()
    }

    private fun getDirectionToLineup(
        parcelableViewData: ParcelableViewData
    ): NavDirections {
        return SplashFragmentDirections.actionToLineup(parcelableViewData)
    }

    private fun getDirectionToSettings(): NavDirections {
        return HomeFragmentDirections.actionToSettings()
    }

    private fun getDirectionToDetail(
        navigatorRender: NavigatorRender?,
        func: (ParcelableViewData) -> NavDirections
    ): NavDirections? {
        val parcelable = navigatorRender?.getParcelableViewData(FragmentNavigator.PARAM_PARCELABLE_VIEW_DATA)

        return if (parcelable is ParcelableViewData) {
            func(parcelable)
        } else { null }
    }

    private fun getDirectionToScreenRender(
        parcelableViewData: ParcelableViewData
    ): NavDirections {
        return HomeFragmentDirections.actionToScreenRender(parcelableViewData)
    }

    private fun getDirectionToConcertDetail(
        parcelableViewData: ParcelableViewData
    ): NavDirections {
        return HomeFragmentDirections.actionToConcert(parcelableViewData)
    }
}
