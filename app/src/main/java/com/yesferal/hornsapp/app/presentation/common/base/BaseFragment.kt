/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.yesferal.hornsapp.core.domain.navigator.Direction
import com.yesferal.hornsapp.core.domain.navigator.NavViewData
import com.yesferal.hornsapp.core.domain.navigator.Navigator
import com.yesferal.hornsapp.core.domain.navigator.ScreenType
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

    fun startExternalActivity(navViewData: NavViewData) {
        Direction.Build().to(ScreenType.WEB_VIEW)
            .with(navViewData)
            .build()
            .navigateTo()
    }

    fun Direction.navigateTo() {
        navigator.navigate(this@BaseFragment, this)
    }
}
