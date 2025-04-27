/* Copyright © 2025 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.lineup

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.yesferal.hornsapp.app.presentation.common.delegate.DelegateAdapterFragment
import com.yesferal.hornsapp.app.presentation.ui.home.TitleViewData
import com.yesferal.hornsapp.app.presentation.ui.screen_render.TitleReviewViewData
import com.yesferal.hornsapp.core.domain.entity.render.NavigatorRender
import com.yesferal.hornsapp.core.domain.navigator.Navigator
import com.yesferal.hornsapp.hadi_android.getViewModel

class LineupFragment : DelegateAdapterFragment(), TitleReviewViewData.Listener,
    TitleViewData.Listener, LineupPerformanceViewData.Listener {

    private val args: LineupFragmentArgs by navArgs()
    private lateinit var viewModel: LineupViewModel

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val lineup = args.lineup
        if (lineup?.id == null) {
            activity?.onBackPressedDispatcher?.onBackPressed()
            return
        }

        viewModel =
            getViewModel<LineupViewModel, LineupViewModelFactory>(param = lineup.id)

        viewModel.state.observe(viewLifecycleOwner) {
            render(it)
        }
    }

    override fun onClick(navigatorRender: NavigatorRender) {
        Navigator.Builder()
            .to(navigatorRender.key.orEmpty())
            .with(navigatorRender)
            .build()
            .navigateTo()
    }

    override fun onCloseClick() {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }

    companion object {
        fun newInstance() = LineupFragment()
    }
}
