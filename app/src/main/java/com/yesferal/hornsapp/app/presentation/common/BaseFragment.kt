package com.yesferal.hornsapp.app.presentation.common

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
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

    fun showToast(
        @StringRes id: Int,
        duration: Int = Toast.LENGTH_SHORT
    ) {
        Toast.makeText(
            context,
            getString(id),
            duration
        ).show()
    }

    fun getColor(@ColorRes id: Int): Int {
        context?.let {
                return ContextCompat.getColor(it, id)
        }

        return 0
    }
}