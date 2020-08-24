package com.yesferal.hornsapp.app.framework.adMob

import android.content.Context
import com.google.android.gms.ads.*

class AdManager(
    private val adUnitIds: AdUnitIds,
    private val context: Context
) {

    private fun adRequest() = AdRequest.Builder().build()

    fun concertsAdView(): AdView {
        val adView = AdView(context)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = adUnitIds.concertsBannerAdUnitId()
        adView.loadAd(adRequest())

        return adView
    }

    fun concertDetailAdView(): AdView {
        val adView = AdView(context)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = adUnitIds.concertDetailBannerAdUnitId()
        adView.loadAd(adRequest())

        return adView
    }
}