package com.yesferal.hornsapp.app.framework.navigator

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.yesferal.hornsapp.app.framework.logger.YLogger
import com.yesferal.hornsapp.app.presentation.common.base.ParcelableViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.favorite.FavoritesFragment
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.NewestFragment
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingFragment
import com.yesferal.hornsapp.app.presentation.ui.home.HomeFragment
import com.yesferal.hornsapp.app.presentation.ui.home.HomeFragmentDirections
import com.yesferal.hornsapp.app.presentation.ui.onboarding.OnBoardingFragment
import com.yesferal.hornsapp.app.presentation.ui.onboarding.OnBoardingFragmentDirections
import com.yesferal.hornsapp.app.presentation.ui.profile.ProfileFragment
import com.yesferal.hornsapp.app.presentation.ui.splash.SplashFragment
import com.yesferal.hornsapp.app.presentation.ui.splash.SplashFragmentDirections
import com.yesferal.hornsapp.core.domain.navigator.Direction
import com.yesferal.hornsapp.core.domain.navigator.ScreenType
import com.yesferal.hornsapp.core.domain.navigator.NavViewData
import com.yesferal.hornsapp.core.domain.navigator.Navigator

class AppNavigator : Navigator<Fragment> {
    override fun navigate(view: Fragment, direction: Direction) {
        val from = view.toDirectionType()
        val to = direction.to

        YLogger.d("navigate from: $from ($view)")
        YLogger.d("navigate to: $to")
        val navDirections = when (to) {
            ScreenType.Home -> getDirectionToHome(from)
            ScreenType.OnBoarding -> getDirectionToOnBoarding(from)
            ScreenType.Setting -> getDirectionToSettings(from)
            ScreenType.ConcertDetail -> getDirectionToConcertDetail(from, direction.parameter)
            else -> null
        }
        navDirections?.let { view.findNavController().navigate(it) }
    }

    private fun Fragment.toDirectionType(): ScreenType {
        return when (this) {
            is HomeFragment, is NewestFragment, is UpcomingFragment,
            is FavoritesFragment -> ScreenType.Home
            is ProfileFragment -> ScreenType.Profile
            is SplashFragment -> ScreenType.Splash
            is OnBoardingFragment -> ScreenType.OnBoarding
            else -> ScreenType.None
        }
    }

    private fun getDirectionToHome(from: ScreenType): NavDirections? {
        return when (from) {
            ScreenType.OnBoarding -> OnBoardingFragmentDirections.actionOnBoardingToHome()
            ScreenType.Splash -> SplashFragmentDirections.actionSplashToHome()
            else -> null
        }
    }

    private fun getDirectionToOnBoarding(from: ScreenType): NavDirections? {
        return when (from) {
            ScreenType.Splash -> SplashFragmentDirections.actionSplashToOnBoarding()
            else -> null
        }
    }

    private fun getDirectionToSettings(from: ScreenType): NavDirections? {
        return when (from) {
            ScreenType.Profile -> HomeFragmentDirections.actionHomeToSettings()
            else -> null
        }
    }

    private fun getDirectionToConcertDetail(
        from: ScreenType,
        parameters: NavViewData?
    ): NavDirections? {
        return when (from) {
            ScreenType.Home -> {
                if (parameters is ParcelableViewData) {
                    HomeFragmentDirections.actionHomeToConcert(parameters)
                } else {
                    null
                }
            }
            else -> null
        }
    }
}
