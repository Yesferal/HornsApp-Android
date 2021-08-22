package com.yesferal.hornsapp.app.presentation.ui.home

import android.os.Bundle
import android.view.View
import android.view.ViewStub
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.BaseFragment
import com.yesferal.hornsapp.app.presentation.common.extension.fadeIn
import com.yesferal.hornsapp.app.presentation.common.extension.fadeOut
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingFragment
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.NewestFragment
import com.yesferal.hornsapp.app.presentation.ui.favorite.FavoritesFragment
import com.yesferal.hornsapp.app.presentation.ui.profile.ProfileBottomSheetFragment

class HomeFragment : BaseFragment<HomeViewState>() {

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
            childFragmentManager.let { manager ->
                ProfileBottomSheetFragment.newInstance(Bundle()).apply {
                    show(manager, tag)
                }
            }
        }

        homeViewModel = ViewModelProvider(
            viewModelStore,
            hada().resolve<HomeViewModelFactory>()
        ).get(HomeViewModel::class.java)

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
        viewState.fragmentTitles?.let { titles ->
            showChildFragmentTitles(titles)
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

    private fun showChildFragmentTitles(titles: List<String>) {
        concertsViewPager.adapter = ScreenSlidePagerAdapter(this, titles.size)
        TabLayoutMediator(tabLayout, concertsViewPager) { tab, position ->
            tab.customView = null
            tab.setCustomView(R.layout.custom_tab_layout)
            tab.text = titles[position]
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
    private val size: Int
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NewestFragment.newInstance()
            1 -> UpcomingFragment.newInstance()
            else -> FavoritesFragment.newInstance()
        }
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
