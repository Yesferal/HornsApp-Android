package com.yesferal.hornsapp.app.presentation.ui.home

import androidx.fragment.app.Fragment
import com.yesferal.hornsapp.app.presentation.ui.concert.favorite.FavoritesFragment
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.NewestFragment
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingFragment
import com.yesferal.hornsapp.app.presentation.ui.error.ErrorFragment
import com.yesferal.hornsapp.core.domain.entity.drawer.ViewDrawer

class FragmentFactory {
    fun getFragment(type: ViewDrawer.Type): Fragment {
        return when (type) {
            ViewDrawer.Type.NEWEST_FRAGMENT -> NewestFragment.newInstance()
            ViewDrawer.Type.UPCOMING_FRAGMENT -> UpcomingFragment.newInstance()
            ViewDrawer.Type.FAVORITE_FRAGMENT -> FavoritesFragment.newInstance()
            else -> ErrorFragment.newInstance()
        }
    }
}
