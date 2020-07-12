package com.yesferal.hornsapp.domain.util

import java.net.URI

fun URI.isSafe(): Boolean {
    val allowedHosts = listOf(
        "www.facebook.com",
        "www.joinnus.com",
        "www.youtube.com"
    )
    val allowedScheme = "https"

    var isSafe = false
    if (allowedScheme == this.scheme
        && allowedHosts.contains(this.host)) {
        isSafe = true
    }

    return isSafe
}