/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.concert.newest

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.ExternalNavViewData
import com.yesferal.hornsapp.app.presentation.common.base.ParcelableViewData
import com.yesferal.hornsapp.app.presentation.common.custom.RecyclerViewVerticalDecorator
import com.yesferal.hornsapp.app.presentation.common.delegate.DelegateAdapterFragment
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewData
import com.yesferal.hornsapp.core.domain.navigator.Direction
import com.yesferal.hornsapp.core.domain.navigator.NavViewData
import com.yesferal.hornsapp.core.domain.navigator.ScreenType
import com.yesferal.hornsapp.hadi_android.getViewModel

class NewestFragment : DelegateAdapterFragment(), NewestViewData.Listener,
    CarouselViewData.Listener, UpcomingViewData.Listener, HomeCardViewData.Listener,
    TitleViewData.Listener {

    private lateinit var viewModel: NewestViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.divider))

        delegateRecyclerView.addItemDecoration(RecyclerViewVerticalDecorator())

        viewModel = getViewModel<NewestViewModel, NewestViewModelFactory>()

        viewModel.stateNewest.observe(viewLifecycleOwner) {
            render(it)
        }
    }

    override fun onClick(carouselViewData: CarouselViewData) {
        startConcertActivity(carouselViewData.asParcelable())
    }

    override fun onClick(newestViewData: NewestViewData) {
        startConcertActivity(newestViewData.asParcelable())
    }

    override fun onTicketingClick(ticketingUrl: String) {
        startExternalActivity(ExternalNavViewData(ticketingUrl))
    }

    override fun onClick(upcomingViewData: UpcomingViewData) {
        startConcertActivity(upcomingViewData.asParcelable())
    }

    override fun onClick(homeCardViewData: HomeCardViewData) {
        navigateToStringDirection(homeCardViewData.deeplink, homeCardViewData.asParcelable())
    }

    override fun onClick(titleViewData: TitleViewData) {
        navigateToStringDirection(titleViewData.deeplink, titleViewData.asParcelable())
    }

    private fun navigateToStringDirection(deeplink: String?, navViewData: NavViewData?) {
        Direction.Build()
            .to(deeplink.orEmpty())
            .with(navViewData)
            .build()
            .navigateTo()
    }

    private fun startConcertActivity(parcelableViewData: ParcelableViewData) {
        Direction.Build()
            .to(ScreenType.CONCERT_DETAIL)
            .with(parcelableViewData)
            .build()
            .navigateTo()
    }

    companion object {
        fun newInstance() = NewestFragment()
    }
}
