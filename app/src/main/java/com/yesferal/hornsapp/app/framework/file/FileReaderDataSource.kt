package com.yesferal.hornsapp.app.framework.file

import android.content.Context
import com.google.gson.Gson
import com.yesferal.hornsapp.app.framework.logger.YLog
import com.yesferal.hornsapp.data.abstraction.features.DrawerDataSource
import com.yesferal.hornsapp.domain.entity.drawer.HomeDrawer
import java.io.IOException

class FileReaderDataSource(private val name: String, private val context: Context, private val gson: Gson) :
    DrawerDataSource {

    fun getJsonDataFromAsset(): String? {
        val jsonString: String
        try {
            jsonString =
                context.assets.open(name).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    override fun getHomeDrawer(): HomeDrawer {
        YLog.d("${getJsonDataFromAsset()}")
        return gson.fromJson(getJsonDataFromAsset(), HomeDrawer::class.java)
    }
}
