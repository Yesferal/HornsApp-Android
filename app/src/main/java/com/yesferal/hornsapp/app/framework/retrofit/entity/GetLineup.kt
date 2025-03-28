/* Copyright © 2025 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.retrofit.entity

import com.yesferal.hornsapp.core.domain.entity.Lineup
import com.yesferal.hornsapp.core.domain.entity.Performance
import com.yesferal.hornsapp.core.domain.entity.Stage
import java.util.Date

class GetLineup(
    val stages: List<GetStage>?
) {
    fun mapToLineup(): Lineup {
        return Lineup(stages = stages?.map { it.mapToStage() })
    }
}

class GetStage(
    val title: String?,
    val performances: List<GetPerformance>?
) {
    fun mapToStage(): Stage {
        return Stage(title, performances?.map { it.mapToPerformance() })
    }
}

class GetPerformance(
    val id: String?,
    val title: String?,
    val subtitle: String?,
    val start: Date?,
    val duration: Int?
) {
    fun mapToPerformance(): Performance {
        return Performance(id, title, subtitle, start?.time, duration)
    }
}
