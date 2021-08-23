package com.yesferal.hornsapp.app.presentation.ui.onboarding

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.custom.TextSubTextView
import com.yesferal.hornsapp.app.presentation.common.extension.fadeIn
import com.yesferal.hornsapp.app.presentation.common.extension.fadeOut
import com.yesferal.hornsapp.app.presentation.common.render.RenderFragment
import com.yesferal.hornsapp.app.presentation.di.hada.getViewModel

class OnBoardingFragment : RenderFragment<OnBoardingViewState>() {

    override val layout = R.layout.fragment_on_boarding

    private lateinit var onBoardingViewModel: OnBoardingViewModel

    private lateinit var nextTextView: TextView
    private lateinit var metalCard: TextSubTextView
    private lateinit var rockCard: TextSubTextView
    private lateinit var upcomingCard: TextSubTextView
    private lateinit var totalCard: TextSubTextView
    private lateinit var progressBar: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBoardingViewModel = getViewModel<OnBoardingViewModel, OnBoardingViewModelFactory>()

        nextTextView = view.findViewById(R.id.nextTextView)
        metalCard = view.findViewById(R.id.metalCard)
        rockCard = view.findViewById(R.id.rockCard)
        upcomingCard = view.findViewById(R.id.upcomingCard)
        totalCard = view.findViewById(R.id.totalCard)
        progressBar = view.findViewById(R.id.progressBar)

        onBoardingViewModel.state.observe(viewLifecycleOwner) {
            render(it)
        }

        nextTextView.setOnClickListener {
            onBoardingViewModel.updateVisibilityOnBoarding()
            findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingToHome())
        }
    }

    override fun render(viewState: OnBoardingViewState) {
        viewState.onBoardingViewData?.let {
            showData(it)
        }

        if (viewState.isLoading) {
            showProgress()
        } else {
            hideProgress()
        }
    }

    private fun showData(onBoardingViewData: OnBoardingViewData) {
        metalCard.setText(
            onBoardingViewData.metalConcerts.toString(),
            getString(R.string.metal_category)
        )
        rockCard.setText(
            onBoardingViewData.rockConcerts.toString(),
            getString(R.string.rock_category)
        )
        upcomingCard.setText(
            onBoardingViewData.upcomingConcerts.toString(),
            getString(R.string.upcoming_category)
        )
        totalCard.setText(onBoardingViewData.total.toString(), getString(R.string.total_category))
    }

    private fun showProgress() {
        progressBar.fadeIn()
        nextTextView.fadeOut()
    }

    private fun hideProgress() {
        progressBar.fadeOut()
        nextTextView.fadeIn()
    }
}
