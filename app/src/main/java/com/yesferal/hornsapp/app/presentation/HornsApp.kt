package com.yesferal.hornsapp.app.presentation

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.yesferal.hornsapp.app.presentation.di.registerAppDependencies
import com.yesferal.hornsapp.hada.container.Container
import com.yesferal.hornsapp.hada.container.Hada

class HornsApp: Application() {
    val container: Container by lazy { Hada() }
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate() {
        super.onCreate()
        container.registerAppDependencies(context = this)

        MobileAds.initialize(this)
        firebaseAnalytics = Firebase.analytics
    }
}