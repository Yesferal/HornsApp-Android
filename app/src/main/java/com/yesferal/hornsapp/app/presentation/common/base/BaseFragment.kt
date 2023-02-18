package com.yesferal.hornsapp.app.presentation.common.base

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.core.domain.navigator.Direction
import com.yesferal.hornsapp.core.domain.navigator.Navigator
import com.yesferal.hornsapp.hadi_android.hadi

abstract class BaseFragment : Fragment(), LayoutBinding {

    val navigator by lazy {
        hadi().resolve<Navigator<Fragment>>()
    }

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
        uri: String,
        externalPackage: String,
        onError: () -> Unit = { showToast(R.string.error_app_not_found) }
    ) {
        val androidUri = Uri.parse(uri)
        val intent = Intent(Intent.ACTION_VIEW, androidUri)

        intent.setPackage(externalPackage)
        activity?.let {
            intent.resolveActivity(it.packageManager)?.let {
                startActivity(intent)
            } ?: kotlin.run { onError() }
        }
    }

    fun startExternalActivity(uri: String) {
        val androidUri = Uri.parse(uri)
        startActivity(Intent(Intent.ACTION_VIEW, androidUri))
    }

    fun Direction.navigateTo() {
        navigator.navigate(this@BaseFragment, this)
    }
}
