/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.adMob

interface AdUnitIds {
    enum class Type {
        FIRST_BAND_DETAIL,
        FIRST_CONCERT_DETAIL,
        FIRST_FAVORITE_LIST,
        FIRST_NEWEST_LIST,
        FIRST_UPCOMING_LIST,
        SECOND_CONCERT_DETAIL,
        SECOND_FAVORITE_LIST,
        SECOND_NEWEST_LIST,
        SECOND_UPCOMING_LIST;
    }

    fun getAdUnitBy(type: Type): String {
        return when(type) {
            Type.FIRST_BAND_DETAIL -> getFirstBandDetail()
            Type.FIRST_CONCERT_DETAIL -> getFirstConcertDetail()
            Type.FIRST_FAVORITE_LIST -> getFirstFavoriteList()
            Type.FIRST_NEWEST_LIST -> getFirstNewestList()
            Type.FIRST_UPCOMING_LIST -> getFirstUpcomingList()
            Type.SECOND_CONCERT_DETAIL -> getSecondConcertDetail()
            Type.SECOND_FAVORITE_LIST -> getSecondFavoriteList()
            Type.SECOND_NEWEST_LIST -> getSecondNewestList()
            Type.SECOND_UPCOMING_LIST -> getSecondUpcomingList()
        }
    }

    fun getFirstBandDetail(): String

    fun getFirstConcertDetail(): String

    fun getFirstFavoriteList(): String

    fun getFirstNewestList(): String

    fun getFirstUpcomingList(): String

    fun getSecondConcertDetail(): String

    fun getSecondFavoriteList(): String

    fun getSecondNewestList(): String

    fun getSecondUpcomingList(): String
}
