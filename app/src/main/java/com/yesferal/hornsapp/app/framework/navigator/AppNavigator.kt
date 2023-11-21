/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.navigator

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.yesferal.hornsapp.app.presentation.common.base.ParcelableViewData
import com.yesferal.hornsapp.app.presentation.ui.home.HomeFragment
import com.yesferal.hornsapp.app.presentation.ui.home.HomeFragmentDirections
import com.yesferal.hornsapp.app.presentation.ui.splash.SplashFragmentDirections
import com.yesferal.hornsapp.core.domain.abstraction.Logger
import com.yesferal.hornsapp.core.domain.navigator.ScreenType
import com.yesferal.hornsapp.core.domain.navigator.Navigator
import com.yesferal.hornsapp.core.domain.navigator.Parameters

class AppNavigator(private val logger: Logger, private val fragmentNavigator: FragmentNavigator? = null) :
    FragmentNavigator {
    override fun navigate(view: Fragment, navigator: Navigator) {
        val to = navigator.to

        val navDirections = when (to) {
            ScreenType.HOME -> getDirectionToHome(view, tab = 0)
            ScreenType.ON_BOARDING -> getDirectionToOnBoarding()
            ScreenType.SETTING -> getDirectionToSettings()
            ScreenType.CONCERT_DETAIL -> getDirectionToConcertDetail(navigator.parameters)
            ScreenType.UPCOMING -> getDirectionToHome(view, tab = 1)
            ScreenType.FAVORITE -> getDirectionToHome(view, tab = 2)
            ScreenType.REVIEW -> getDirectionToReview()
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

    private fun getDirectionToHome(view: Fragment, tab: Int): NavDirections? {
        return if (view.parentFragment is HomeFragment) {
            logger.d("Its parent is HomeFragment, so we will navigate into a specific TAB #${tab}")
            (view.parentFragment as HomeFragment).navigateToTab(tab)
            null
        } else {
            logger.d("Its parent is NOT HomeFragment, so we will navigate into HOME")
            SplashFragmentDirections.actionToHome()
        }
    }

    private fun getDirectionToOnBoarding(): NavDirections {
        return SplashFragmentDirections.actionToOnBoarding()
    }

    private fun getDirectionToSettings(): NavDirections {
        return HomeFragmentDirections.actionToSettings()
    }

    private fun getDirectionToReview(): NavDirections {
        return HomeFragmentDirections.actionToReview()
    }

    private fun getDirectionToConcertDetail(
        parameters: Parameters?
    ): NavDirections? {
        val parcelable = parameters?.get<ParcelableViewData>(FragmentNavigator.PARAM_PARCELABLE_VIEW_DATA)

        return if (parcelable is ParcelableViewData) {
            return HomeFragmentDirections.actionToConcert(parcelable)
        } else { null }
    }
}
