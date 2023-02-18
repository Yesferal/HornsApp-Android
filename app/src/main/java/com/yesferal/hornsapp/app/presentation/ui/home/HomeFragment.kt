package com.yesferal.hornsapp.app.presentation.ui.home

import android.os.Bundle
import android.view.View
import android.view.ViewStub
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.extension.fadeIn
import com.yesferal.hornsapp.app.presentation.common.extension.fadeOut
import com.yesferal.hornsapp.app.presentation.common.render.RenderFragment
import com.yesferal.hornsapp.core.domain.entity.drawer.ScreenDrawer
import com.yesferal.hornsapp.core.domain.navigator.ScreenType
import com.yesferal.hornsapp.hadi_android.getViewModel

class HomeFragment : RenderFragment<HomeViewState>() {

    override val layout = R.layout.fragment_home

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var customProgressBar: View
    private lateinit var tabLayout: TabLayout
    private lateinit var hornsAppImageView: ImageView
    private lateinit var concertsViewPager: ViewPager2
    private lateinit var stubViewInflated: ViewStub

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customProgressBar = view.findViewById(R.id.customProgressBar)
        tabLayout = view.findViewById(R.id.tabLayout)
        hornsAppImageView = view.findViewById(R.id.hornsAppImageView)
        concertsViewPager = view.findViewById(R.id.concertsViewPager)
        stubViewInflated = view.findViewById(R.id.stubView)

        customProgressBar.visibility = View.GONE

        tabLayout.addOnTabSelectedListener(instanceOnTabSelectedListener())

        hornsAppImageView.setOnClickListener {
            ScreenType.PROFILE.asDirection().navigateTo()
        }

        homeViewModel = getViewModel<HomeViewModel, HomeViewModelFactory>()

        homeViewModel.state.observe(viewLifecycleOwner) {
            render(it)
        }

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (concertsViewPager.currentItem == 0) {
                        isEnabled = false
                        activity?.onBackPressed()
                    } else {
                        concertsViewPager.currentItem = concertsViewPager.currentItem - 1
                    }
                }
            })
    }

    override fun render(viewState: HomeViewState) {
        viewState.screens?.let { screens ->
            showChildFragmentTitles(screens)
        }

        viewState.errorMessage?.let {
            showError(
                messageId = viewState.errorMessage,
                allowRetry = viewState.allowRetry
            )
        } ?: kotlin.run { hideError() }

        if (viewState.isLoading) {
            showProgress()
        } else {
            hideProgress()
        }
    }

    private fun showChildFragmentTitles(screens: List<Pair<ScreenDrawer.Type, String>>) {
        concertsViewPager.adapter = ScreenSlidePagerAdapter(this, FragmentFactory(), screens.map { it.first })
        TabLayoutMediator(tabLayout, concertsViewPager) { tab, position ->
            tab.customView = null
            tab.setCustomView(R.layout.custom_tab_layout)
            tab.text = screens[position].second
        }.attach()
        tabLayout.visibility = View.VISIBLE
    }

    private fun showProgress() {
        customProgressBar.fadeIn()
    }

    private fun hideProgress() {
        customProgressBar.fadeOut()
    }

    private fun showError(
        @StringRes messageId: Int,
        allowRetry: Boolean
    ) {
        stubViewInflated.visibility = View.VISIBLE

        view?.findViewById<TextView>(R.id.errorTextView)?.text = getString(messageId)
        val tryAgainTextView = view?.findViewById<TextView>(R.id.tryAgainTextView)

        if (allowRetry) {
            tryAgainTextView?.visibility = View.VISIBLE
        }

        tryAgainTextView?.setOnClickListener {
            homeViewModel.onRefresh()
            tryAgainTextView.visibility = View.GONE
        }
    }

    private fun hideError() {
        stubViewInflated.visibility = View.GONE
    }
}

private class ScreenSlidePagerAdapter(
    activity: Fragment,
    private val fragmentFactory: FragmentFactory,
    private val screens: List<ScreenDrawer.Type>
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = screens.size

    override fun createFragment(position: Int): Fragment {
        return fragmentFactory.getFragment(type = screens[position])
    }
}

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
