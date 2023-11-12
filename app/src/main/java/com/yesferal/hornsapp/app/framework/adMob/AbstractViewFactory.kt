/* Copyright © 2021 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.adMob

import android.content.Context
import android.view.View

/**
 * This class follow the Abstract Factory Pattern
 *
 * @author Yesferal
 */
interface AbstractViewFactory {
    enum class Type {
        MAIN
    }

    fun drawView(context: Context, type: Type, size: Int): View
}
