package com.yesferal.hornsapp.app.presentation.ui.onboarding

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.BaseFragment
import com.yesferal.hornsapp.app.presentation.common.custom.fadeIn
import com.yesferal.hornsapp.app.presentation.common.custom.fadeOut
import com.yesferal.hornsapp.app.presentation.ui.home.HomeViewModel
import com.yesferal.hornsapp.app.presentation.ui.home.HomeViewModelFactory
import kotlinx.android.synthetic.main.fragment_concert.*
import kotlinx.android.synthetic.main.fragment_on_boarding.*
import java.net.URI

class OnBoardingFragment
    : BaseFragment <OnBoardingViewState>() {
    private lateinit var homeViewModel: HomeViewModel

    override val layout: Int
        get() = R.layout.fragment_on_boarding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.viewModelStore?.let { viewModelStore ->
            homeViewModel = ViewModelProvider(
                viewModelStore,
                container.resolve<HomeViewModelFactory>()
            ).get(HomeViewModel::class.java)

            homeViewModel.state.observe(viewLifecycleOwner) {
                homeViewModel.getOnBoardingData()
            }

            homeViewModel.stateOnBoarding.observe(viewLifecycleOwner) {
                render(it)
            }
        }

        nextTextView.setOnClickListener {
            findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingToHome())
        }
    }

    override fun render(viewState: OnBoardingViewState) {
        viewState.onBoardingViewData?.let {
            showData(it)
        }

        viewState.errorMessage?.let {
            showError(
                messageId =  viewState.errorMessage
            )
        }?: kotlin.run { hideError() }

        if (viewState.isLoading) {
            showProgress()
        } else {
            hideProgress()
        }
    }

    private fun showData(onBoardingViewData: OnBoardingViewData) {
        metalCard.setText(onBoardingViewData.metalConcerts.toString(),getString(R.string.metal_category))
        rockCard.setText(onBoardingViewData.rockConcerts.toString(),getString(R.string.rock_category))
        upcomingCard.setText(onBoardingViewData.upcomingConcerts.toString(),getString(R.string.upcoming_category))
        totalCard.setText(onBoardingViewData.total.toString(),getString(R.string.total_category))
    }

    private fun showProgress() {
        progressBar.fadeIn()
        nextTextView.fadeOut()
    }

    private fun hideProgress() {
        progressBar.fadeOut()
        nextTextView.fadeIn()
    }

    private fun showError(
        @StringRes messageId: Int
    ) {
    }

    private fun hideError() {
    }
}