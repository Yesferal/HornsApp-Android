package com.yesferal.hornsapp.app.presentation.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.yesferal.hornsapp.app.presentation.HornsApp

abstract class BaseFragment
    : Fragment(),
    BaseContract.View {
    abstract val actionListener: BaseContract.ActionListener?

    protected fun getContainer() = (activity?.application as HornsApp).container

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionListener?.attach(view = this)
    }

    override fun onDestroyView() {
        actionListener?.detachView()
        super.onDestroyView()
    }
}