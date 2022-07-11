/* Copyright © 2021 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.adMob

import android.content.Context
import android.view.View
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

/**
 * This class follow the Factory Pattern & it is also part of the Abstract Factory Pattern
 *
 * @author Yesferal
 */
class FreeViewFactory(private val adUnitIds: AdUnitIds): AbstractViewFactory {

    private fun adRequest() = AdRequest.Builder().build()

    override fun drawView(context: Context, type: AbstractViewFactory.Type): View {
        return when(type) {
            AbstractViewFactory.Type.MAIN -> getAdView(context, getAdViewData())
        }
    }

    private fun getAdView(context: Context, adViewData: AdViewData): AdView {
        val adView = AdView(context)
        adView.adSize = adViewData.adSize
        adView.adUnitId = adViewData.adUnitId
        adView.loadAd(adViewData.adRequest)

        return adView
    }

    private fun getAdViewData(): AdViewData {
        val adSize = AdSize.BANNER
        val adUnitId = adUnitIds.concertsBannerAdUnitId()
        val adRequest = adRequest()

        return AdViewData(adSize, adUnitId, adRequest)
    }
}
