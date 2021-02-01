package com.yesferal.hornsapp.domain.abstraction

import com.yesferal.hornsapp.domain.entity.Band
import com.yesferal.hornsapp.domain.common.Result

interface BandRepository {
    suspend fun getBand(
        id: String
    ): Result<Band>
}