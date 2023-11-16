/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.adMob

class ReleaseAdUnitIds: AdUnitId {

    companion object {
        init {
            System.loadLibrary("hornsapp-lib")
        }
    }

    external fun concertsBannerAdUnitId(): String
}
