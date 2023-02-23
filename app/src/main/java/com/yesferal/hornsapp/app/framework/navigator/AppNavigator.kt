package com.yesferal.hornsapp.app.framework.navigator

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.yesferal.hornsapp.app.presentation.common.base.asParcelable
import com.yesferal.hornsapp.app.presentation.ui.band.BandBottomSheetFragment
import com.yesferal.hornsapp.app.presentation.ui.concert.detail.EXTRA_PARAM_PARCELABLE
import com.yesferal.hornsapp.app.presentation.ui.home.HomeFragment
import com.yesferal.hornsapp.app.presentation.ui.home.HomeFragmentDirections
import com.yesferal.hornsapp.app.presentation.ui.profile.ProfileBottomSheetFragment
import com.yesferal.hornsapp.app.presentation.ui.splash.SplashFragmentDirections
import com.yesferal.hornsapp.core.domain.abstraction.Logger
import com.yesferal.hornsapp.core.domain.navigator.Direction
import com.yesferal.hornsapp.core.domain.navigator.ScreenType
import com.yesferal.hornsapp.core.domain.navigator.NavViewData
import com.yesferal.hornsapp.core.domain.navigator.Navigator

class AppNavigator(private val logger: Logger) : Navigator<Fragment> {
    override fun navigate(view: Fragment, direction: Direction) {
        val to = direction.to

        logger.d("navigate from: $view")
        logger.d("navigate to: $to")
        val navDirections = when (to) {
            ScreenType.HOME -> getDirectionToHome(view, tab = 0)
            ScreenType.ON_BOARDING -> getDirectionToOnBoarding()
            ScreenType.SETTING -> getDirectionToSettings()
            ScreenType.CONCERT_DETAIL -> getDirectionToConcertDetail(direction.parameter)
            ScreenType.PROFILE -> getDirectionToProfile(view)
            ScreenType.BAND_DETAIL -> getDirectionToBandDetail(view, direction.parameter)
            ScreenType.UPCOMING -> getDirectionToHome(view, tab = 1)
            ScreenType.FAVORITE -> getDirectionToHome(view, tab = 2)
            else -> null
        }
        navDirections?.let { view.findNavController().navigate(it) }
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

    private fun getDirectionToConcertDetail(
        parameters: NavViewData?
    ): NavDirections {
        return HomeFragmentDirections.actionToConcert(parameters?.asParcelable())
    }

    private fun getDirectionToProfile(
        view: Fragment
    ): NavDirections? {
        view.childFragmentManager.let { manager ->
            ProfileBottomSheetFragment.newInstance(Bundle()).apply {
                show(manager, tag)
            }
        }
        return null
    }

    private fun getDirectionToBandDetail(
        view: Fragment,
        parameters: NavViewData?
    ): NavDirections? {
        view.childFragmentManager.let {
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_PARAM_PARCELABLE, parameters?.asParcelable())

            BandBottomSheetFragment.newInstance(bundle).apply {
                show(it, tag)
            }
        }
        return null
    }
}
