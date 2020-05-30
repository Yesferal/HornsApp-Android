package com.yesferal.hornsapp.domain.usecase

interface BaseUseCase<P: Any, R: Any> {
    operator fun invoke(
        param: P? = null,
        onSuccess: (response: R) -> Unit,
        onError: (t: Throwable) -> Unit
    )
}