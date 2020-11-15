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
import com.yesferal.hornsapp.app.presentation.common.*
import com.yesferal.hornsapp.app.presentation.common.ui.adapter.ItemAdapter
import com.yesferal.hornsapp.app.presentation.common.entity.Item
import com.yesferal.hornsapp.app.presentation.common.entity.ItemParcelable
import com.yesferal.hornsapp.app.presentation.common.ui.custom.*
import com.yesferal.hornsapp.app.presentation.common.ui.BaseFragment
import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.hada.container.resolve
import kotlinx.android.synthetic.main.custom_date_text_view.*
import kotlinx.android.synthetic.main.custom_error.*
import kotlinx.android.synthetic.main.custom_view_progress_bar.*
import kotlinx.android.synthetic.main.fragment_concert.*
import java.net.URI
import java.util.*

class ConcertFragment
    : BaseFragment<ConcertViewState>(),
    RenderEffect {

    private lateinit var bandAdapter: ItemAdapter

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

        val item = arguments?.getParcelable<ItemParcelable>(
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
        bandAdapter = ItemAdapter(instanceItemAdapterListener())

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

    private fun show(concert: Concert) {

        dayTextView.setUpWith(concert.day)
        monthTextView.setUpWith(concert.month)

        favoriteImageView.isChecked = concert.isFavorite
        favoriteImageView.setOnCheckedChangeListener { isChecked ->
            actionListener.onFavoriteImageViewClick(concert, isChecked)
        }

        /**
         * TODO("Move to Presenter")
         */
        val items = concert.bands?.map {
            Item(it._id, it.name, it.membersImage)
        }

        bandAdapter.setItem(items)

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
            setOnClickListener { concert.date?.let { date ->
                val calendar = Calendar.getInstance()
                calendar.time = date

                startCalendar(concert, calendar)
            }}
        }

        descriptionTextView.apply {
            setImageView(R.drawable.ic_information)
            setText(getString(R.string.about_concert), concert.description)
        }

        showYoutube(concert.trailerUrl)
        showFacebook(concert.facebookUrl)
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
        concert: Concert,
        calendar: Calendar
    ) {
        val intent = Intent(Intent.ACTION_EDIT)
        intent.type = getString(R.string.calendar_action_type)
        intent.putExtra(CalendarContract.Events.TITLE, concert.name)
        intent.putExtra("beginTime", calendar.timeInMillis)
        intent.putExtra("endTime", calendar.timeInMillis + 180 * 60 * 1000)
        intent.putExtra(CalendarContract.Events.DESCRIPTION, concert.description)
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, concert.venue?.name)
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
            item: ItemParcelable
        ) : ConcertFragment {
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_PARAM_PARCELABLE, item)

            return ConcertFragment().apply {
                arguments = bundle
            }
        }
    }
}

private fun ConcertFragment.instanceItemAdapterListener() =
    object : ItemAdapter.Listener {
        override fun onClick(item: Item) {
            childFragmentManager.let {
                val bundle = Bundle()
                bundle.putParcelable(EXTRA_PARAM_PARCELABLE, item.asParcelable())

                BandBottomSheetFragment.newInstance(bundle).apply {
                    show(it, tag)
                }
            }
        }
    }