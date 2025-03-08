/* Copyright © 2025 Yesferal Cueva. All rights reserved. */

package com.yesferal.hornsapp.app.framework.retrofit.entity

import com.yesferal.hornsapp.core.domain.entity.State
import com.yesferal.hornsapp.core.domain.entity.util.LocalizedString

data class GetState(
    val _id: String,
    val name: LocalizedString?,
    val description: LocalizedString?,
) {
    fun mapToState(): State {
        return State(this._id, this.name?.text, this.description?.text)
    }
}
