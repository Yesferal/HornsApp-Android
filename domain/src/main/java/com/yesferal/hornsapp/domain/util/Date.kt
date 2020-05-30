package com.yesferal.hornsapp.domain.util

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

fun Date.formattedWith(pattern: String): String? {
    return try {
        val simpleDateFormat = SimpleDateFormat(pattern, Locale("es"))
        simpleDateFormat.timeZone = TimeZone.getTimeZone("GMT")
        simpleDateFormat.format(this)
    } catch (e: Exception) {
        null
    }
}

fun Date.formatted(): String? {
    return "${formattedWith("dd")}" +
            " de" +
            " ${formattedWith("MMMM, yyyy")}"
}

fun Date.timeFormatted(): String? {
    return formattedWith("HH:mm a")
}