/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.review

import android.os.Bundle
import android.view.View
import com.yesferal.hornsapp.app.presentation.common.custom.RecyclerViewVerticalDecorator
import com.yesferal.hornsapp.app.presentation.common.delegate.DelegateAdapterFragment
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.TitleViewData
import com.yesferal.hornsapp.core.domain.navigator.Navigator
import com.yesferal.hornsapp.core.domain.navigator.Parameters
import com.yesferal.hornsapp.hadi_android.getViewModel

class ReviewFragment : DelegateAdapterFragment(), TitleViewData.Listener, TitleReviewViewData.Listener {
    private lateinit var viewModel: ReviewViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        delegateRecyclerView.addItemDecoration(RecyclerViewVerticalDecorator())
        viewModel = getViewModel<ReviewViewModel, ReviewViewModelFactory>()
        viewModel.stateReview.observe(viewLifecycleOwner) {
            render(it)
        }
    }

    companion object {
        fun newInstance() = ReviewFragment()
    }

    override fun onClick(parameters: Parameters) {
        Navigator.Builder()
            .to(parameters.key.orEmpty())
            .with(parameters)
            .build()
            .navigateTo()
    }

    override fun onCloseClick() {
        activity?.onBackPressed()
    }
}
