/* Copyright © 2025 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.lineup

import com.yesferal.hornsapp.core.domain.entity.Lineup

interface LineupDataSource {
    fun getLineup(): Lineup?
}
