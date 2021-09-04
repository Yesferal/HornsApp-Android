package com.yesferal.hornsapp.app.framework.file

import android.content.Context
import java.io.IOException

class FileReaderManager(
    private val context: Context
) {

    fun getJsonDataFromAsset(name: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(name).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}
