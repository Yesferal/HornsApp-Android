/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.concert.detail

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewStub
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.ExternalNavViewData
import com.yesferal.hornsapp.app.presentation.common.custom.CheckBoxImageView
import com.yesferal.hornsapp.app.presentation.common.custom.ImageTextView
import com.yesferal.hornsapp.app.presentation.common.custom.ScalePageTransformation
import com.yesferal.hornsapp.app.presentation.common.extension.fadeIn
import com.yesferal.hornsapp.app.presentation.common.extension.fadeOut
import com.yesferal.hornsapp.app.presentation.common.extension.setUpCTA
import com.yesferal.hornsapp.app.presentation.common.extension.setUpWith
import com.yesferal.hornsapp.app.presentation.common.render.RenderFragment
import com.yesferal.hornsapp.core.domain.entity.Venue
import com.yesferal.hornsapp.core.domain.navigator.Navigator
import com.yesferal.hornsapp.core.domain.navigator.ScreenType
import com.yesferal.hornsapp.delegate.DelegateAdapter
import com.yesferal.hornsapp.hadi_android.getViewModel

const val EXTRA_PARAM_PARCELABLE = "EXTRA_PARAM_PARCELABLE"

class ConcertFragment : RenderFragment<ConcertViewState>() {

    override val layout = R.layout.fragment_concert

    private val args: ConcertFragmentArgs by navArgs()

    private lateinit var concertViewModel: ConcertViewModel
    private lateinit var delegateAdapter: DelegateAdapter

    private lateinit var closeImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var bandsViewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var dayTextView: TextView
    private lateinit var monthTextView: TextView
    private lateinit var favoriteImageView: CheckBoxImageView
    private lateinit var venueTextView: ImageTextView
    private lateinit var datetimeTextView: ImageTextView
    private lateinit var descriptionTextView: ImageTextView
    private lateinit var ticketTextView: ImageTextView
    private lateinit var buyTicketsTextView: TextView
    private lateinit var facebookTextView: ImageTextView
    private lateinit var youtubeTextView: ImageTextView
    private lateinit var customProgressBar: View
    private lateinit var stubView: ViewStub

    private val bandViewDataListener =
        object : BandViewData.Listener {
            override fun onClick(bandViewData: BandViewData) {
                Navigator.Builder()
                    .to(ScreenType.BAND_DETAIL)
                    .with(bandViewData.asParcelable())
                    .build()
                    .navigateTo()
            }
        }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val concert = args.concert
        if (concert == null) {
            activity?.onBackPressed()
            return
        }

        closeImageView = view.findViewById(R.id.closeImageView)
        titleTextView = view.findViewById(R.id.titleTextView)
        bandsViewPager = view.findViewById(R.id.bandsViewPager)
        tabLayout = view.findViewById(R.id.tabLayout)
        dayTextView = view.findViewById(R.id.dayTextView)
        monthTextView = view.findViewById(R.id.monthTextView)
        favoriteImageView = view.findViewById(R.id.favoriteImageView)
        venueTextView = view.findViewById(R.id.venueTextView)
        datetimeTextView = view.findViewById(R.id.datetimeTextView)
        descriptionTextView = view.findViewById(R.id.descriptionTextView)
        ticketTextView = view.findViewById(R.id.ticketTextView)
        buyTicketsTextView = view.findViewById(R.id.buyTicketsTextView)
        facebookTextView = view.findViewById(R.id.facebookTextView)
        youtubeTextView = view.findViewById(R.id.youtubeTextView)
        customProgressBar = view.findViewById(R.id.customProgressBar)
        stubView = view.findViewById(R.id.stubView)

        setUpBandsViewPager()

        closeImageView.setOnClickListener {
            activity?.onBackPressed()
        }

        titleTextView.setUpWith(concert.name)

        concertViewModel =
            getViewModel<ConcertViewModel, ConcertViewModelFactory>(param = concert.id)

        concertViewModel.state.observe(viewLifecycleOwner) {
            render(it)
        }

        concertViewModel.effect.observe(viewLifecycleOwner) {
            render(it)
        }
    }

    private fun setUpBandsViewPager() {
        delegateAdapter = DelegateAdapter.Builder()
            .setListener(bandViewDataListener)
            .build()

        val bigMargin = 24F
        val dpWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            bigMargin,
            context?.resources?.displayMetrics
        ).toInt()

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(dpWidth / 2))
        compositePageTransformer.addTransformer(ScalePageTransformation())

        bandsViewPager.also {
            it.adapter = delegateAdapter
            it.clipToPadding = false
            it.clipChildren = false
            it.offscreenPageLimit = 3
            it.setPageTransformer(compositePageTransformer)
            it.setPadding(0, 0, dpWidth, 0)
        }

        TabLayoutMediator(tabLayout, bandsViewPager) { _, _ -> }
            .attach()
    }

    override fun render(
        viewState: ConcertViewState
    ) {
        viewState.concert?.let {
            show(concert = it)
        }

        viewState.bands?.let {
            show(bands = it)
        }

        viewState.errorMessageId?.let {
            showError(messageId = it)
        }

        if (viewState.isLoading) {
            showProgress()
        } else {
            hideProgress()
        }
    }

    private fun show(concert: ConcertViewData) {
        titleTextView.setUpWith(concert.concert.name)
        dayTextView.setUpWith(concert.day)
        monthTextView.setUpWith(concert.month)

        favoriteImageView.isChecked = concert.concert.isFavorite
        favoriteImageView.setOnCheckedChangeListener { isChecked ->
            concertViewModel.onFavoriteImageViewClick(concert, isChecked)
        }

        enableTicketPurchase(concert.concert.ticketingUrl, concert.concert.ticketingHost)

        venueTextView.apply {
            setImageView(R.drawable.ic_map)
            setText(concert.concert.venue?.name, getString(R.string.go_to_maps))
            setOnClickListener {
                concert.concert.venue?.let {
                    startGoogleMaps(it)
                }
            }
        }

        datetimeTextView.apply {
            setImageView(R.drawable.ic_calendar)
            setText(concert.dateTime, getString(R.string.add_to_calendar))
            setOnClickListener {
                startCalendar(concert)
            }
        }

        descriptionTextView.apply {
            setImageView(R.drawable.ic_information)
            setText(getString(R.string.about), concert.concert.description)
        }

        showYoutube(concert.concert.trailerUrl)
        showFacebook(concert.concert.facebookUrl)
    }

    private fun show(bands: List<BandViewData>) {
        delegateAdapter.updateDelegates(bands)
    }

    private fun enableTicketPurchase(
        ticketingUrl: String?,
        ticketingHost: String?
    ) {
        buyTicketsTextView.setUpCTA(ticketingHost, ticketingUrl) {
            ticketingUrl?.let { startExternalActivity(ExternalNavViewData(it)) }
        }

        ticketTextView.setImageView(R.drawable.ic_ticket)
        if (ticketingUrl.isNullOrEmpty()) {
            ticketTextView.setText(getString(R.string.available_soon))
        } else {
            ticketTextView.setText(getString(R.string.available_on), getString(R.string.buy_here) )
        }
    }

    private fun showFacebook(facebookUrl: String?) {
        facebookUrl?.let { url ->
            facebookTextView.apply {
                setImageView(R.drawable.ic_facebook)
                setText(getString(R.string.fan_page), getString(R.string.go_to_event))
                setOnClickListener {
                    startFacebook(url)
                }
            }
        } ?: kotlin.run {
            facebookTextView.visibility = View.GONE
        }
    }

    private fun showYoutube(youtubeTrailer: String?) {
        youtubeTrailer?.let { url ->
            youtubeTextView.apply {
                setImageView(R.drawable.ic_youtube)
                setText(getString(R.string.official_video), getString(R.string.go_to_youtube))
                setOnClickListener {
                    startExternalActivity(ExternalNavViewData(url))
                }
            }
        } ?: kotlin.run {
            youtubeTextView.visibility = View.GONE
        }
    }

    private fun showProgress() {
        customProgressBar.fadeIn()
    }

    private fun hideProgress() {
        customProgressBar.fadeOut()
    }

    private fun showError(@StringRes messageId: Int) {
        stubView.visibility = View.VISIBLE
        view?.findViewById<TextView>(R.id.errorTextView)?.text = getString(messageId)
    }

    private fun startFacebook(facebookUri: String) {
        startExternalActivity(ExternalNavViewData(facebookUri))
    }

    private fun startGoogleMaps(venue: Venue) {
        Navigator.Builder()
            .to(ScreenType.MAP)
            .with(VenueViewData(venue))
            .build()
            .navigateTo()
    }

    private fun startCalendar(
        concertViewData: ConcertViewData
    ) {
        Navigator.Builder()
            .to(ScreenType.CALENDAR)
            .with(concertViewData)
            .build()
            .navigateTo()
    }
}
