package com.yesferal.hornsapp.app.presentation.ui.concert.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.util.TypedValue
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.google.android.material.tabs.TabLayoutMediator
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.framework.adMob.AdViewData
import com.yesferal.hornsapp.app.presentation.common.base.*
import com.yesferal.hornsapp.app.presentation.ui.band.BandBottomSheetFragment
import com.yesferal.hornsapp.app.presentation.common.custom.*
import com.yesferal.hornsapp.app.presentation.common.extension.fadeIn
import com.yesferal.hornsapp.app.presentation.common.extension.fadeOut
import com.yesferal.hornsapp.app.presentation.common.extension.setUpWith
import com.yesferal.hornsapp.hada.parameter.Parameters
import com.yesferal.hornsapp.multitype.MultiTypeAdapter
import kotlinx.android.synthetic.main.custom_date_text_view.*
import kotlinx.android.synthetic.main.custom_error.*
import kotlinx.android.synthetic.main.custom_view_progress_bar.*
import kotlinx.android.synthetic.main.fragment_concert.*
import kotlinx.android.synthetic.main.fragment_concert.adContainerLayout
import java.net.URI
import java.util.*

const val EXTRA_PARAM_PARCELABLE = "EXTRA_PARAM_PARCELABLE"

class ConcertFragment
    : BaseFragment<ConcertViewState>(),
    RenderEffect {

    override val layout: Int
        get() = R.layout.fragment_concert

    private val args: ConcertFragmentArgs by navArgs()

    private lateinit var concertViewModel: ConcertViewModel
    private lateinit var multiTypeAdapter: MultiTypeAdapter

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

        setUpBandsViewPager()

        closeImageView.setOnClickListener {
            activity?.onBackPressed()
        }

        concertViewModel = ViewModelProvider(
            this,
            hada().resolve<ConcertViewModelFactory>(params = Parameters(concert.id))
        ).get(ConcertViewModel::class.java)

        concertViewModel.state.observe(viewLifecycleOwner) {
            render(it)
        }

        concertViewModel.effect.observe(viewLifecycleOwner) {
            render(it)
        }
    }

    private fun setUpBandsViewPager() {
        multiTypeAdapter = MultiTypeAdapter(listener = instanceAdapterListener())

        val bigMargin = 24F
        val dpWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            bigMargin,
            context?.resources?.displayMetrics
        ).toInt()

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(dpWidth/2))
        compositePageTransformer.addTransformer(ScalePageTransformation())

        bandsViewPager.also {
            it.adapter = multiTypeAdapter
            it.clipToPadding = false
            it.clipChildren = false
            it.offscreenPageLimit = 3
            it.setPageTransformer(compositePageTransformer)
            it.setPadding(0, 0, dpWidth, 0)
        }

        TabLayoutMediator(tabLayout, bandsViewPager) { _,_ -> }
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

        viewState.adViewData?.let {
            showAd(it)
        }

        viewState.errorMessageId?.let {
            showError(messageId =  it)
        }

        if (viewState.isLoading) {
            showProgress()
        } else {
            hideProgress()
        }
    }

    private fun show(concert: ConcertViewData) {

        titleTextView.setUpWith(concert.name)
        dayTextView.setUpWith(concert.day)
        monthTextView.setUpWith(concert.month)

        favoriteImageView.isChecked = concert.isFavorite
        favoriteImageView.setOnCheckedChangeListener { isChecked ->
            concertViewModel.onFavoriteImageViewClick(concert, isChecked)
        }

        enableTicketPurchase(concert.ticketingHost, concert.ticketingUrl)

        venueTextView.apply {
            setImageView(R.drawable.ic_map)
            setText(concert.venue?.name, getString(R.string.go_to_maps))
            setOnClickListener { concert.venue?.let {
                val latitude = it.latitude
                val longitude = it.longitude
                val uri = URI("geo:${latitude},${longitude}?q=${Uri.encode(it.name)}")

                startGoogleMaps(uri)
            }}
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
            setText(getString(R.string.about_concert), concert.description)
        }

        showYoutube(concert.trailerUrl)
        showFacebook(concert.facebookUrl)
    }

    private fun show(bands: List<BandViewData>) {
        multiTypeAdapter.setModels(bands)
    }

    private fun enableTicketPurchase(
        ticketingHost: String?,
        ticketingUrl: URI?
    ) {
        ticketingUrl?.let {
            ticketTextView.apply {
                setImageView(R.drawable.ic_ticket)
                setText(getString(R.string.available_in))
            }
            buyTicketsTextView.setUpWith(ticketingHost ?: getString(R.string.go_now))
            buyTicketsTextView.setOnClickListener {
                startExternalActivity(ticketingUrl)
            }
        }?: kotlin.run {
            ticketTextView.visibility = View.GONE
            buyTicketsTextView.visibility = View.GONE
        }
    }

    private fun showFacebook(facebookUrl: URI?) {
        facebookUrl?.let {
            facebookTextView.apply {
                setImageView(R.drawable.ic_facebook)
                setText(getString(R.string.fan_page), getString(R.string.go_to_event))
                setOnClickListener {
                    val event = facebookUrl.path.replace("/events", "event")
                    val facebookAppUri = URI("fb://$event")

                    startFacebook(facebookUrl, facebookAppUri)
                }
            }
        }?: kotlin.run {
            facebookTextView.visibility = View.GONE
        }
    }

    private fun showYoutube(youtubeTrailer: URI?) {
        youtubeTrailer?.let {
            youtubeTextView.apply {
                setImageView(R.drawable.ic_youtube)
                setText(getString(R.string.official_video), getString(R.string.go_to_youtube))
                setOnClickListener {
                    startExternalActivity(youtubeTrailer)
                }
            }
        }?: kotlin.run {
            youtubeTextView.visibility = View.GONE
        }
    }

    private fun showAd(adViewData: AdViewData) {
        adContainerLayout.addAdView(adViewData)
    }

    private fun showProgress() {
        customProgressBar.fadeIn()
    }

    private fun hideProgress() {
        customProgressBar.fadeOut()
    }

    private fun showError(@StringRes messageId: Int) {
        stubView.visibility = View.VISIBLE
        errorTextView.text = getString(messageId)
    }

    private fun startFacebook(facebookUri: URI, facebookAppUri: URI) {
        startExternalActivity(
            facebookAppUri,
            getString(R.string.facebook_package),
            onError = {
                startExternalActivity(facebookUri)
            })
    }

    private fun startGoogleMaps(uri: URI) {
        startExternalActivity(uri, getString(R.string.maps_package))
    }

    private fun startCalendar(
        concertViewData: ConcertViewData
    ) {
        val intent = Intent(Intent.ACTION_EDIT)
        intent.type = getString(R.string.calendar_action_type)
        intent.putExtra(CalendarContract.Events.TITLE, concertViewData.name)
        val dtStart = concertViewData
            .timeInMillis
            ?.minus(TimeZone.getDefault().rawOffset + TimeZone.getDefault().dstSavings)
            ?: 0
        intent.putExtra("beginTime", dtStart)
        intent.putExtra("endTime", dtStart + (2 * 60 * 60 * 1000))
        intent.putExtra(CalendarContract.Events.DESCRIPTION, concertViewData.description)
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, concertViewData.venue?.name)
        startActivity(intent)
    }

    override fun render(effect: ViewEffect) {
        when(effect) {
            is ViewEffect.Toast -> {
                showToast(effect.errorMessageId)
            }
        }
    }
}

private fun ConcertFragment.instanceAdapterListener() =
    object : BandViewData.Listener {
        override fun onClick(bandViewData: BandViewData) {
            childFragmentManager.let {
                val bundle = Bundle()
                bundle.putParcelable(EXTRA_PARAM_PARCELABLE, bandViewData.asParcelable())

                BandBottomSheetFragment.newInstance(bundle).apply {
                    show(it, tag)
                }
            }
        }
    }