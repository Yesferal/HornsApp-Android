/* Copyright © 2026 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.messaging.strategy

interface PushHandler {
    fun canHandle(type: String?): Boolean
    fun handle(data: Map<String, String>)
}
