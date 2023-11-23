/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.framework.adMob.BusinessModelFactoryProducer
import com.yesferal.hornsapp.app.presentation.common.delegate.DelegateViewState
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.TitleViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.ErrorViewData
import com.yesferal.hornsapp.core.domain.entity.drawer.ViewDrawer
import com.yesferal.hornsapp.core.domain.usecase.GetReviewUseCase
import com.yesferal.hornsapp.core.domain.util.HaResult
import com.yesferal.hornsapp.delegate.abstraction.Delegate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReviewViewModel(
    id: String,
    private val businessModelFactoryProducer: BusinessModelFactoryProducer,
    private val getReviewUseCase: GetReviewUseCase
    ) : ViewModel() {

    private val _stateReview = MutableLiveData<DelegateViewState>()
    val stateReview: LiveData<DelegateViewState>
        get() = _stateReview

    init {
        viewModelScope.launch {
            val stateReview = withContext(Dispatchers.IO) {
                when (val result = getReviewUseCase(id)) {
                    is HaResult.Success -> {
                        val delegates = mutableListOf<Delegate>()
                        result.value.views?.forEach {
                            when(it.type) {
                                ViewDrawer.Type.TITLE_REVIEW_CARD_VIEW -> {
                                    delegates.add(TitleReviewViewData(it.data?.title?.text))
                                }
                                ViewDrawer.Type.SUBTITLE_REVIEW_CARD_VIEW -> {
                                    delegates.add(TitleViewData(
                                        it.data?.title?.text,
                                        it.data?.subtitle?.text,
                                        it.navigation,
                                        it.data?.icon
                                    ))
                                }
                                ViewDrawer.Type.IMAGE_REVIEW_CARD_VIEW -> {
                                    delegates.add(ImageReviewViewData(it.data?.imageUrl, it.data?.description?.text))
                                }
                                ViewDrawer.Type.DESCRIPTION_REVIEW_CARD_VIEW -> {
                                    delegates.add(DescriptionReviewViewData(it.data?.description?.text))
                                }
                                ViewDrawer.Type.BUTTON_CARD_VIEW -> {
                                    delegates.add(RenderButtonViewData(it.data?.title?.text, it.navigation))
                                }
                                else -> { }
                            }
                        }

                        return@withContext DelegateViewState(delegates)
                    }
                    is HaResult.Error -> {
                        return@withContext DelegateViewState(
                            delegates = listOf(
                                ErrorViewData(
                                    R.drawable.ic_music_note,
                                    R.string.error_default
                                )
                            )
                        )
                    }
                }
            }

            _stateReview.value = stateReview
        }
    }
}

class ReviewViewModelFactory(
    private val id: String,
    private val businessModelFactoryProducer: BusinessModelFactoryProducer,
    private val getReviewUseCase: GetReviewUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            String::class.java,
            BusinessModelFactoryProducer::class.java,
            GetReviewUseCase::class.java
        ).newInstance(
            id,
            businessModelFactoryProducer,
            getReviewUseCase
        )
    }
}
