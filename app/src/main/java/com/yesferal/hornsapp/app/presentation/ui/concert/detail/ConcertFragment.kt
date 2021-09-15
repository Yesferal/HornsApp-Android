package com.yesferal.hornsapp.app.presentation.ui.concert.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
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
import com.yesferal.hornsapp.app.presentation.common.custom.CheckBoxImageView
import com.yesferal.hornsapp.app.presentation.common.custom.ImageTextView
import com.yesferal.hornsapp.app.presentation.common.custom.ScalePageTransformation
import com.yesferal.hornsapp.app.presentation.ui.band.BandBottomSheetFragment
import com.yesferal.hornsapp.app.presentation.common.extension.fadeIn
import com.yesferal.hornsapp.app.presentation.common.extension.fadeOut
import com.yesferal.hornsapp.app.presentation.common.extension.setUpWith
import com.yesferal.hornsapp.app.presentation.common.render.RenderFragment
import com.yesferal.hornsapp.app.presentation.di.hada.getViewModel
import com.yesferal.hornsapp.multitype.DelegateAdapter
import java.net.URI
import java.util.*

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
            .setListener(instanceAdapterListener())
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
            setOnClickListener {
                concert.venue?.let {
                    val latitude = it.latitude
                    val longitude = it.longitude
                    val uri = URI("geo:${latitude},${longitude}?q=${Uri.encode(it.name)}")

                    startGoogleMaps(uri)
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
            setText(getString(R.string.about), concert.description)
        }

        showYoutube(concert.trailerUrl)
        showFacebook(concert.facebookUrl)
    }

    private fun show(bands: List<BandViewData>) {
        delegateAdapter.updateItems(bands)
    }

    private fun enableTicketPurchase(
        ticketingHost: String?,
        ticketingUrl: URI?
    ) {
        ticketingUrl?.let {
            ticketTextView.apply {
                setImageView(R.drawable.ic_ticket)
                setText(getString(R.string.available_on))
            }
            buyTicketsTextView.setUpWith(ticketingHost ?: getString(R.string.go_now))
            buyTicketsTextView.setOnClickListener {
                startExternalActivity(ticketingUrl)
            }
        } ?: kotlin.run {
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
        } ?: kotlin.run {
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
