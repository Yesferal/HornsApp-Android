package com.yesferal.hornsapp.app.presentation.ui.onboarding

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.extension.fadeIn
import com.yesferal.hornsapp.app.presentation.common.extension.fadeOut
import com.yesferal.hornsapp.app.presentation.common.render.RenderFragment
import com.yesferal.hornsapp.core.domain.navigator.ScreenType
import com.yesferal.hornsapp.delegate.DelegateAdapter
import com.yesferal.hornsapp.delegate.abstraction.Delegate
import com.yesferal.hornsapp.hadi_android.getViewModel

class OnBoardingFragment : RenderFragment<OnBoardingViewState>() {

    override val layout = R.layout.fragment_on_boarding

    private lateinit var onBoardingViewModel: OnBoardingViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var nextTextView: TextView
    private lateinit var progressBar: ProgressBar

    private lateinit var delegateAdapter: DelegateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delegateAdapter = DelegateAdapter.Builder()
            .build()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBoardingViewModel = getViewModel<OnBoardingViewModel, OnBoardingViewModelFactory>()

        recyclerView = view.findViewById(R.id.recyclerView)
        nextTextView = view.findViewById(R.id.nextTextView)
        progressBar = view.findViewById(R.id.progressBar)

        recyclerView.also {
            it.adapter = delegateAdapter
            it.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }

        onBoardingViewModel.state.observe(viewLifecycleOwner) {
            render(it)
        }

        nextTextView.setOnClickListener {
            onBoardingViewModel.updateVisibilityOnBoarding()
            navigator.navigate(this, ScreenType.Home.asDirection())
        }
    }

    override fun render(viewState: OnBoardingViewState) {
        viewState.categories?.let {
            showData(it)
        }

        if (viewState.isLoading) {
            showProgress()
        } else {
            hideProgress()
        }
    }

    private fun showData(onBoardingViewData: List<Delegate>) {
        delegateAdapter.updateDelegates(onBoardingViewData)
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
