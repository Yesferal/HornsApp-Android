package com.yesferal.hornsapp.app.presentation.ui.concert.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.google.android.gms.ads.AdView
import com.google.android.material.tabs.TabLayoutMediator
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.ui.band.BandBottomSheetFragment
import com.yesferal.hornsapp.app.presentation.common.custom.*
import com.yesferal.hornsapp.app.presentation.common.base.BaseFragment
import com.yesferal.hornsapp.app.presentation.common.base.ParcelableViewData
import com.yesferal.hornsapp.app.presentation.common.base.RenderEffect
import com.yesferal.hornsapp.app.presentation.common.base.ViewEffect
import com.yesferal.hornsapp.app.presentation.ui.concert.detail.adapter.BandsAdapter
import com.yesferal.hornsapp.hada.container.resolve
import kotlinx.android.synthetic.main.custom_date_text_view.*
import kotlinx.android.synthetic.main.custom_error.*
import kotlinx.android.synthetic.main.custom_view_progress_bar.*
import kotlinx.android.synthetic.main.fragment_concert.*
import java.net.URI

class ConcertFragment
    : BaseFragment<ConcertViewState>(),
    RenderEffect {

    private lateinit var bandAdapter: BandsAdapter

    override val actionListener by lazy {
        container.resolve<ConcertPresenter>()
    }

    var listener: Listener? = null
    interface Listener {
        fun show(adView: AdView)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_concert, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val item = arguments?.getParcelable<ParcelableViewData>(
            EXTRA_PARAM_PARCELABLE
        )

        if (item == null) {
            activity?.finish()
            return
        }

        setUpBandsViewPager()

        actionListener.onViewCreated(item.id)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun setUpBandsViewPager() {
        bandAdapter = BandsAdapter(instanceBandAdapterListener())

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
            it.adapter = bandAdapter
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

        viewState.adView?.let {
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

        dayTextView.setUpWith(concert.day)
        monthTextView.setUpWith(concert.month)

        favoriteImageView.isChecked = concert.isFavorite
        favoriteImageView.setOnCheckedChangeListener { isChecked ->
            actionListener.onFavoriteImageViewClick(concert, isChecked)
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
        bandAdapter.setItem(bands)
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

    private fun showAd(adView: AdView) {
        listener?.show(adView)
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
        intent.putExtra("beginTime", concertViewData.timeInMillis)
        intent.putExtra("endTime", concertViewData.timeInMillis?: 0 + 180 * 60 * 1000)
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

    companion object {
        fun newInstance(
            item: ParcelableViewData
        ) : ConcertFragment {
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_PARAM_PARCELABLE, item)

            return ConcertFragment().apply {
                arguments = bundle
            }
        }
    }
}

private fun ConcertFragment.instanceBandAdapterListener() =
    object : BandsAdapter.Listener {
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