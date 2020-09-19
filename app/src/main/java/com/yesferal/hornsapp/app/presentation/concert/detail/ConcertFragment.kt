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
import com.yesferal.hornsapp.domain.entity.Local
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

        localTextView.apply {
            setImageView(R.drawable.ic_map)
            setText(concert.local?.name)
        }

        datetimeTextView.apply {
            setImageView(R.drawable.ic_calendar)
            setText(concert.datetime)
        }

        genreTextView.apply {
            setImageView(R.drawable.ic_music_note)
            setText(concert.genres)
        }

        val items = concert.bands?.map {
            it.mapToBaseItem()
        }
        bandAdapter.setItem(items)

        enableTicketPurchase(concert.ticketingHost, concert.ticketingUrl)
        show(local = concert.local)
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

    private fun show(local: Local?) {
        local?.let {
            localTextView.setOnClickListener {
                val latitude = local.latitude
                val longitude = local.longitude
                // TODO("Move to mapper")
                val uri = URI("geo:${latitude},${longitude}?q=${Uri.encode(local.name)}")

                startExternalActivity(uri, getString(R.string.maps_package))
            }
        }
    }

    private fun showFacebook(facebookUrl: URI?) {
        /*facebookUrl?.let {
            facebookImageView.visibility = (View.VISIBLE)
            facebookImageView.setOnClickListener {
                // TODO ("Move to Mapper")
                val event = facebookUrl.path.replace("/events", "event")
                val fbUri = URI("fb://$event")

                startExternalActivity(fbUri, getString(R.string.facebook_package)) {
                    startExternalActivity(facebookUrl)
                }
            }
        }?: kotlin.run {
            facebookImageView.visibility = (View.GONE)
        }*/
    }

    private fun showYoutube(youtubeTrailer: URI?) {
        /*youtubeTrailer?.let {
            trailerImageView.visibility = (View.VISIBLE)
            trailerImageView.setOnClickListener {
                startExternalActivity(youtubeTrailer)
            }
        }?: kotlin.run {
            trailerImageView.visibility = (View.GONE)
        }*/
    }

    fun show(adView: AdView) {
        listener?.show(adView)
    }

    fun showProgress() {
        customProgressBar.fadeIn()
    }

    fun hideProgress() {
        customProgressBar.fadeOut()
    }

    fun show(@StringRes error: Int) {
        stubView.visibility = View.VISIBLE
        errorTextView.text = getString(error)
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