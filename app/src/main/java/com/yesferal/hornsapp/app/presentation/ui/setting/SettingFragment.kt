package com.yesferal.hornsapp.app.presentation.ui.setting

import android.content.Intent
import android.content.pm.PackageInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.pm.PackageInfoCompat
import androidx.fragment.app.Fragment
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.ui.favorite.FavoriteActivity
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

        setUpVersion()

        favoriteTextView.setImageView(R.drawable.ic_favorite)
        favoriteTextView.setText(getString(R.string.favorites))
        favoriteTextView.setOnClickListener {
            val intent = Intent(activity, FavoriteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUpVersion() {
        activity?.let {
            val packageInfo: PackageInfo = it
                .packageManager
                .getPackageInfo(it.packageName, 0)

            val versionName: String = packageInfo.versionName
            val versionCode: Long = PackageInfoCompat.getLongVersionCode(packageInfo)

            val version = "$versionName.$versionCode"
            versionTextView.setImageView(R.drawable.ic_information)
            versionTextView.setText(getString(R.string.version), version)
        }
    }

    companion object {
        fun newInstance() : SettingFragment {
            return SettingFragment()
        }
    }
}