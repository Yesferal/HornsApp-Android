/* Copyright © 2026 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.messaging.strategy

import com.yesferal.hornsapp.app.framework.logger.ChainLoggerProvider

class NotificationPushHandler : PushHandler {

    override fun canHandle(type: String?) =
        type == "NOTIFICATION"

    override fun handle(data: Map<String, String>) {
        // TODO: Show system notification
        ChainLoggerProvider.provideLogger().d("FCM: Notification: Message received: ${data}")
    }
}
