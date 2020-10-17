package com.yesferal.hornsapp.app.presentation.ui.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.yesferal.hornsapp.app.presentation.common.custom.HornsBottomSheetFragment

class SettingBottomSheetFragment: HornsBottomSheetFragment() {

    override fun initFragment(bundle: Bundle?): Fragment? {
        return SettingFragment.newInstance()
    }

    companion object {
        fun newInstance(
            bundle: Bundle
        ): HornsBottomSheetFragment {
            return SettingBottomSheetFragment().apply {
                arguments = bundle
            }
        }
    }
}