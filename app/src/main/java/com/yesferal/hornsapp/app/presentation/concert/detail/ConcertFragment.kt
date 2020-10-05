package com.yesferal.hornsapp.app.presentation.concert.detail

import android.content.Intent
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
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.BaseFragment
import com.yesferal.hornsapp.app.presentation.item.adapter.ItemAdapter
import com.yesferal.hornsapp.app.presentation.item.adapter.Item
import com.yesferal.hornsapp.app.presentation.item.adapter.mapToBaseItem
import com.yesferal.hornsapp.app.presentation.common.ItemParcelable
import com.yesferal.hornsapp.app.util.*
import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.hada.container.resolve
import kotlinx.android.synthetic.main.custom_date_text_view.*
import kotlinx.android.synthetic.main.custom_error.*
import kotlinx.android.synthetic.main.custom_view_progress_bar.*
import kotlinx.android.synthetic.main.fragment_concert.*
import java.net.URI
import java.util.*

class ConcertFragment
    : BaseFragment() {

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
        favoriteImageView.isChecked = item.isFavorite
        buyTicketsButton.visibility = View.GONE

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
    }

    fun show(concert: Concert) {
        favoriteImageView.setOnCheckedChangeListener { isChecked ->
            actionListener.onFavoriteImageViewClick(concert, isChecked)
        }

        dayTextView.setUpWith(concert.day)
        monthTextView.setUpWith(concert.month)

        datetimeTextView.apply {
            setImageView(R.drawable.ic_calendar)
            setText(concert.dateTime, getString(R.string.add_to_calendar))
            setOnClickListener {
                actionListener.onDateClick(concert)
            }
        }

        venueTextView.apply {
            setImageView(R.drawable.ic_map)
            setText(concert.venue?.name, getString(R.string.go_to_maps))
            setOnClickListener {
                actionListener.onVenueClick(concert.venue)
            }
        }

        descriptionTextView.apply {
            setImageView(R.drawable.ic_information)
            setText(getString(R.string.about_concert), concert.description)
        }
        val items = concert.bands?.map {
            it.mapToBaseItem()
        }
        bandAdapter.setItem(items)

        enableTicketPurchase(concert.ticketingHost, concert.ticketingUrl)

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
            buyTicketsButton.setUpWith(ticketingHost ?: getString(R.string.go_now))
            buyTicketsButton.visibility = View.VISIBLE
            buyTicketsButton.setOnClickListener {
                startExternalActivity(ticketingUrl)
            }
        }?: kotlin.run {
            ticketTextView.visibility = View.GONE
            buyTicketsButton.visibility = View.GONE
        }
    }

    private fun showFacebook(facebookUrl: URI?) {
        facebookUrl?.let {
            facebookTextView.apply {
                setImageView(R.drawable.ic_facebook)
                setText(getString(R.string.fan_page), getString(R.string.go_to_event))
                visibility = View.VISIBLE
                setOnClickListener {
                    actionListener.onFacebookClick(facebookUrl)
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
                visibility = View.VISIBLE
                setOnClickListener {
                    startExternalActivity(youtubeTrailer)
                }
            }
        }?: kotlin.run {
            youtubeTextView.visibility = View.GONE
        }
    }

    fun showAd(adView: AdView) {
        listener?.show(adView)
    }

    fun showProgress() {
        customProgressBar.fadeIn()
    }

    fun hideProgress() {
        customProgressBar.fadeOut()
    }

    fun showError(@StringRes messageId: Int) {
        stubView.visibility = View.VISIBLE
        errorTextView.text = getString(messageId)
    }

    fun openFacebook(facebookUri: URI, facebookAppUri: URI) {
        startExternalActivity(
            facebookAppUri,
            getString(R.string.facebook_package),
            onError = {
                startExternalActivity(facebookUri)
            })
    }

    fun openGoogleMaps(uri: URI) {
        startExternalActivity(uri, getString(R.string.maps_package))
    }

    fun openCalendar(
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
            showToast(R.string.app_name)
        }
    }