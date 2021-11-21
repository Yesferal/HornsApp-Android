package com.yesferal.hornsapp.app.presentation

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.yesferal.hornsapp.app.presentation.di.registerAppDependencies
import com.yesferal.hornsapp.hadi.container.Container
import com.yesferal.hornsapp.hadi.container.Hadi
import com.yesferal.hornsapp.hadi_android.HadiApp

class HornsApp : Application(), HadiApp {

    override val container: Container by lazy { Hadi() }

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate() {
        super.onCreate()
        container.registerAppDependencies(context = this)
        firebaseAnalytics = Firebase.analytics
    }
}
