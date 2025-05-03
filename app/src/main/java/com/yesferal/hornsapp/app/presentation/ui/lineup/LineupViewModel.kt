/* Copyright © 2025 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.lineup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.delegate.ColumnDelegate
import com.yesferal.hornsapp.app.presentation.common.delegate.DelegateViewState
import com.yesferal.hornsapp.app.presentation.common.extension.timeFormatted
import com.yesferal.hornsapp.app.presentation.ui.home.TitleViewData
import com.yesferal.hornsapp.app.presentation.ui.screen_render.TitleReviewViewData
import com.yesferal.hornsapp.core.domain.usecase.LineupUseCase
import com.yesferal.hornsapp.core.domain.util.HaResult
import com.yesferal.hornsapp.delegate.abstraction.Delegate
import com.yesferal.hornsapp.delegate.delegate.RowDelegate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LineupViewModel(
    id: String,
    private val lineupUseCase: LineupUseCase,
) : ViewModel() {
    private val _state = MutableLiveData<DelegateViewState>()

    val state: LiveData<DelegateViewState>
        get() = _state

    init {
        viewModelScope.launch {
            val state = withContext(Dispatchers.IO) {
                when (val result = lineupUseCase.getLineup(id)) {
                    is HaResult.Success -> {
                        val screenDelegates = mutableListOf<Delegate>()
                        screenDelegates.add(TitleReviewViewData(result.value.title))

                        val columnDelegates = mutableListOf<Delegate>()

                        val firstDailyLineup = result.value.days?.firstOrNull()

                        val dateTimeInMillis = firstDailyLineup?.dateTimeInMillis
                            ?: return@withContext DelegateViewState(screenDelegates)

                        firstDailyLineup.stages?.forEach { stage ->
                            // TODO : Clean up this mess
                            var lineupStartTime = dateTimeInMillis
                            val delegates = mutableListOf<Delegate>()
                            delegates.add(TitleViewData(stage.title.orEmpty(), null, null, null))
                            stage.activities?.forEach { performance ->
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
                                    .addBackground(R.color.divider).build()
                            )
                        }

                        screenDelegates.add(
                            RowDelegate.Builder().addItems(columnDelegates).build()
                        )

                        return@withContext DelegateViewState(screenDelegates)
                    }

                    is HaResult.Error -> {
                        return@withContext DelegateViewState.showDelegateViewStateError()
                    }
                }
            }
            _state.value = state
        }
    }
}

class LineupViewModelFactory(
    private val id: String,
    private val lineupUseCase: LineupUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            String::class.java,
            LineupUseCase::class.java,
        ).newInstance(
            id,
            lineupUseCase,
        )
    }
}
