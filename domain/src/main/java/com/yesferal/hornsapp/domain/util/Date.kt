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

fun Date.dateTimeFormatted(): String? {
    return formattedWith("EEEE dd, MMMM (HH:mm)")
}

fun Date.dayFormatted(): String? {
    return formattedWith("dd")
}

fun Date.monthFormatted(): String? {
    return formattedWith("MMM")?.substring(0,3)
}

fun Date.yearFormatted(): String? {
    return formattedWith("YYYY")
}

fun Date.timeFormatted(): String? {
    return formattedWith("HH:mm a")
}