/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.packageinfo

import android.content.pm.PackageInfo
import androidx.core.content.pm.PackageInfoCompat

class PackageInfoDataSource(
    private val packageInfo: PackageInfo
) {
    fun getVersionCode(): Long {
        return PackageInfoCompat.getLongVersionCode(packageInfo)
    }

    fun getVersionName(): String {
        return packageInfo.versionName
    }
}
