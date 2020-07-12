package com.yesferal.hornsapp.app.framework.adMob

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

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
}