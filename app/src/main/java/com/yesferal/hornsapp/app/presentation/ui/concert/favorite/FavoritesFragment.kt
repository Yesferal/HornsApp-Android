package com.yesferal.hornsapp.app.presentation.ui.concert.favorite

import android.os.Bundle
import android.view.View
import com.yesferal.hornsapp.app.presentation.common.custom.RecyclerViewVerticalDecorator
import com.yesferal.hornsapp.app.presentation.common.delegate.DelegateAdapterFragment
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewData
import com.yesferal.hornsapp.core.domain.navigator.Direction
import com.yesferal.hornsapp.core.domain.navigator.ScreenType
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
        val direction = Direction.Build()
            .to(ScreenType.ConcertDetail)
            .with(upcomingViewData.asParcelable())
            .build()
        navigator.navigate(this, direction)
    }

    companion object {
        fun newInstance() = FavoritesFragment()
    }
}
