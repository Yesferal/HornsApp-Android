/* Copyright © 2025 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.gson

import com.google.gson.Gson
import com.yesferal.hornsapp.core.domain.abstraction.Logger

class GsonDataSource (private val gson: Gson, private val logger: Logger) {
    fun <T> fromJsonSafe(json: String?, classOfT: Class<T>): T? {
        if (json == null) {
            return null
        }

        try {
            return gson.fromJson(json, classOfT)
        } catch (e: Exception) {
            logger.e(e.message.orEmpty())
            return null
        }
    }

    fun toJsonSafe(myObject: Any): String? {
        try {
            return gson.toJson(myObject)
        } catch (e: Exception) {
            logger.e(e.message.orEmpty())
            return null
        }
    }
}
