package com.yesferal.hornsapp.app.presentation.common.base

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.framework.adMob.AdViewData
import com.yesferal.hornsapp.app.presentation.di.hada.HadaAwareness
import java.net.URI

abstract class BaseFragment<VIEW_STATE>
    : Fragment(),
    RenderState<VIEW_STATE>,
    LayoutBinding,
    HadaAwareness {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout, container, false)
    }

    protected fun showToast(
        @StringRes id: Int,
        duration: Int = Toast.LENGTH_SHORT
    ) {
        Toast.makeText(
            context,
            getString(id),
            duration
        ).show()
    }

    protected fun startExternalActivity(
        uri: URI?,
        externalPackage: String,
        onError: () -> Unit = { showToast(R.string.error_app_not_found) }
    ) {
        if (uri == null) return

        val androidUri = Uri.parse(uri.toString())
        val intent = Intent(Intent.ACTION_VIEW,  androidUri)

        intent.setPackage(externalPackage)
        activity?.let {
            intent.resolveActivity(it.packageManager)?.let {
                startActivity(intent)
            } ?: kotlin.run { onError() }
        }
    }

    protected fun startExternalActivity(uri: URI?) {
        if (uri == null) return

        val androidUri = Uri.parse(uri.toString())
        startActivity(Intent(Intent.ACTION_VIEW, androidUri))
    }

    protected fun FrameLayout.addAdView(adViewData: AdViewData) {
        val adView = AdView(context)
        adView.adSize = adViewData.adSize
        adView.adUnitId = adViewData.adUnitId
        adView.loadAd(adViewData.adRequest)

        removeAllViews()
        addView(adView)
    }
}