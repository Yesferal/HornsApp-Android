/* Copyright © 2025 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.lineup

import android.os.Bundle
import android.view.View
import com.yesferal.hornsapp.app.presentation.common.delegate.DelegateAdapterFragment
import com.yesferal.hornsapp.app.presentation.common.delegate.DelegateViewState
import com.yesferal.hornsapp.app.presentation.common.extension.timeFormatted
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.TitleViewData
import com.yesferal.hornsapp.core.domain.navigator.Parameters
import com.yesferal.hornsapp.hadi_android.getViewModel

class StageLineupFragment : DelegateAdapterFragment(), TitleViewData.Listener {

    private lateinit var viewModel: LineupViewModel
    // TODO: Make ID dynamic
    private val ID = "67e61d62c644dc0fa6d3f8ec"

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            getViewModel<LineupViewModel, LineupViewModelFactory>(param = ID)

        val position = arguments?.getInt(KEY_POSITION, 0) ?: 0
        viewModel.state.observe(viewLifecycleOwner) {
            render(DelegateViewState(it.stages?.get(position)?.performances?.map { performance ->
                val description = performance.startTimeInMillis.timeFormatted() + " - " + (performance.startTimeInMillis?.plus(
                    ((performance.duration ?: 60)* 60 * 1000)
                )).timeFormatted()
                TitleViewData(performance.title, description, null, "clock")
            }))
        }
    }

    companion object {
        private val KEY_POSITION = "position"

        fun newInstance(position: Int?): StageLineupFragment {
            val stageLineupFragment = StageLineupFragment()

            val args = Bundle()
            args.putInt(KEY_POSITION, position ?: 0)
            stageLineupFragment.setArguments(args)
            return stageLineupFragment
        }
    }

    override fun onClick(parameters: Parameters) {

    }
}
