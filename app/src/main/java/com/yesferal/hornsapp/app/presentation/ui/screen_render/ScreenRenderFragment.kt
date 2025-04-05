/* Copyright © 2025 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.screen_render

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.yesferal.hornsapp.app.presentation.common.base.ExternalNavViewData
import com.yesferal.hornsapp.app.presentation.common.base.ParcelableViewData
import com.yesferal.hornsapp.app.presentation.common.custom.RecyclerViewVerticalDecorator
import com.yesferal.hornsapp.app.presentation.common.delegate.DelegateAdapterFragment
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.CarouselViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.IconHomeCardViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.ImageHomeCardViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.NewestViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.TitleViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewData
import com.yesferal.hornsapp.core.domain.entity.render.ScreenRender
import com.yesferal.hornsapp.core.domain.navigator.Navigator
import com.yesferal.hornsapp.core.domain.navigator.Parameters
import com.yesferal.hornsapp.hadi_android.getViewModel

class ScreenRenderFragment : DelegateAdapterFragment(), TitleViewData.Listener,
    TitleReviewViewData.Listener, RenderButtonViewData.Listener, IconHomeCardViewData.Listener,
    ImageHomeCardViewData.Listener, NewestViewData.Listener,
    CarouselViewData.Listener, UpcomingViewData.Listener {
    private lateinit var viewModel: ScreenRenderViewModel
    private val args: ScreenRenderFragmentArgs? by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val screen = try {
             args?.screen
        } catch (e: Exception) {
            null
        }
        val screenId = if (screen?.id == null) {
            arguments?.getString(KEY_ID, null)
        } else {
            screen.id
        }

        if (screenId == null) {
            activity?.onBackPressedDispatcher?.onBackPressed()
            return
        }

        delegateRecyclerView.addItemDecoration(RecyclerViewVerticalDecorator())
        viewModel =
            getViewModel<ScreenRenderViewModel, ScreenRenderViewModelFactory>(param = screenId)
        viewModel.stateReview.observe(viewLifecycleOwner) {
            render(it)
        }
    }

    override fun onClick(parameters: Parameters) {
        Navigator.Builder()
            .to(parameters.key.orEmpty())
            .with(parameters)
            .build()
            .navigateTo()
    }

    override fun onCloseClick() {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }

    companion object {
        private val KEY_ID = "key_id"

        fun newInstance(id: String?): ScreenRenderFragment {
            val fragment = ScreenRenderFragment()

            val args = Bundle()
            args.putString(KEY_ID, id)
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun onClick(newestViewData: NewestViewData) {
        startConcertActivity(newestViewData.asParcelable())
    }

    override fun onClick(carouselViewData: CarouselViewData) {
        startConcertActivity(carouselViewData.asParcelable())
    }

    override fun onTicketingClick(ticketingUrl: String) {
        startExternalActivity(ExternalNavViewData(ticketingUrl))
    }

    override fun onClick(upcomingViewData: UpcomingViewData) {
        startConcertActivity(upcomingViewData.asParcelable())
    }

    private fun startConcertActivity(parcelableViewData: ParcelableViewData) {
        Navigator.Builder()
            .to(ScreenRender.Type.CONCERT_DETAIL_SCREEN)
            .with(parcelableViewData)
            .build()
            .navigateTo()
    }
}
