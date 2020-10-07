package com.yesferal.hornsapp.domain.abstraction

import com.yesferal.hornsapp.domain.entity.Band

interface BandRepository {
    fun getBand(
        id: String,
        onSuccess: (band: Band) -> Unit,
        onError: (t: Throwable) -> Unit
    )
}