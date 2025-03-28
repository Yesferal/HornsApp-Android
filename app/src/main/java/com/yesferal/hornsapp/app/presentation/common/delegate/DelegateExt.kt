/* Copyright © 2025 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.common.delegate

import com.yesferal.hornsapp.app.framework.adMob.AdUnitIds
import com.yesferal.hornsapp.app.framework.adMob.BusinessModelFactoryProducer
import com.yesferal.hornsapp.app.presentation.common.extension.addVerticalDivider
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.AdViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.IconHomeCardViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.ImageHomeCardViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.TitleViewData
import com.yesferal.hornsapp.core.domain.entity.render.ViewRender
import com.yesferal.hornsapp.delegate.abstraction.Delegate


fun MutableList<Delegate>.includeIconHomeCardSection(
    screenDrawer: ViewRender
) {
    this.add(
        IconHomeCardViewData(
            screenDrawer.data?.title?.text,
            screenDrawer.data?.subtitle?.text,
            screenDrawer.data?.backgroundColor,
            screenDrawer.data?.textColor,
            screenDrawer.navigation,
            screenDrawer.data?.icon
        )
    )
    this.addVerticalDivider(24)
}

fun MutableList<Delegate>.includeImageHomeCardSection(
    screenDrawer: ViewRender
) {
    this.add(
        TitleViewData(
            screenDrawer.data?.title?.text,
            screenDrawer.data?.subtitle?.text,
            screenDrawer.navigation,
            screenDrawer.data?.icon
        )
    )
    this.add(
        ImageHomeCardViewData(
            screenDrawer.data?.description?.text,
            screenDrawer.navigation,
            screenDrawer.data?.imageUrl
        )
    )
    this.addVerticalDivider(24)
}

fun MutableList<Delegate>.includeAdViewSection(
    businessModelFactoryProducer: BusinessModelFactoryProducer,
    viewDrawer: ViewRender
) {
    this.add(
        AdViewData(
            businessModelFactoryProducer.getViewFactory(),
            viewDrawer.data?.height,
            AdUnitIds.valueOfOrNull(viewDrawer.children?.key)
        )
    )
    this.addVerticalDivider(24)
}
