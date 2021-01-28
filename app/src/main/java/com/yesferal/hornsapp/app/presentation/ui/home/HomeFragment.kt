package com.yesferal.hornsapp.app.presentation.ui.home

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
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
import kotlinx.android.synthetic.main.custom_error.*
import kotlinx.android.synthetic.main.custom_view_progress_bar.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment
    : BaseFragment<HomeViewState>() {
    private lateinit var stubViewInflated: View
    private lateinit var homeViewModel: HomeViewModel

    override val layout: Int
        get() = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabLayout.addOnTabSelectedListener(instanceOnTabSelectedListener())
        concertsViewPager.adapter = ScreenSlidePagerAdapter(activity as FragmentActivity)

        hornsAppImageView.setOnClickListener {
            childFragmentManager.let { manager ->
                ProfileBottomSheetFragment.newInstance(Bundle()).apply {
                    show(manager, tag)
                }
            }
        }

        activity?.viewModelStore?.let { viewModelStore ->
            homeViewModel = ViewModelProvider(
                viewModelStore,
                hada().resolve<HomeViewModelFactory>()
            ).get(HomeViewModel::class.java)

            homeViewModel.state.observe(viewLifecycleOwner) {
                render(it)
            }

            homeViewModel.getFavoriteConcerts()
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(concertsViewPager.currentItem == 0){
                    isEnabled = false
                    activity?.onBackPressed()
                }else{
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
                messageId =  viewState.errorMessage,
                allowRetry = viewState.allowRetry
            )
        }?: kotlin.run { hideError() }

        if (viewState.isLoading) {
            showProgress()
        } else {
            hideProgress()
        }
    }

    private fun showChildFragmentTitles(titles: List<String>) {
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
        if (!::stubViewInflated.isInitialized) {
            stubViewInflated = stubView.inflate()
        }
        stubViewInflated.visibility = View.VISIBLE

        errorTextView.text = getString(messageId)
        if (allowRetry) {
            tryAgainTextView.visibility = View.VISIBLE
        }

        tryAgainTextView.setOnClickListener {
            homeViewModel.onRefresh()
            tryAgainTextView.visibility = View.GONE
        }
    }

    private fun hideError() {
        if (::stubViewInflated.isInitialized) {
            stubViewInflated.visibility = View.GONE
        }
    }
}

private class ScreenSlidePagerAdapter(
        activity: FragmentActivity
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3

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