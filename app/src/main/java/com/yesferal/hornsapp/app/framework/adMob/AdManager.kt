package com.yesferal.hornsapp.app.framework.adMob

import com.google.android.gms.ads.*

class AdManager(
    private val adUnitIds: AdUnitIds
) {
    private fun adRequest() = AdRequest.Builder().build()

    fun concertsAdView(): AdViewData {
        val adSize = AdSize.BANNER
        val adUnitId = adUnitIds.concertsBannerAdUnitId()
        val adRequest = adRequest()

        return AdViewData(adSize, adUnitId, adRequest)
    }

    fun concertDetailAdView(): AdViewData {
        val adSize = AdSize.BANNER
        val adUnitId = adUnitIds.concertDetailBannerAdUnitId()
        val adRequest = adRequest()

        return AdViewData(adSize, adUnitId, adRequest)
    }
}