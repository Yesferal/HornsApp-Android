package com.yesferal.hornsapp.app.framework.navigator

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.yesferal.hornsapp.app.presentation.common.base.ParcelableViewData
import com.yesferal.hornsapp.app.presentation.common.base.asParcelable
import com.yesferal.hornsapp.app.presentation.ui.band.BandBottomSheetFragment
import com.yesferal.hornsapp.app.presentation.ui.concert.detail.ConcertFragment
import com.yesferal.hornsapp.app.presentation.ui.concert.detail.EXTRA_PARAM_PARCELABLE
import com.yesferal.hornsapp.app.presentation.ui.concert.favorite.FavoritesFragment
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.NewestFragment
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingFragment
import com.yesferal.hornsapp.app.presentation.ui.home.HomeFragment
import com.yesferal.hornsapp.app.presentation.ui.home.HomeFragmentDirections
import com.yesferal.hornsapp.app.presentation.ui.onboarding.OnBoardingFragment
import com.yesferal.hornsapp.app.presentation.ui.onboarding.OnBoardingFragmentDirections
import com.yesferal.hornsapp.app.presentation.ui.profile.ProfileBottomSheetFragment
import com.yesferal.hornsapp.app.presentation.ui.profile.ProfileFragment
import com.yesferal.hornsapp.app.presentation.ui.splash.SplashFragment
import com.yesferal.hornsapp.app.presentation.ui.splash.SplashFragmentDirections
import com.yesferal.hornsapp.core.domain.abstraction.Logger
import com.yesferal.hornsapp.core.domain.navigator.Direction
import com.yesferal.hornsapp.core.domain.navigator.ScreenType
import com.yesferal.hornsapp.core.domain.navigator.NavViewData
import com.yesferal.hornsapp.core.domain.navigator.Navigator

class AppNavigator(private val logger: Logger) : Navigator<Fragment> {
    override fun navigate(view: Fragment, direction: Direction) {
        val from = view.toDirectionType()
        val to = direction.to

        logger.d("navigate from: $from ($view)")
        logger.d("navigate to: $to")
        val navDirections = when (to) {
            ScreenType.HOME -> getDirectionToHome(from)
            ScreenType.ON_BOARDING -> getDirectionToOnBoarding(from)
            ScreenType.SETTING -> getDirectionToSettings(from)
            ScreenType.CONCERT_DETAIL -> getDirectionToConcertDetail(from, direction.parameter)
            ScreenType.PROFILE -> getDirectionToProfile(view)
            ScreenType.BAND_DETAIL -> getDirectionToBandDetail(view, direction.parameter)
            else -> null
        }
        navDirections?.let { view.findNavController().navigate(it) }
    }

    private fun Fragment.toDirectionType(): ScreenType {
        return when (this) {
            is HomeFragment, is NewestFragment, is UpcomingFragment,
            is FavoritesFragment -> ScreenType.HOME
            is ProfileFragment -> ScreenType.PROFILE
            is SplashFragment -> ScreenType.SPLASH
            is OnBoardingFragment -> ScreenType.ON_BOARDING
            is ConcertFragment -> ScreenType.CONCERT_DETAIL
            else -> ScreenType.NONE
        }
    }

    private fun getDirectionToHome(from: ScreenType): NavDirections? {
        return when (from) {
            ScreenType.ON_BOARDING -> OnBoardingFragmentDirections.actionOnBoardingToHome()
            ScreenType.SPLASH -> SplashFragmentDirections.actionSplashToHome()
            else -> null
        }
    }

    private fun getDirectionToOnBoarding(from: ScreenType): NavDirections? {
        return when (from) {
            ScreenType.SPLASH -> SplashFragmentDirections.actionSplashToOnBoarding()
            else -> null
        }
    }

    private fun getDirectionToSettings(from: ScreenType): NavDirections? {
        return when (from) {
            ScreenType.PROFILE -> HomeFragmentDirections.actionHomeToSettings()
            else -> null
        }
    }

    private fun getDirectionToConcertDetail(
        from: ScreenType,
        parameters: NavViewData?
    ): NavDirections? {
        return when (from) {
            ScreenType.HOME -> {
                if (parameters is ParcelableViewData) {
                    HomeFragmentDirections.actionHomeToConcert(parameters)
                } else {
                    null
                }
            }
            else -> null
        }
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
