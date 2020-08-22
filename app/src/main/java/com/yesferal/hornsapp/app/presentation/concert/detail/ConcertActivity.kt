package com.yesferal.hornsapp.app.presentation.concert.detail

import android.net.Uri
import android.os.Bundle
import android.view.View
import com.google.android.gms.ads.AdView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.BaseActivity
import com.yesferal.hornsapp.app.presentation.concert.ItemParcelable
import com.yesferal.hornsapp.app.util.fadeIn
import com.yesferal.hornsapp.app.util.fadeOut
import com.yesferal.hornsapp.app.util.load
import com.yesferal.hornsapp.app.util.setUpWith
import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.domain.entity.Local
import kotlinx.android.synthetic.main.activity_concert.*
import kotlinx.android.synthetic.main.custom_view_progress_bar.*
import java.net.URI

const val EXTRA_PARAM_PARCELABLE = "EXTRA_PARAM_PARCELABLE"

class ConcertActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_concert)

        val item = intent?.extras?.getParcelable<ItemParcelable>(
            EXTRA_PARAM_PARCELABLE
        )

        if (item == null) {
            finish()
            return
        }

        if (savedInstanceState == null) {
            val concertFragment = ConcertFragment.newInstance(item).apply {
                listener = instanceConcertFragmentListener()
            }
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragmentContainerLayout,
                    concertFragment
                )
                .commit()
        }

        concertImageView.load(item.posterImage)
        favoriteImageView.isChecked = item.isFavorite
        favoriteImageView.setOnCheckedChangeListener { isChecked ->
            item.isFavorite = isChecked
        }

        titleTextView.setUpWith(item.name)
        titleToolbarTextView.setUpWith(item.name)

        closeImageView.setOnClickListener {
            finish()
        }
    }
}

private fun ConcertActivity.instanceConcertFragmentListener() =
    object : ConcertFragment.Listener {
        override fun show(adView: AdView) {
            adContainerLayout.removeAllViews()
            adContainerLayout.addView(adView)
        }

        override fun show(concert: Concert) {
            show(local = concert.local)
            showFacebook(concert.facebookUrl)
            showYoutube(concert.trailerUrl)
        }

        private fun show(local: Local?) {
            local?.let {
                locationImageView.motionVisibility(View.VISIBLE)
                locationImageView.setOnClickListener {
                    val latitude = local.latitude
                    val longitude = local.longitude
                    // TODO("Move to mapper")
                    val uri = URI("geo:${latitude},${longitude}?q=${Uri.encode(local.name)}")

                    startExternalActivity(uri, getString(R.string.maps_package))
                }
            }?: kotlin.run {
                locationImageView.motionVisibility(View.INVISIBLE)
            }
        }

        private fun showFacebook(facebookUrl: URI?) {
            facebookUrl?.let {
                facebookImageView.motionVisibility(View.VISIBLE)
                facebookImageView.setOnClickListener {
                    // TODO ("Move to Mapper")
                    val event = facebookUrl.path.replace("/events", "event")
                    val fbUri = URI("fb://$event")

                    startExternalActivity(fbUri, getString(R.string.facebook_package)) {
                        startExternalActivity(facebookUrl)
                    }
                }
            }?: kotlin.run {
                facebookImageView.motionVisibility(View.GONE)
            }
        }

        private fun showYoutube(youtubeTrailer: URI?) {
            youtubeTrailer?.let {
                trailerImageView.motionVisibility(View.VISIBLE)
                trailerImageView.setOnClickListener {
                    startExternalActivity(youtubeTrailer)
                }
            }?: kotlin.run {
                trailerImageView.motionVisibility(View.GONE)
            }
        }

        /**
         * Adds a new [visibility] to this view.
         * and will update it
         * in the ConstraintSet (R.id.start, R.id.end)
         * of MotionLayout.
         */
        private fun View.motionVisibility(
            visibility: Int
        ) {
            containerLayout.getConstraintSet(R.id.start)
                .setVisibility(id, visibility)
            containerLayout.getConstraintSet(R.id.end)
                .setVisibility(id, visibility)
        }

        override fun showProgress() {
            customProgressBar.fadeIn()
        }

        override fun hideProgress() {
            customProgressBar.fadeOut()
        }
    }