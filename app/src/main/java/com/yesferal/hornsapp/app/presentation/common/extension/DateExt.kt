package com.yesferal.hornsapp.app.presentation.common.extension

import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

fun Long?.formattedWith(pattern: String): String? {
    if (this == null) {
        return null
    }

    return try {
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        simpleDateFormat.timeZone = TimeZone.getTimeZone("GMT")
        simpleDateFormat.format(Date(this))
    } catch (e: Exception) {
        null
    }
}

fun Long?.dateTimeFormatted(): String? {
    return formattedWith("EEE dd, MMMM YYYY")
}

const val oneDayInMilliseconds = 86400000

fun Long?.dateTimeFormatted(days: Int?): String? {
    return if (days == null) {
        dateTimeFormatted()
    } else {
        val builder = StringBuilder()
        val firstDay = this?.plus(oneDayInMilliseconds * 0)
        val lastDay = this?.plus(oneDayInMilliseconds * (days-1))

        if (firstDay.monthFormatted() != lastDay.monthFormatted()) {
            builder.append(firstDay.formattedWith("dd. MM"))
        } else {
            builder.append(firstDay.formattedWith("dd"))
        }
        builder.append(" - ")
        builder.append(lastDay.formattedWith("dd. MM. YYYY"))

        return builder.toString()
    }
}

fun Long?.dayFormatted(): String? {
    return formattedWith("dd")
}

fun Long?.monthFormatted(): String? {
    return formattedWith("MMM")?.substring(0,3)
}

fun Long?.yearFormatted(): String? {
    return formattedWith("YYYY")
}

fun Long?.timeFormatted(): String? {
    return formattedWith("HH:mm a")
}
