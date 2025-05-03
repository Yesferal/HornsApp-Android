/* Copyright © 2025 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.retrofit.entity

import com.yesferal.hornsapp.core.domain.entity.DailyLineup
import com.yesferal.hornsapp.core.domain.entity.Lineup
import com.yesferal.hornsapp.core.domain.entity.LineupActivity
import com.yesferal.hornsapp.core.domain.entity.Stage
import com.yesferal.hornsapp.core.domain.entity.util.LocalizedString
import java.util.Date

class GetLineup(
    val title: LocalizedString?,
    val days: List<GetDailyLineup>?
) {
    fun mapToLineup(): Lineup {
        return Lineup(title = title?.text, days = days?.map { it.mapToDailyLineup() })
    }
}

class GetDailyLineup(
    val dateTime: Date?,
    val stages: List<GetStage>?
) {
    fun mapToDailyLineup(): DailyLineup {
        return DailyLineup(dateTimeInMillis = dateTime?.time, stages = stages?.map { it.mapToStage() })
    }
}

class GetStage(
    val title: String?,
    val activities: List<GetLineupActivity>?
) {
    fun mapToStage(): Stage {
        return Stage(title, activities?.map { it.mapToEvent() })
    }
}

class GetLineupActivity(
    val id: String?,
    val title: String?,
    val subtitle: String?,
    val start: Date?,
    val duration: Int?
) {
    fun mapToEvent(): LineupActivity {
        return LineupActivity(id, title, subtitle, start?.time, duration)
    }
}
