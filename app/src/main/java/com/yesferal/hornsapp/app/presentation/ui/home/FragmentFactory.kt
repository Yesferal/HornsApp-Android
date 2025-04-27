package com.yesferal.hornsapp.app.presentation.ui.home

import androidx.fragment.app.Fragment
import com.yesferal.hornsapp.app.presentation.ui.concert.favorite.FavoritesFragment
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingFragment
import com.yesferal.hornsapp.app.presentation.ui.error.ErrorFragment
import com.yesferal.hornsapp.app.presentation.ui.lineup.LineupFragment
import com.yesferal.hornsapp.app.presentation.ui.screen_render.ScreenRenderFragment
import com.yesferal.hornsapp.core.domain.entity.render.ScreenRender

class FragmentFactory {
    fun getFragment(screenRender: ScreenRender): Fragment {
        return when (screenRender.type) {
            ScreenRender.Type.UPCOMING_SCREEN -> UpcomingFragment.newInstance()
            ScreenRender.Type.FAVORITE_SCREEN -> FavoritesFragment.newInstance()
            ScreenRender.Type.SCREEN_RENDER_SCREEN -> ScreenRenderFragment.newInstance(screenRender._id)
            ScreenRender.Type.STAGE_LINEUP_SCREEN -> LineupFragment.newInstance()
            else -> ErrorFragment.newInstance()
        }
    }
}
