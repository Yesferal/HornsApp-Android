package com.yesferal.hornsapp.app.framework.adMob

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize

data class AdViewData(
    val adSize: AdSize,
    val adUnitId: String,
    val adRequest: AdRequest
)