package com.yesferal.hornsapp.app.presentation.setting

import android.content.pm.PackageInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.pm.PackageInfoCompat
import androidx.fragment.app.Fragment
import com.yesferal.hornsapp.app.R
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment
    : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            val packageInfo: PackageInfo = it
                .packageManager
                .getPackageInfo(it.packageName, 0)

            val versionName: String = packageInfo.versionName
            val versionCode: Long = PackageInfoCompat.getLongVersionCode(packageInfo)

            val version = "$versionName.$versionCode"
            versionTextView.text = version
        }
    }

    companion object {
        fun newInstance() : SettingFragment {
            return SettingFragment()
        }
    }
}