package com.yesferal.hornsapp.app.presentation.ui.error

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.BaseFragment
import com.yesferal.hornsapp.app.presentation.common.extension.setUpWith

class ErrorFragment : BaseFragment() {

    override val layout: Int
        get() = R.layout.custom_error

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.errorTextView)
            .setUpWith(view.context.getString(R.string.error_default))
    }

    companion object {
        fun newInstance() = ErrorFragment()
    }
}
