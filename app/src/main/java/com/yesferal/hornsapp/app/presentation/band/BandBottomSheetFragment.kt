package com.yesferal.hornsapp.app.presentation.band

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.yesferal.hornsapp.app.presentation.common.ItemParcelable
import com.yesferal.hornsapp.app.presentation.concert.detail.EXTRA_PARAM_PARCELABLE
import com.yesferal.hornsapp.app.util.HornsBottomSheetFragment

class BandBottomSheetFragment: HornsBottomSheetFragment() {

    override fun initFragment(bundle: Bundle?): Fragment? {
        val item = bundle?.getParcelable<ItemParcelable>(
            EXTRA_PARAM_PARCELABLE
        ) ?: return null

        bundle.putParcelable(EXTRA_PARAM_PARCELABLE, item)

        return BandFragment.newInstance(bundle)
    }

    companion object {
        fun newInstance(
            bundle: Bundle
        ): HornsBottomSheetFragment {
            return BandBottomSheetFragment().apply {
                arguments = bundle
            }
        }
    }
}