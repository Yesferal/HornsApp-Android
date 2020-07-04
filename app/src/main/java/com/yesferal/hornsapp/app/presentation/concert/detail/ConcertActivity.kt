package com.yesferal.hornsapp.app.presentation.concert.detail

import android.os.Bundle
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.BaseActivity
import com.yesferal.hornsapp.app.presentation.concert.ConcertParcelable
import com.yesferal.hornsapp.app.util.load
import kotlinx.android.synthetic.main.activity_concert.*

const val EXTRA_PARAM_PARCELABLE = "EXTRA_PARAM_PARCELABLE"

class ConcertActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_concert)
        transparentStateBar()

        val concert = intent?.extras?.getParcelable<ConcertParcelable>(
            EXTRA_PARAM_PARCELABLE
        )

        if (concert == null) {
            this.finish()
            return
        }

        concertImageView.load(concert.posterImage)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.contentLayout,
                    ConcertFragment.newInstance(concert)
                )
                .commit()
        }
    }
}
