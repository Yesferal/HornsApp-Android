package com.yesferal.hornsapp.app.presentation.ui.concert.upcoming

import android.os.Bundle
import android.view.View
import com.yesferal.hornsapp.app.presentation.common.custom.RecyclerViewVerticalDecorator
import com.yesferal.hornsapp.app.presentation.common.delegate.DelegateAdapterFragment
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.filters.CategoryViewData
import com.yesferal.hornsapp.core.domain.navigator.Direction
import com.yesferal.hornsapp.core.domain.navigator.ScreenType
import com.yesferal.hornsapp.hadi_android.getViewModel

class UpcomingFragment : DelegateAdapterFragment(), CategoryViewData.Listener,
    UpcomingViewData.Listener {

    lateinit var viewModel: UpcomingViewModel

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        delegateRecyclerView.addItemDecoration(RecyclerViewVerticalDecorator(paddingBottom = 8))

        viewModel = getViewModel<UpcomingViewModel, UpcomingViewModelFactory>()

        viewModel.stateUpcoming.observe(viewLifecycleOwner) {
            render(it)
        }
    }

    override fun onClick(upcomingViewData: UpcomingViewData) {
        Direction.Build()
            .to(ScreenType.CONCERT_DETAIL)
            .with(upcomingViewData.asParcelable())
            .build()
            .navigateTo()
    }

    override fun onClick(categoryViewData: CategoryViewData) {
        viewModel.onCategoryClick(categoryViewData)
    }

    companion object {
        fun newInstance() = UpcomingFragment()
    }
}
