/* Copyright © 2025 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.lineup

import android.os.Bundle
import android.view.View
import com.yesferal.hornsapp.app.framework.logger.ChainLoggerProvider
import com.yesferal.hornsapp.app.presentation.common.delegate.DelegateAdapterFragment
import com.yesferal.hornsapp.app.presentation.common.delegate.DelegateViewState
import com.yesferal.hornsapp.app.presentation.common.extension.timeFormatted
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.TitleViewData
import com.yesferal.hornsapp.core.domain.navigator.Navigator
import com.yesferal.hornsapp.core.domain.navigator.Parameters
import com.yesferal.hornsapp.delegate.abstraction.Delegate
import com.yesferal.hornsapp.hadi_android.getViewModel

class StageLineupFragment : DelegateAdapterFragment(), TitleViewData.Listener,
    LineupPerformanceViewData.Listener {

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

        // TODO: Fix/Clean this logic
        viewModel.state.observe(viewLifecycleOwner) {
            var lineupStartTime = 1743253200000
            val delegates = mutableListOf<Delegate>()
            val stage = it.stages?.get(position)
            delegates.add(TitleViewData(stage?.title.orEmpty(), null, null, null))

            // TODO : Clean up this mess
            stage?.performances?.map { performance ->
                val description =
                    performance.startTimeInMillis.timeFormatted() + " - " + (performance.startTimeInMillis?.plus(
                        ((performance.duration ?: 60) * 60 * 1000)
                    )).timeFormatted()
                if (lineupStartTime < (performance.startTimeInMillis ?: 0)) {
                    delegates.add(
                        LineupEmptyViewData(
                            (performance.startTimeInMillis?.minus(
                                lineupStartTime
                            ))?.toInt()?.div(60000)
                        )
                    )
                }

                lineupStartTime =
                    performance.startTimeInMillis?.plus(((performance.duration ?: 60) * 60 * 1000))
                        ?: 0

                delegates.add(
                    LineupPerformanceViewData(
                        performance.title,
                        description,
                        performance.startTimeInMillis,
                        performance.duration,
                        false
                    )
                )
            }

            render(DelegateViewState(delegates.toList()))
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
        Navigator.Builder()
            .to(parameters.key.orEmpty())
            .with(parameters)
            .build()
            .navigateTo()
    }
}
