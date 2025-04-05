/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.concert.favorite

import android.os.Bundle
import android.view.View
import com.yesferal.hornsapp.app.presentation.common.custom.RecyclerViewVerticalDecorator
import com.yesferal.hornsapp.app.presentation.common.delegate.DelegateAdapterFragment
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewData
import com.yesferal.hornsapp.core.domain.entity.render.ScreenRender
import com.yesferal.hornsapp.core.domain.navigator.Navigator
import com.yesferal.hornsapp.hadi_android.getViewModel

class FavoritesFragment : DelegateAdapterFragment(), UpcomingViewData.Listener {

    private lateinit var viewModel: FavoritesViewModel

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        delegateRecyclerView.addItemDecoration(RecyclerViewVerticalDecorator(8, 8))

        viewModel = getViewModel<FavoritesViewModel, FavoritesViewModelFactory>()

        viewModel.stateFavorite.observe(viewLifecycleOwner) {
            render(it)
        }

        viewModel.getFavoriteConcerts()
    }

    override fun onClick(upcomingViewData: UpcomingViewData) {
        Navigator.Builder()
            .to(ScreenRender.Type.CONCERT_DETAIL_SCREEN)
            .with(upcomingViewData.asParcelable())
            .build()
            .navigateTo()
    }

    companion object {
        fun newInstance() = FavoritesFragment()
    }
}
