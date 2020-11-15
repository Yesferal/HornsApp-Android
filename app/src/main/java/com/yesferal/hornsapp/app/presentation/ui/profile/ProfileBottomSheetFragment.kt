package com.yesferal.hornsapp.app.presentation.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.yesferal.hornsapp.app.presentation.common.ui.custom.HornsBottomSheetFragment

class ProfileBottomSheetFragment: HornsBottomSheetFragment() {

    override fun initFragment(bundle: Bundle?): Fragment? {
        return ProfileFragment.newInstance()
    }

    companion object {
        fun newInstance(
            bundle: Bundle
        ): HornsBottomSheetFragment {
            return ProfileBottomSheetFragment().apply {
                arguments = bundle
            }
        }
    }
}