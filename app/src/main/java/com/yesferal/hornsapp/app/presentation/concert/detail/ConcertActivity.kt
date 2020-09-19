package com.yesferal.hornsapp.app.presentation.concert.detail

import android.os.Bundle
import androidx.constraintlayout.motion.widget.MotionLayout
import com.google.android.gms.ads.AdView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.BaseActivity
import com.yesferal.hornsapp.app.presentation.common.ItemParcelable
import com.yesferal.hornsapp.app.util.*
import kotlinx.android.synthetic.main.activity_concert.*

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

        titleTextView.setUpWith(item.name)
        concertImageView.load(item.posterImage)
        concertImageView.setAllCornersRounded()

        closeImageView.setOnClickListener {
            finish()
        }

        containerLayout.addTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}

            override fun onTransitionCompleted(p0: MotionLayout?, transitionId: Int) {
                val startId = R.id.start
                if (transitionId == startId) {
                    nestedScrollView.smoothScrollTo(0, 0)
                }
            }
        })
    }
}

private fun ConcertActivity.instanceConcertFragmentListener() =
    object : ConcertFragment.Listener {
        override fun show(adView: AdView) {
            adContainerLayout.removeAllViews()
            adContainerLayout.addView(adView)
        }
    }