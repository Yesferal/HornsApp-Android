package com.yesferal.hornsapp.app.framework.file

import android.content.Context
import com.google.gson.Gson
import com.yesferal.hornsapp.data.abstraction.features.DrawerDataSource
import com.yesferal.hornsapp.domain.entity.drawer.AppDrawer
import java.io.IOException

class FileReaderDataSource(
    private val name: String,
    private val context: Context,
    private val gson: Gson
) : DrawerDataSource {

    private fun getJsonDataFromAsset(): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(name).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    override fun getAppDrawer(): AppDrawer {
        return gson.fromJson(getJsonDataFromAsset(), AppDrawer::class.java)
    }
}
