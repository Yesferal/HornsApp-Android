package com.yesferal.hornsapp.app.framework.adMob

class AdUnitIds {

    companion object {
        init {
            System.loadLibrary("hornsapp-lib")
        }
    }

    external fun concertsBannerAdUnitId(): String
    external fun concertDetailBannerAdUnitId(): String
}