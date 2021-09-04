package com.yesferal.hornsapp.data.abstraction.remote

import com.yesferal.hornsapp.domain.common.Result
import com.yesferal.hornsapp.domain.entity.Band

interface BandRemoteDataSource {
    suspend fun getBand(id: String): Result<Band>
}
