/* Copyright © 2021 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.MobileAds
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.framework.adMob.AbstractViewFactory
import com.yesferal.hornsapp.hadi_android.hadi

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
            hadi().resolve<MainViewModelFactory>()
        ).get(MainViewModel::class.java)

        adContainerLayout = findViewById(R.id.adContainerLayout)

        mainViewModel.viewFactory.observe(this) {
            adContainerLayout.addBottomView(it)
        }

        setupReceiver()
    }

    private fun setupReceiver() {
        val filter = IntentFilter(Intent.ACTION_LOCALE_CHANGED)
        registerReceiver(broadcastReceiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }

    private fun FrameLayout.addBottomView(viewFactory: AbstractViewFactory) {
        removeAllViews()
        addView(viewFactory.drawView(context, AbstractViewFactory.Type.MAIN))
    }
}
