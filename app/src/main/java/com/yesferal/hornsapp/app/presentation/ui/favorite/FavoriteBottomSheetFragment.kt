package com.yesferal.hornsapp.app.presentation.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.yesferal.hornsapp.app.presentation.common.custom.HornsBottomSheetFragment
import com.yesferal.hornsapp.app.presentation.ui.concert.ConcertsFragment
import com.yesferal.hornsapp.domain.entity.CategoryKey

class FavoriteBottomSheetFragment: HornsBottomSheetFragment() {
    override fun initFragment(bundle: Bundle?): Fragment? {
        return ConcertsFragment.newInstance(CategoryKey.FAVORITE.toString())
    }

    companion object {
        fun newInstance(
            bundle: Bundle
        ): HornsBottomSheetFragment {
            return FavoriteBottomSheetFragment().apply {
                arguments = bundle
            }
        }
    }
}