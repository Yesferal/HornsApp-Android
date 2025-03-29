/* Copyright © 2025 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.lineup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.framework.adMob.AdUnitIds
import com.yesferal.hornsapp.app.framework.adMob.BusinessModelFactoryProducer
import com.yesferal.hornsapp.app.presentation.common.extension.addBottomView
import com.yesferal.hornsapp.app.presentation.common.extension.fadeIn
import com.yesferal.hornsapp.app.presentation.common.extension.fadeOut
import com.yesferal.hornsapp.app.presentation.common.extension.setUpWith
import com.yesferal.hornsapp.app.presentation.common.render.RenderFragment
import com.yesferal.hornsapp.app.presentation.ui.home.FragmentFactory
import com.yesferal.hornsapp.app.presentation.ui.home.HomeViewState
import com.yesferal.hornsapp.core.domain.entity.render.ViewRender
import com.yesferal.hornsapp.hadi_android.getViewModel
import com.yesferal.hornsapp.hadi_android.hadi

class DayLineupFragment: RenderFragment<HomeViewState>() {
    override val layout = R.layout.fragment_day_lineup_beta

    private lateinit var viewModel: LineupViewModel
    private val args: DayLineupFragmentArgs by navArgs()

    private lateinit var customProgressBar: View
    private lateinit var closeImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var firstAdLayout: FrameLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val stage1Fragment = StageLineupFragment.newInstance(0)
        val stage2Fragment = StageLineupFragment.newInstance(1)
        val stage3Fragment = StageLineupFragment.newInstance(2)
        val stage4Fragment = StageLineupFragment.newInstance(3)
        val stage5Fragment = StageLineupFragment.newInstance(4)

        val transaction = getChildFragmentManager().beginTransaction()

        transaction.replace(R.id.stage1Fragment, stage1Fragment)
            .replace(R.id.stage2Fragment, stage2Fragment)
            .replace(R.id.stage3Fragment, stage3Fragment)
            .replace(R.id.stage4Fragment, stage4Fragment)
            .replace(R.id.stage5Fragment, stage5Fragment)
            .commit()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lineup = args.lineup
        if (lineup?.id == null) {
            activity?.onBackPressedDispatcher?.onBackPressed()
            return
        }
        customProgressBar = view.findViewById(R.id.customProgressBar)
        titleTextView = view.findViewById(R.id.titleTextView)
        closeImageView = view.findViewById(R.id.closeImageView)
        closeImageView.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        viewModel = getViewModel<LineupViewModel, LineupViewModelFactory>(param = lineup.id)
        viewModel.state.observe(viewLifecycleOwner) {
            titleTextView.setUpWith(it.day)
            render(HomeViewState())
        }

        firstAdLayout = view.findViewById(R.id.firstAdLayout)
        val abstractViewFactory = hadi().resolve<BusinessModelFactoryProducer>().getViewFactory()
        firstAdLayout.addBottomView(abstractViewFactory, AdUnitIds.Type.FIRST_CONCERT_DETAIL, 50)

    }

    override fun render(viewState: HomeViewState) {
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


    companion object {
        fun newInstance() = DayLineupFragment()
    }
}

/*
class DayLineupFragment : RenderFragment<HomeViewState>() {
    override val layout = R.layout.fragment_day_lineup

    private lateinit var tabLayout: TabLayout
    private lateinit var customProgressBar: View
    private lateinit var lineUpViewPager: ViewPager2
    private lateinit var closeImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var viewModel: LineupViewModel
    private val args: DayLineupFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lineup = args.lineup
        if (lineup?.id == null) {
            activity?.onBackPressedDispatcher?.onBackPressed()
            return
        }

        customProgressBar = view.findViewById(R.id.customProgressBar)
        lineUpViewPager = view.findViewById(R.id.lineUpViewPager)

        tabLayout = view.findViewById(R.id.tabLayout)
        tabLayout.addOnTabSelectedListener(instanceOnTabSelectedListener())

        titleTextView = view.findViewById(R.id.titleTextView)
        closeImageView = view.findViewById(R.id.closeImageView)
        closeImageView.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        viewModel = getViewModel<LineupViewModel, LineupViewModelFactory>(param = lineup.id)

        viewModel.state.observe(viewLifecycleOwner) {
            titleTextView.setUpWith(it.day)
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
        fun newInstance() = DayLineupFragment()
    }
}*/

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
