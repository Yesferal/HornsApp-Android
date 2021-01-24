package com.yesferal.hornsapp.app.presentation.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds
import com.yesferal.hornsapp.app.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.setBackgroundColor(getColor(R.color.background))

        MobileAds.initialize(this)
    }
}