package com.yesferal.hornsapp.app.presentation.concert

import com.yesferal.hornsapp.app.presentation.common.ViewData
import com.yesferal.hornsapp.domain.entity.Concert

data class ConcertsViewData(
    val concerts: List<Concert>
) : ViewData()