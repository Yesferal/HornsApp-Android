/* Copyright © 2025 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.lineup

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.presentation.common.delegate.DelegateAdapterFragment
import com.yesferal.hornsapp.app.presentation.common.delegate.DelegateViewState
import com.yesferal.hornsapp.app.presentation.common.extension.timeFormatted
import com.yesferal.hornsapp.app.presentation.ui.home.TitleViewData
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

        // TODO: Fix/Clean this logic
        viewModel.state.observe(viewLifecycleOwner) {
            val columnDelegates = mutableListOf<Delegate>()

            it.stages?.forEach { stage ->
                // TODO: Use first event time instead
                var lineupStartTime = 1743253200000
                // TODO : Clean up this mess
                val delegates = mutableListOf<Delegate>()
                delegates.add(TitleViewData(stage.title.orEmpty(), null, null, null))
                stage.performances?.forEach { performance ->
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
                        performance.startTimeInMillis?.plus(
                            ((performance.duration ?: 60) * 60 * 1000)
                        )
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

                columnDelegates.add(
                    ColumnDelegate.Builder().addItems(delegates)
                        .addElevation(4F).build()
                )
            }
            render(DelegateViewState(columnDelegates))
        }
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onClick(parameters: Parameters) {
        Navigator.Builder()
            .to(parameters.key.orEmpty())
            .with(parameters)
            .build()
            .navigateTo()
    }

    companion object {
        fun newInstance() = StageLineupFragment()
    }
}
