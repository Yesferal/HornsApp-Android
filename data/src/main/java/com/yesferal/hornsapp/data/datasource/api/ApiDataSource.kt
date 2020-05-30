package com.yesferal.hornsapp.data.datasource.api

import com.yesferal.hornsapp.data.datasource.api.response.GetConcerts

interface ApiDataSource {
    fun getConcerts(
        onSuccess: (entity: List<GetConcerts>) -> Unit,
        onError: (t: Throwable) -> Unit
    )
}