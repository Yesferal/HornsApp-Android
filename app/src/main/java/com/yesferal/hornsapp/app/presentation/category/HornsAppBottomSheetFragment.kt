package com.yesferal.hornsapp.app.presentation.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.yesferal.hornsapp.app.presentation.concert.ItemParcelable
import com.yesferal.hornsapp.app.presentation.concert.detail.EXTRA_PARAM_PARCELABLE

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