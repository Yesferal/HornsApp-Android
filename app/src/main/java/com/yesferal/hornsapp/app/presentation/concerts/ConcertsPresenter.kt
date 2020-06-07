package com.yesferal.hornsapp.app.presentation.concerts

import com.yesferal.hornsapp.app.presentation.base.BasePresenter
import com.yesferal.hornsapp.app.presentation.base.State
import com.yesferal.hornsapp.domain.usecase.GetConcertsUseCase

class ConcertsPresenter(
    private val getConcertsUseCase: GetConcertsUseCase
) : BasePresenter<ConcertsContract.View>(),
    ConcertsContract.ActionListener {
    
    override fun onCreate() {
        getConcertsUseCase(
            onSuccess = { viewData ->
                val success = State.Success(viewData)
                view?.updateWith(state = success)
            },
            onError = {
                // TODO: ErrorHandler
            }
        )
    }
}