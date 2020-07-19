package com.yesferal.hornsapp.app.presentation.concert

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.ads.AdView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.BaseFragment
import com.yesferal.hornsapp.app.presentation.concert.adapter.ConcertAdapter
import com.yesferal.hornsapp.app.presentation.concert.adapter.PageTransformation
import com.yesferal.hornsapp.app.presentation.concert.detail.ConcertActivity
import com.yesferal.hornsapp.app.presentation.concert.detail.EXTRA_PARAM_PARCELABLE
import com.yesferal.hornsapp.app.util.fadeIn
import com.yesferal.hornsapp.app.util.fadeOut
import com.yesferal.hornsapp.app.util.load
import com.yesferal.hornsapp.app.util.setBottomCornersRounded
import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.hada.container.resolve
import kotlinx.android.synthetic.main.fragment_concerts.*
import java.net.URI

class ConcertsFragment
    : BaseFragment() {

    private lateinit var concertAdapter: ConcertAdapter

    override val actionListener by lazy {
        getContainer().resolve<ConcertsPresenter>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_concerts, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        concertImageView.visibility = View.INVISIBLE
        concertImageView.setBottomCornersRounded(dp = 128)

        concertAdapter = initAdapter()
        concertsViewPager.visibility = View.INVISIBLE
        concertsViewPager.adapter = concertAdapter
        concertsViewPager.setPageTransformer(PageTransformation())

        actionListener.onViewCreated()
    }

    fun showProgress() {
        progressBar.fadeIn()
    }

    fun hideProgress() {
        progressBar.fadeOut()
    }

    fun show(concerts: List<Concert>) {
        concertAdapter.setItem(concerts)

        concertsViewPager.fadeIn()
        concertsViewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    val concert = concerts[position]
                    concertImageView.load(concert.headlinerImage)
                    concertImageView.fadeIn()
                }
            }
        )
    }

    fun show(adView: AdView) {
        adContainerLayout.removeAllViews()
        adContainerLayout.addView(adView)
    }

    companion object {
        fun newInstance() = ConcertsFragment()
    }
}

fun ConcertsFragment.initAdapter() =
    ConcertAdapter(
        object :
            ConcertAdapter.Listener {
            override fun onConcertItemClick(concert: Concert) {
                activity?.let {
                    val intent = Intent(
                        it,
                        ConcertActivity::class.java
                    )

                    intent.putExtra(
                        EXTRA_PARAM_PARCELABLE,
                        concert.asParcelable()
                    )

                    startActivity(intent)
                }
            }

            override fun onFacebookButtonClick(uri: URI) {
                val androidUri = try {
                    activity?.packageManager?.getPackageInfo(
                        getString(R.string.facebook_package),
                        0
                    )
                    val event = uri.path.replace("/events", "event")
                    Uri.parse("fb://$event")
                } catch (e: Exception) {
                    Uri.parse(uri.toString())
                }
                startExternalActivity(androidUri)
            }

            override fun onYoutubeButtonClick(uri: URI) {
                startExternalActivity(Uri.parse(uri.toString()))
            }

            private fun startExternalActivity(uri: Uri) {
                val intent = Intent(Intent.ACTION_VIEW,  uri)
                startActivity(intent)
            }

            override fun onFavoriteButtonClick(concert: Concert) {
                actionListener.onFavoriteButtonClick(concert)
            }
        })