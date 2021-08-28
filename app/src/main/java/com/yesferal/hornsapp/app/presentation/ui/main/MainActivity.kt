package com.yesferal.hornsapp.app.presentation.ui.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.framework.adMob.AdViewData
import com.yesferal.hornsapp.app.presentation.di.hada.hada

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    private lateinit var adContainerLayout: FrameLayout

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            this@MainActivity.finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.setBackgroundColor(getColor(R.color.background))

        MobileAds.initialize(this)

        mainViewModel = ViewModelProvider(
            viewModelStore,
            hada().resolve<MainViewModelFactory>()
        ).get(MainViewModel::class.java)

        adContainerLayout = findViewById(R.id.adContainerLayout)

        mainViewModel.adViewData.observe(this) {
            adContainerLayout.addAdView(it)
        }

        val filter = IntentFilter(Intent.ACTION_LOCALE_CHANGED)
        registerReceiver(broadcastReceiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
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
