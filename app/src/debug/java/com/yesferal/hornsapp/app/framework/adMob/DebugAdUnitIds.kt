/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.adMob

class DebugAdUnitIds: AdUnitIds {
    private var debugAdUnitId = "ca-app-pub-3940256099942544/6300978111"

    override fun getFirstBandDetail() = debugAdUnitId

    override fun getFirstConcertDetail() = debugAdUnitId

    override fun getFirstFavoriteList() = debugAdUnitId

    override fun getFirstNewestList() = debugAdUnitId

    override fun getFirstUpcomingList() = debugAdUnitId

    override fun getSecondConcertDetail() = debugAdUnitId

    override fun getSecondFavoriteList() = debugAdUnitId

    override fun getSecondNewestList() = debugAdUnitId

    override fun getSecondUpcomingList() = debugAdUnitId
}
