package com.yesferal.hornsapp.app.presentation.ui.concert.favorite

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.yesferal.hornsapp.app.presentation.common.custom.RecyclerViewVerticalDecorator
import com.yesferal.hornsapp.app.presentation.common.delegate.DelegateAdapterFragment
import com.yesferal.hornsapp.app.presentation.di.hada.getViewModel
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewData
import com.yesferal.hornsapp.app.presentation.ui.home.HomeFragmentDirections

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
        findNavController().navigate(
            HomeFragmentDirections.actionHomeToConcert(upcomingViewData.asParcelable())
        )
    }

    companion object {
        fun newInstance() = FavoritesFragment()
    }
}
