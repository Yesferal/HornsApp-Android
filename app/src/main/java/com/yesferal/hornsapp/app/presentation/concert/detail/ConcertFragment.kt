package com.yesferal.hornsapp.app.presentation.concert.detail

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import com.google.android.gms.ads.AdView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.BaseFragment
import com.yesferal.hornsapp.app.presentation.item.adapter.ItemAdapter
import com.yesferal.hornsapp.app.presentation.item.adapter.Item
import com.yesferal.hornsapp.app.presentation.item.adapter.mapToBaseItem
import com.yesferal.hornsapp.app.presentation.common.ItemParcelable
import com.yesferal.hornsapp.app.util.*
import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.domain.entity.Venue
import com.yesferal.hornsapp.hada.container.resolve
import kotlinx.android.synthetic.main.custom_error.*
import kotlinx.android.synthetic.main.custom_view_progress_bar.*
import kotlinx.android.synthetic.main.fragment_concert.*
import java.net.URI

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

        bandAdapter = ItemAdapter(instanceItemAdapterListener())
        bandRecyclerView.also {
            it.adapter = bandAdapter
            it.layoutManager = linearLayoutManager
            it.addItemDecoration(RecyclerViewDecorator(padding = 8))
        }

        favoriteImageView.isChecked = item.isFavorite
        actionListener.onViewCreated(item.id)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun show(concert: Concert) {
        favoriteImageView.setOnCheckedChangeListener { isChecked ->
            actionListener.onFavoriteImageViewClick(concert, isChecked)
        }

        descriptionTextView.setUpWith(concert.description)

        datetimeTextView.apply {
            setImageView(R.drawable.ic_calendar)
            setText(concert.dateTime, concert.time)
        }

        genreTextView.apply {
            setImageView(R.drawable.ic_music_note)
            setText(concert.subGenres)
        }

        val items = concert.bands?.map {
            it.mapToBaseItem()
        }
        bandAdapter.setItem(items)

        enableTicketPurchase(concert.ticketingHost, concert.ticketingUrl)
        showVenue(concert.venue)
        showYoutube(concert.trailerUrl)
        showFacebook(concert.facebookUrl)
    }

    private fun enableTicketPurchase(
        ticketingHost: String?,
        ticketingUrl: URI?
    ) {
        ticketingUrl?.let {
            buyTicketsButton.setUpWith(ticketingHost
                ?: getString(R.string.go_now))
            buyTicketsButton.setOnClickListener {
                startExternalActivity(ticketingUrl)
            }
        }?: kotlin.run {
            buyTicketsButton.visibility = View.GONE
        }
    }

    private fun showVenue(venue: Venue?) {
        venue?.let {
            venueTextView.setImageView(R.drawable.ic_map)
            venueTextView.setText(venue.name, getString(R.string.go_to_map))
            venueTextView.setOnClickListener {
                val latitude = venue.latitude
                val longitude = venue.longitude
                // TODO("Move to mapper")
                val uri = URI("geo:${latitude},${longitude}?q=${Uri.encode(venue.name)}")

                startExternalActivity(uri, getString(R.string.maps_package))
            }
        }?: kotlin.run {
            venueTextView.visibility = View.GONE
        }
    }

    private fun showFacebook(facebookUrl: URI?) {
        facebookUrl?.let {
            facebookTextView.setImageView(R.drawable.ic_facebook)
            facebookTextView.setText(getString(R.string.fan_page), getString(R.string.go_to_event))
            facebookTextView.visibility = View.VISIBLE
            facebookTextView.setOnClickListener {
                // TODO ("Move to Mapper")
                val event = facebookUrl.path.replace("/events", "event")
                val fbUri = URI("fb://$event")

                startExternalActivity(fbUri, getString(R.string.facebook_package)) {
                    startExternalActivity(facebookUrl)
                }
            }
        }?: kotlin.run {
            facebookTextView.visibility = View.GONE
        }
    }

    private fun showYoutube(youtubeTrailer: URI?) {
        youtubeTrailer?.let {
            youtubeTextView.setImageView(R.drawable.ic_youtube)
            youtubeTextView.setText(getString(R.string.official_video), getString(R.string.go_to_youtube))
            youtubeTextView.visibility = View.VISIBLE
            youtubeTextView.setOnClickListener {
                startExternalActivity(youtubeTrailer)
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