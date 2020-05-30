package com.yesferal.hornsapp.data.abstraction

import com.yesferal.hornsapp.data.repository.entity.GetConcerts

interface ApiDataSource {
    fun getConcerts(
        onSuccess: (entity: List<GetConcerts>) -> Unit,
        onError: (t: Throwable) -> Unit
    )
}