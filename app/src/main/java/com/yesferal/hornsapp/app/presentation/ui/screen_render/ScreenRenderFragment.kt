/* Copyright © 2025 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.screen_render

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.yesferal.hornsapp.app.presentation.common.custom.RecyclerViewVerticalDecorator
import com.yesferal.hornsapp.app.presentation.common.delegate.DelegateAdapterFragment
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.IconHomeCardViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.ImageHomeCardViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.TitleViewData
import com.yesferal.hornsapp.core.domain.navigator.Navigator
import com.yesferal.hornsapp.core.domain.navigator.Parameters
import com.yesferal.hornsapp.hadi_android.getViewModel

class ScreenRenderFragment : DelegateAdapterFragment(), TitleViewData.Listener,
    TitleReviewViewData.Listener, RenderButtonViewData.Listener, IconHomeCardViewData.Listener,
    ImageHomeCardViewData.Listener {
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
            // TODO: Get this value from Home Screen
            "67e6e5d4c644dc0fa6d3f900"
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
        fun newInstance() = ScreenRenderFragment()
    }
}
