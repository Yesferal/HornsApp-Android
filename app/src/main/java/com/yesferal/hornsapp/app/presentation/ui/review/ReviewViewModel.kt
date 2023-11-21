/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yesferal.hornsapp.app.framework.adMob.BusinessModelFactoryProducer
import com.yesferal.hornsapp.app.presentation.common.delegate.DelegateViewState
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.TitleViewData
import com.yesferal.hornsapp.core.domain.usecase.GetConcertsUseCase
import com.yesferal.hornsapp.delegate.abstraction.Delegate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReviewViewModel(
    private val businessModelFactoryProducer: BusinessModelFactoryProducer,
    private val getConcertsUseCase: GetConcertsUseCase
    ) : ViewModel() {

    private val _stateReview = MutableLiveData<DelegateViewState>()
    val stateReview: LiveData<DelegateViewState>
        get() = _stateReview

    init {
        viewModelScope.launch {
            val stateReview = withContext(Dispatchers.IO) {
                /// TODO: Replace with remote data
                val delegates = mutableListOf<Delegate>()
                delegates.add(TitleReviewViewData("Title Here"))
                delegates.add(ImageReviewViewData("https://cdn-p.smehost.net/sites/7f9737f2506941499994d771a29ad47a/wp-content/uploads/2023/10/Taylor-Mayorga-600x337.png"))
                delegates.add(DescriptionReviewViewData("Title Hasdasdadasdasdblakjsdbflkasdfkasdflkjasldfkasldkflaksjd flkasj dflk asdlkf alsdnfalksdnf knsdkfjnlaskdjnflaksjndflajsdnflaksnjdflkajsdfere"))
                delegates.add(ImageReviewViewData("https://www.brooklynvegan.com/files/2016/04/health-coachella-05.jpg"))
                delegates.add(TitleViewData("SubTitle", "Sub Subtitle", null, null))
                delegates.add(DescriptionReviewViewData("Title Hasdasdadasdasdblakjsdbflkasdfkasdflkjasldfkasldkflaksjd flkasj dflk asdlkf alsdnfalksdnf knsdkfjnlaskdjnflaksjndflajsdnflaksnjdflkajsdfere"))
                delegates.add(ImageReviewViewData("https://www.brooklynvegan.com/files/2016/04/health-coachella-05.jpg"))
                delegates.add(TitleViewData("SubTitle", "Sub Subtitle", null, null))
                delegates.add(DescriptionReviewViewData("Title Hasdasdadasdasdblakjsdbflkasdfkasdflkjasldfkasldkflaksjd flkasj dflk asdlkf alsdnfalksdnf knsdkfjnlaskdjnflaksjndflajsdnflaksnjdflkajsdfere"))

                DelegateViewState(delegates)
            }

            _stateReview.value = stateReview
        }
    }
}

class ReviewViewModelFactory(
    private val businessModelFactoryProducer: BusinessModelFactoryProducer,
    private val getConcertsUseCase: GetConcertsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            BusinessModelFactoryProducer::class.java,
            GetConcertsUseCase::class.java
        ).newInstance(businessModelFactoryProducer, getConcertsUseCase)
    }
}
