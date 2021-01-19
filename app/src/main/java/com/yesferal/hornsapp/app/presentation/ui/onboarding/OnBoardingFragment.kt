package com.yesferal.hornsapp.app.presentation.ui.onboarding

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
import kotlinx.android.synthetic.main.fragment_on_boarding.*

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
        // TODO("Implement")
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
        // TODO("Implement")
    }

    private fun hideError() {
        // TODO("Implement")
    }
}