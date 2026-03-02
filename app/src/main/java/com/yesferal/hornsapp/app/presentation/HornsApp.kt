package com.yesferal.hornsapp.app.presentation

import android.app.Application
import androidx.work.Configuration
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.yesferal.hornsapp.app.framework.worker.AppRenderWorkerFactory
import com.yesferal.hornsapp.app.presentation.di.SettingFlavorDataClass
import com.yesferal.hornsapp.app.presentation.di.registerAppDependencies
import com.yesferal.hornsapp.hadi.container.Container
import com.yesferal.hornsapp.hadi.container.Hadi
import com.yesferal.hornsapp.hadi_android.HadiApp

class HornsApp : Application(), HadiApp, Configuration.Provider {

    override val container: Container by lazy { Hadi() }

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate() {
        super.onCreate()
        container.registerAppDependencies(context = this)

        FirebaseApp.initializeApp(this)
        firebaseAnalytics = Firebase.analytics

        val appId = settingFlavorDataClass.appId
        val appRenderTopic = "android-${appId}-app-render"
        FirebaseMessaging.getInstance().subscribeToTopic(appRenderTopic)
    }

    private val settingFlavorDataClass: SettingFlavorDataClass by lazy {
        container.resolve()
    }

    override val workManagerConfiguration: Configuration by lazy {
        Configuration.Builder()
            .setWorkerFactory(container.resolve<AppRenderWorkerFactory>())
            .build()
    }
}
