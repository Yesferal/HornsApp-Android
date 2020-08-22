package com.yesferal.hornsapp.app.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.yesferal.hornsapp.app.presentation.common.ItemParcelable
import com.yesferal.hornsapp.app.presentation.concert.detail.EXTRA_PARAM_PARCELABLE
import com.yesferal.hornsapp.app.presentation.item.ItemsFragment

class HornsAppBottomSheetFragment: BottomSheetFragment() {

    override fun initFragment(item: ItemParcelable): Fragment {
        val bundle = Bundle()
        bundle.putParcelable(EXTRA_PARAM_PARCELABLE, item)

        return ItemsFragment.newInstance(bundle)
    }

    companion object {
        fun newInstance(
            bundle: Bundle
        ): BottomSheetFragment {
            return HornsAppBottomSheetFragment().apply {
                arguments = bundle
            }
        }
    }
}