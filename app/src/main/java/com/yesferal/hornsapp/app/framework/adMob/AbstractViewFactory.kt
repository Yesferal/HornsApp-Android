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
    fun drawView(context: Context, type: AdUnitIds.Type, size: Int): View
}
