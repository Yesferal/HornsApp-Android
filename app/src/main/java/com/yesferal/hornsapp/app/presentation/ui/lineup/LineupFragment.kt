/* Copyright © 2025 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.lineup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.extension.fadeIn
import com.yesferal.hornsapp.app.presentation.common.extension.fadeOut
import com.yesferal.hornsapp.app.presentation.common.render.RenderFragment
import com.yesferal.hornsapp.app.presentation.ui.home.FragmentFactory
import com.yesferal.hornsapp.app.presentation.ui.home.HomeViewState
import com.yesferal.hornsapp.core.domain.entity.render.ViewRender
import com.yesferal.hornsapp.hadi_android.getViewModel

class LineupFragment : RenderFragment<HomeViewState>() {
    override val layout = R.layout.fragment_lineup

    private lateinit var tabLayout: TabLayout
    private lateinit var customProgressBar: View
    private lateinit var lineUpViewPager: ViewPager2
    private lateinit var viewModel: LineupViewModel

    // TODO: Make ID dynamic
    private val ID = "67e61d62c644dc0fa6d3f8ec"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customProgressBar = view.findViewById(R.id.customProgressBar)
        lineUpViewPager = view.findViewById(R.id.lineUpViewPager)

        tabLayout = view.findViewById(R.id.tabLayout)
        tabLayout.addOnTabSelectedListener(instanceOnTabSelectedListener())

        viewModel = getViewModel<LineupViewModel, LineupViewModelFactory>(param = ID)


        viewModel.state.observe(viewLifecycleOwner) {
            render(
                HomeViewState(
                    it.headers?.map {
                        Pair(ViewRender.Type.STAGE_LINEUP_FRAGMENT, it)
                    }
                )
            )
        }
    }

    override fun render(viewState: HomeViewState) {
        viewState.screens?.let {
            showChildFragmentTitles(it)
        }


        if (viewState.isLoading) {
            showProgress()
        } else {
            hideProgress()
        }
    }

    private fun showProgress() {
        customProgressBar.fadeIn()
    }

    private fun hideProgress() {
        customProgressBar.fadeOut()
    }

    private fun showChildFragmentTitles(screens: List<Pair<ViewRender.Type, String>>) {
        lineUpViewPager.adapter =
            ScreenSlidePagerAdapter(this, FragmentFactory(), screens.map { it.first })
        TabLayoutMediator(tabLayout, lineUpViewPager) { tab, position ->
            tab.customView = null
            tab.setCustomView(R.layout.custom_tab_layout)
            tab.text = screens[position].second
        }.attach()
        tabLayout.visibility = View.VISIBLE
    }

    companion object {
        fun newInstance() = LineupFragment()
    }
}

// TODO: This code is duplicates in Home too
private class ScreenSlidePagerAdapter(
    activity: Fragment,
    private val fragmentFactory: FragmentFactory,
    private val screens: List<ViewRender.Type>
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = screens.size

    override fun createFragment(position: Int): Fragment {
        return fragmentFactory.getFragment(type = screens[position], position)
    }
}

// TODO: This code is duplicates in Home too
private fun instanceOnTabSelectedListener() = object : TabLayout.OnTabSelectedListener {
    override fun onTabSelected(tab: TabLayout.Tab?) {
        tab?.customView = null
        tab?.setCustomView(R.layout.custom_tab_layout_selected)
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        tab?.customView = null
        tab?.setCustomView(R.layout.custom_tab_layout)
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {}
}
