package com.yesferal.hornsapp.app.presentation.ui.profile

import android.content.Intent
import android.content.pm.PackageInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.pm.PackageInfoCompat
import androidx.fragment.app.Fragment
import com.yesferal.hornsapp.app.R
import kotlinx.android.synthetic.main.fragment_setting.*

class ProfileFragment
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

        setUpShare()
    }

    private fun setUpShare() {
        shareTextView.setImageView(R.drawable.ic_information)
        shareTextView.setText(getString(R.string.share_with_friends))
        shareTextView.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_hornsapp))
            intent.type = "text/plain"
            startActivity(intent)
        }
    }

    private fun setUpVersion() {
        activity?.let { activityNonNull ->
            val packageInfo: PackageInfo = activityNonNull
                .packageManager
                .getPackageInfo(activityNonNull.packageName, 0)

            val versionName: String = packageInfo.versionName

            versionTextView.setImageView(R.drawable.ic_information)
            versionTextView.setText(getString(R.string.version), versionName)
            versionTextView.setOnClickListener {
                val versionCode: Long = PackageInfoCompat.getLongVersionCode(packageInfo)
                Toast.makeText(activityNonNull, versionCode.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}