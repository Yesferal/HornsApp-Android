package com.yesferal.hornsapp.app.presentation.common.extension

import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.framework.adMob.AdUnitIds
import com.yesferal.hornsapp.app.framework.adMob.BusinessModelFactoryProducer
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.AdViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.IconHomeCardViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.ImageHomeCardViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.TitleViewData
import com.yesferal.hornsapp.core.domain.entity.Concert
import com.yesferal.hornsapp.core.domain.entity.render.ViewRender
import com.yesferal.hornsapp.delegate.abstraction.Delegate
import com.yesferal.hornsapp.delegate.delegate.DividerDelegate
import com.yesferal.hornsapp.delegate.delegate.RowDelegate

fun MutableList<Delegate>.addVerticalDivider(height: Int, background: Int = R.color.background) {
    this.add(DividerDelegate(height = height, width = Int.MAX_VALUE, background = background))
}


fun MutableList<Delegate>.safeInsert(i: Int, delegate: Delegate) {
    if (i <= size) {
        this.add(i, delegate)
    }
}

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

fun MutableList<Delegate>.includeCarouselSection(
    concerts: List<Concert>,
    viewRender: ViewRender
) {
    val delegates = viewRender.getConcertDelegates(concerts)

    if (delegates.isEmpty()) {
        return
    }

    this.add(
        RowDelegate.Builder().addItems(delegates).addBackground(R.color.background)
            .addElevation(4F).build()
    )
    this.addVerticalDivider(24)
}

fun MutableList<Delegate>.includeVerticalSection(
    concerts: List<Concert>,
    viewRender: ViewRender
) {
    val delegates = viewRender.getConcertDelegates(concerts)

    if (delegates.isEmpty()) {
        return
    }

    this.add(
        TitleViewData(
            viewRender.data?.title?.text,
            viewRender.data?.subtitle?.text,
            viewRender.navigation,
            viewRender.data?.icon
        )
    )
    this.addAll(delegates)
    this.addVerticalDivider(24)
}
