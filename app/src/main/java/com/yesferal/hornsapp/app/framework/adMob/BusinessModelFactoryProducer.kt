/* Copyright © 2021 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.adMob

/**
 * This class follow the Abstract Factory Pattern
 *
 * @author Yesferal
 */
class BusinessModelFactoryProducer(private val adUnitIds: AdUnitIds,
                                   private val businessModel: BusinessModel) {

    enum class BusinessModel {
        PREMIUM,
        FREE
    }

    fun getViewFactory(): AbstractViewFactory {
        return when (businessModel) {
            BusinessModel.FREE -> FreeViewFactory(adUnitIds)
            BusinessModel.PREMIUM -> PremiumViewFactory()
        }
    }
}
