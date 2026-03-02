/* Copyright © 2026 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.messaging

import com.google.firebase.messaging.FirebaseMessagingService
import com.yesferal.hornsapp.hadi.container.Container
import com.yesferal.hornsapp.hadi_android.HadiApp

fun FirebaseMessagingService.hadi(): Container {
    return (application as HadiApp).container
}
