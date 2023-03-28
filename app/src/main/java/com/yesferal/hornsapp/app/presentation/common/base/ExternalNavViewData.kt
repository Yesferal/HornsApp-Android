/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.common.base

import android.net.Uri
import com.yesferal.hornsapp.core.domain.navigator.NavViewData

class ExternalNavViewData(
    uri: String
) : NavViewData {
    override val id = uri
    override val name = uri
    val androidUri: Uri = Uri.parse(uri)
}
