/* Copyright © 2026 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.messaging.strategy

import com.yesferal.hornsapp.app.framework.logger.ChainLoggerProvider

class AppRenderUpdatePushHandler : PushHandler {

    override fun canHandle(type: String?) =
        type == "APP_RENDER_UPDATE"

    override fun handle(data: Map<String, String>) {
        // TODO: Trigger repository fetch
        // TODO: Maybe schedule WorkManager
        ChainLoggerProvider.provideLogger().d("FCM: AppRenderUpdate: Message received: ${data}")
    }
}
