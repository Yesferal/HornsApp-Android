package com.yesferal.hornsapp.app.presentation.concerts

import com.yesferal.hornsapp.app.presentation.common.base.BasePresenter
import com.yesferal.hornsapp.app.presentation.common.base.State
import com.yesferal.hornsapp.domain.usecase.GetConcertsUseCase

class ConcertsPresenter(
    private val getConcertsUseCase: GetConcertsUseCase
) : BasePresenter<ConcertsContract.View>(),
    ConcertsContract.ActionListener {
    
    override fun onCreate() {
        getConcertsUseCase(
            onSuccess = { viewData ->
                val stateSuccess = State.Success(viewData)
                getView()?.updateWith(stateSuccess)
            },
            onError = {
                // TODO: ErrorHandler
            }
        )
    }
}