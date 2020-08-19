package com.yesferal.hornsapp.app.presentation

import android.app.Application
import android.content.Context
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.yesferal.hornsapp.app.presentation.di.registerAppDependencies
import com.yesferal.hornsapp.hada.container.Container
import com.yesferal.hornsapp.hada.container.Hada
import com.yesferal.hornsapp.hada.container.register
import com.yesferal.hornsapp.hada.dependency.Singleton

class HornsApp: Application() {
    val container: Container by lazy { Hada() }
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate() {
        super.onCreate()
        container register Singleton<Context> { this }
        container.registerAppDependencies()

        MobileAds.initialize(this)
        firebaseAnalytics = Firebase.analytics
    }
}