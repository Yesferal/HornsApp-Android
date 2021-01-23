package com.yesferal.hornsapp.app.presentation.di.hada

import androidx.fragment.app.Fragment
import com.yesferal.hornsapp.hada.container.Container

interface HadaAwareness {
    fun Fragment.hada(): Container {
        return (activity?.application as HadaApp).container
    }
}