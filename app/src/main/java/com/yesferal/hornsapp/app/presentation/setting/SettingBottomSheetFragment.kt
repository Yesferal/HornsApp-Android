package com.yesferal.hornsapp.app.presentation.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.yesferal.hornsapp.app.util.HornsBottomSheetFragment

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