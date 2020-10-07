package com.yesferal.hornsapp.app.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.ItemParcelable
import com.yesferal.hornsapp.app.presentation.concert.detail.EXTRA_PARAM_PARCELABLE

abstract class HornsBottomSheetFragment: BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_bottom_sheet,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            val fragment = initFragment(arguments)
            if (fragment == null) {
                dismiss()
                return
            }

            childFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragmentContainerLayout,
                    fragment
                )
                .commit()
        }
    }

    protected abstract fun initFragment(bundle: Bundle?): Fragment?
}