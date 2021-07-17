package com.yesferal.hornsapp.app.presentation.ui.main

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.framework.adMob.AdViewData
import com.yesferal.hornsapp.app.presentation.di.hada.HadaAwareness
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity
    : AppCompatActivity(),
    HadaAwareness {
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.setBackgroundColor(getColor(R.color.background))

        MobileAds.initialize(this)

        mainViewModel = ViewModelProvider(
                viewModelStore,
                hada().resolve<MainViewModelFactory>()
        ).get(MainViewModel::class.java)

        mainViewModel.adViewData.observe(this) {
            adContainerLayout.addAdView(it)
        }
    }

    private fun FrameLayout.addAdView(adViewData: AdViewData) {
        val adView = AdView(context)
        adView.adSize = adViewData.adSize
        adView.adUnitId = adViewData.adUnitId
        adView.loadAd(adViewData.adRequest)

        removeAllViews()
        addView(adView)
    }
}
