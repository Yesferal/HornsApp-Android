/* Copyright © 2026 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.messaging

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.yesferal.hornsapp.app.framework.logger.ChainLoggerProvider
import com.yesferal.hornsapp.app.framework.messaging.strategy.AppRenderUpdatePushHandler
import com.yesferal.hornsapp.app.framework.messaging.strategy.NotificationPushHandler
import com.yesferal.hornsapp.app.framework.messaging.strategy.PushDispatcher

class HornsAppFirebaseMessagingService: FirebaseMessagingService() {
    private val dispatcher by lazy {
        PushDispatcher(
            listOf(
                AppRenderUpdatePushHandler(),
                NotificationPushHandler(),
            )
        )
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        dispatcher.dispatch(remoteMessage.data)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        ChainLoggerProvider.provideLogger().d("FCM: New token: $token")
    }
}
