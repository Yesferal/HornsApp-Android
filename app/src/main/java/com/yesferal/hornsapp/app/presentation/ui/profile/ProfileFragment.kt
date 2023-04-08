/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.profile

import android.content.pm.PackageInfo
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.content.pm.PackageInfoCompat
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.BaseFragment
import com.yesferal.hornsapp.app.presentation.common.custom.ImageTextView
import com.yesferal.hornsapp.app.presentation.ui.settings.EasterEggsApplier
import com.yesferal.hornsapp.core.domain.navigator.Navigator
import com.yesferal.hornsapp.core.domain.navigator.ScreenType

class ProfileFragment : BaseFragment(), EasterEggsApplier {

    override val layout = R.layout.fragment_profile

    private lateinit var shareTextView: ImageTextView
    private lateinit var versionTextView: ImageTextView
    private lateinit var hornsAppImageView: ImageView

    private var preferencesCountDown: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferencesCountDown = 0

        shareTextView = view.findViewById(R.id.shareTextView)
        versionTextView = view.findViewById(R.id.versionTextView)
        hornsAppImageView = view.findViewById(R.id.hornsAppImageView)

        setUpPreferences()
        setUpVersion(versionSuffix())
        setUpShare()
    }

    private fun setUpShare() {
        shareTextView.setImageView(R.drawable.ic_share)
        shareTextView.setText(
            getString(R.string.share_with_friends),
            getString(R.string.use_your_favorite_apps)
        )
        shareTextView.setOnClickListener {
            Navigator.Builder()
                .to(ScreenType.MESSAGE)
                .with(MessageViewData(getString(R.string.share_hornsapp_message)))
                .build()
                .navigateTo()
        }
    }

    private fun setUpVersion(suffix: String) {
        activity?.let { activityNonNull ->
            val packageInfo: PackageInfo = activityNonNull
                .packageManager
                .getPackageInfo(activityNonNull.packageName, 0)

            val versionName: String = packageInfo.versionName
            val versionCode: Long = PackageInfoCompat.getLongVersionCode(packageInfo)

            versionTextView.setImageView(R.drawable.ic_information)
            val version = StringBuilder()
                .append(versionName)
                .append("+")
                .append(versionCode)
                .append("-")
                .append(suffix)

            versionTextView.setText(getString(R.string.version), version.toString())
        }
    }

    private fun setUpPreferences() {
        hornsAppImageView.setOnClickListener {
            preferencesCountDown++
            if (preferencesCountDown >= 3) {
                onAppImageViewClick()
            }
        }
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}
