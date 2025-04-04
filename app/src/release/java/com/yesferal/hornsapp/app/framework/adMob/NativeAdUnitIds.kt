/* Copyright © 2025 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.adMob

import com.yesferal.hornsapp.app.presentation.di.FlavorDataClass

class NativeAdUnitIds(flavorDataClass: FlavorDataClass) : AdUnitIds {
    init {
        System.loadLibrary(flavorDataClass.cFileName)
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

    override fun getFirstBandDetail(): String {
        return nativeFirstBandDetail()
    }

    override fun getFirstConcertDetail(): String {
        return nativeFirstConcertDetail()
    }

    override fun getFirstFavoriteList(): String {
        return nativeFirstFavoriteList()
    }

    override fun getFirstNewestList(): String {
        return nativeFirstNewestList()
    }

    override fun getFirstUpcomingList(): String {
        return nativeFirstUpcomingList()
    }

    override fun getSecondConcertDetail(): String {
        return nativeSecondConcertDetail()
    }

    override fun getSecondFavoriteList(): String {
        return nativeSecondFavoriteList()
    }

    override fun getSecondNewestList(): String {
        return nativeSecondNewestList()
    }

    override fun getSecondUpcomingList(): String {
        return nativeSecondUpcomingList()
    }
}
