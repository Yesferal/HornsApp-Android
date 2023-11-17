/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.adMob

class ReleaseAdUnitIds(private val nativeAdUnitIds: NativeAdUnitIds): AdUnitIds {

    override fun getFirstBandDetail() = nativeAdUnitIds.nativeFirstBandDetail()

    override fun getFirstConcertDetail() = nativeAdUnitIds.nativeFirstConcertDetail()

    override fun getFirstFavoriteList() = nativeAdUnitIds.nativeFirstFavoriteList()

    override fun getFirstNewestList() = nativeAdUnitIds.nativeFirstNewestList()

    override fun getFirstUpcomingList() = nativeAdUnitIds.nativeFirstUpcomingList()

    override fun getSecondConcertDetail() = nativeAdUnitIds.nativeSecondConcertDetail()

    override fun getSecondFavoriteList() = nativeAdUnitIds.nativeSecondFavoriteList()

    override fun getSecondNewestList() = nativeAdUnitIds.nativeSecondNewestList()

    override fun getSecondUpcomingList() = nativeAdUnitIds.nativeSecondUpcomingList()
}

class NativeAdUnitIds {
    companion object {
        init {
            System.loadLibrary("hornsapp-lib")
        }
    }

    external fun nativeFirstBandDetail(): String
    external fun nativeFirstConcertDetail(): String
    external fun nativeFirstFavoriteList(): String
    external fun nativeFirstUpcomingList(): String
    external fun nativeFirstNewestList(): String
    external fun nativeSecondConcertDetail(): String
    external fun nativeSecondFavoriteList(): String
    external fun nativeSecondNewestList(): String
    external fun nativeSecondUpcomingList(): String
}
