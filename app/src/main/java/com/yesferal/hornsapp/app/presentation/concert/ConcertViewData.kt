package com.yesferal.hornsapp.app.presentation.concert

import com.yesferal.hornsapp.app.presentation.common.ViewData
import com.yesferal.hornsapp.domain.entity.Concert

data class ConcertViewData(
    val list: List<Concert>
) : ViewData()