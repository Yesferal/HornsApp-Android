package com.yesferal.hornsapp.app.presentation.concert.detail

import com.yesferal.hornsapp.app.presentation.common.ViewData
import com.yesferal.hornsapp.domain.entity.Concert

data class ConcertViewData(
    val concert: Concert
) : ViewData()