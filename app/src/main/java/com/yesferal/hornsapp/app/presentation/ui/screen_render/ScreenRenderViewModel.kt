/* Copyright © 2025 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.screen_render

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.framework.adMob.BusinessModelFactoryProducer
import com.yesferal.hornsapp.app.presentation.common.delegate.DelegateViewState
import com.yesferal.hornsapp.app.presentation.common.extension.includeAdViewSection
import com.yesferal.hornsapp.app.presentation.common.extension.includeCarouselSection
import com.yesferal.hornsapp.app.presentation.common.extension.includeIconHomeCardSection
import com.yesferal.hornsapp.app.presentation.common.extension.includeImageHomeCardSection
import com.yesferal.hornsapp.app.presentation.common.extension.includeVerticalSection
import com.yesferal.hornsapp.app.presentation.ui.home.TitleViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.ErrorViewData
import com.yesferal.hornsapp.core.domain.abstraction.Logger
import com.yesferal.hornsapp.core.domain.entity.render.ViewRender
import com.yesferal.hornsapp.core.domain.usecase.GetConcertsUseCase
import com.yesferal.hornsapp.core.domain.usecase.GetReviewUseCase
import com.yesferal.hornsapp.core.domain.util.HaResult
import com.yesferal.hornsapp.delegate.abstraction.Delegate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScreenRenderViewModel(
    id: String,
    private val getReviewUseCase: GetReviewUseCase,
    private val getConcertsUseCase: GetConcertsUseCase,
    private val businessModelFactoryProducer: BusinessModelFactoryProducer,
    private val logger: Logger
) : ViewModel() {

    private val _stateReview = MutableLiveData<DelegateViewState>()
    val stateReview: LiveData<DelegateViewState>
        get() = _stateReview

    init {
        viewModelScope.launch {
            val stateReview = withContext(Dispatchers.IO) {
                var renderAd = true
                val concertsResult = getConcertsUseCase()
                val screenResult = getReviewUseCase(id)
                when (concertsResult) {
                    is HaResult.Success -> {
                        when (screenResult) {
                            is HaResult.Success -> {
                                val delegates = mutableListOf<Delegate>()
                                screenResult.value.views?.forEach {
                                    when(it.type) {
                                        ViewRender.Type.TITLE_REVIEW_CARD_VIEW -> {
                                            delegates.add(TitleReviewViewData(it.data?.title?.text))
                                        }
                                        ViewRender.Type.SUBTITLE_REVIEW_CARD_VIEW -> {
                                            delegates.add(TitleViewData(
                                                it.data?.title?.text,
                                                it.data?.subtitle?.text,
                                                it.navigation,
                                                it.data?.icon
                                            ))
                                        }
                                        ViewRender.Type.IMAGE_REVIEW_CARD_VIEW -> {
                                            delegates.add(ImageReviewViewData(it.data?.imageUrl, it.data?.description?.text))
                                        }
                                        ViewRender.Type.DESCRIPTION_REVIEW_CARD_VIEW -> {
                                            delegates.add(DescriptionReviewViewData(it.data?.description?.text))
                                        }
                                        ViewRender.Type.BUTTON_CARD_VIEW -> {
                                            delegates.add(RenderButtonViewData(it.data?.ctas?.firstOrNull()?.title?.text, it.navigation))
                                        }
                                        ViewRender.Type.ICON_CARD_VIEW -> {
                                            delegates.includeIconHomeCardSection(it)
                                        }
                                        ViewRender.Type.CARD_VIEW -> {
                                            delegates.includeImageHomeCardSection(it)
                                        }
                                        ViewRender.Type.AD_VIEW -> {
                                            renderAd = false
                                            delegates.includeAdViewSection(businessModelFactoryProducer, it)
                                        }
                                        ViewRender.Type.ROW_VIEW -> {
                                            delegates.includeCarouselSection(concertsResult.value, it)
                                        }
                                        ViewRender.Type.COLUMN_VIEW -> {
                                            delegates.includeVerticalSection(concertsResult.value, it)
                                        }
                                        else -> { }
                                    }
                                }

                                return@withContext DelegateViewState(delegates, renderAd)
                            }
                            is HaResult.Error -> {
                                return@withContext showDelegateViewStateError()
                            }
                        }
                    }
                    is HaResult.Error -> {
                        return@withContext showDelegateViewStateError()
                    }
                }
            }

            _stateReview.value = stateReview
        }
    }

    private fun showDelegateViewStateError(): DelegateViewState {
        return DelegateViewState(
            delegates = listOf(
                ErrorViewData(
                    R.drawable.ic_music_note,
                    R.string.error_default
                )
            )
        )
    }
}

class ScreenRenderViewModelFactory(
    private val id: String,
    private val getReviewUseCase: GetReviewUseCase,
    private val getConcertsUseCase: GetConcertsUseCase,
    private val businessModelFactoryProducer: BusinessModelFactoryProducer,
    private val logger: Logger,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            String::class.java,
            GetReviewUseCase::class.java,
            GetConcertsUseCase::class.java,
            BusinessModelFactoryProducer::class.java,
            Logger::class.java
        ).newInstance(
            id,
            getReviewUseCase,
            getConcertsUseCase,
            businessModelFactoryProducer,
            logger
        )
    }
}
