/* Copyright © 2026 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.messaging.strategy

class PushDispatcher(
    private val handlers: List<PushHandler>
) {

    fun dispatch(data: Map<String, String>) {
        val type = data["type"]

        handlers.firstOrNull { it.canHandle(type) }
            ?.handle(data)
    }
}
