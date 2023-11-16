/* Copyright © 2021 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.adMob

import android.content.Context
import android.view.View

/**
 * This class follow the Factory Pattern & it is also part of the Abstract Factory Pattern
 *
 * @author Yesferal
 */
class PremiumViewFactory: AbstractViewFactory {
    override fun drawView(context: Context, type: AdUnitIds.Type, size: Int): View {
        return View(context)
    }
}
