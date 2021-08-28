package com.yesferal.hornsapp.app.presentation.di.hada

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yesferal.hornsapp.hada.container.Container
import com.yesferal.hornsapp.hada.parameter.Parameters

fun Fragment.hada(): Container {
    return (requireActivity().application as HadaApp).container
}

fun Activity.hada(): Container {
    return (application as HadaApp).container
}

inline fun <reified T : ViewModel, reified F : ViewModelProvider.Factory> Fragment.getViewModel(
    param: Any? = null
) = ViewModelProvider(
    viewModelStore,
    resolveFactory<F>(param)
).get(T::class.java)

inline fun <reified F : ViewModelProvider.Factory> Fragment.resolveFactory(param: Any?): F {
    return param?.let {
        hada().resolve(params = Parameters(it))
    } ?: run {
        hada().resolve()
    }
}
