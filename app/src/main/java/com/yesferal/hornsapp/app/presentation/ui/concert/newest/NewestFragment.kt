package com.yesferal.hornsapp.app.presentation.ui.concert.newest

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.ParcelableViewData
import com.yesferal.hornsapp.app.presentation.common.custom.RecyclerViewVerticalDecorator
import com.yesferal.hornsapp.app.presentation.common.delegate.DelegateAdapterFragment
import com.yesferal.hornsapp.app.presentation.di.hada.getViewModel
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewData
import com.yesferal.hornsapp.app.presentation.ui.home.HomeFragmentDirections
import java.net.URI

class NewestFragment : DelegateAdapterFragment(), NewestViewData.Listener,
    CarouselViewData.Listener, UpcomingViewData.Listener {

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

    override fun onTicketingClick(ticketingUrl: URI?) {
        startExternalActivity(ticketingUrl)
    }

    override fun onClick(upcomingViewData: UpcomingViewData) {
        startConcertActivity(upcomingViewData.asParcelable())
    }

    private fun startConcertActivity(parcelableViewData: ParcelableViewData) {
        findNavController().navigate(
            HomeFragmentDirections
                .actionHomeToConcert(parcelableViewData)
        )
    }

    companion object {
        fun newInstance() = NewestFragment()
    }
}
