package com.yesferal.hornsapp.app.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.gms.ads.AdView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.ui.BaseFragment
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.ConcertsFragment
import com.yesferal.hornsapp.app.presentation.common.ui.custom.fadeIn
import com.yesferal.hornsapp.app.presentation.common.ui.custom.fadeOut
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.NewestFragment
import com.yesferal.hornsapp.app.presentation.ui.favorite.FavoritesFragment
import com.yesferal.hornsapp.hada.container.resolve
import kotlinx.android.synthetic.main.custom_error.*
import kotlinx.android.synthetic.main.custom_view_progress_bar.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment
    : BaseFragment<HomeViewState>() {
    private lateinit var stubViewInflated: View

    override val actionListener by lazy {
        container.resolve<HomePresenter>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabLayout.addOnTabSelectedListener(instanceOnTabSelectedListener())

        actionListener.onViewCreated()
    }

    override fun render(viewState: HomeViewState) {
        viewState.fragmentTitles?.let { titles ->
            showFragments(titles)
        }

        viewState.adView?.let { adView ->
            showAd(adView)
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

    private fun showFragments(titles: List<String>) {
        activity?.let {
            val pagerAdapter = ScreenSlidePagerAdapter(it, titles)
            concertsViewPager.adapter = pagerAdapter
        }

        TabLayoutMediator(tabLayout, concertsViewPager) { tab, position ->
            tab.customView = null
            tab.setCustomView(R.layout.custom_tab_layout)
            tab.text = titles[position]
        }.attach()
    }

    private fun showAd(adView: AdView) {
        adContainerLayout.removeAllViews()
        adContainerLayout.addView(adView)
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
            actionListener.onRefresh()
            tryAgainTextView.visibility = View.GONE
        }
    }

    private fun hideError() {
        if (::stubViewInflated.isInitialized) {
            stubViewInflated.visibility = View.GONE
        }
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}

private class ScreenSlidePagerAdapter(
    fragmentActivity: FragmentActivity,
    private val fragments: List<String>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                NewestFragment.newInstance()
            }
            1 -> {
                ConcertsFragment.newInstance()
            }
            else -> {
                FavoritesFragment.newInstance()
            }
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