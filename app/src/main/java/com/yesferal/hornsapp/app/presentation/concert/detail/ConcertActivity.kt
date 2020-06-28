package com.yesferal.hornsapp.app.presentation.concert.detail

import android.os.Bundle
import com.yesferal.hornsapp.app.R
import androidx.appcompat.app.AppCompatActivity
import com.yesferal.hornsapp.app.util.transparentStateBar
import com.yesferal.hornsapp.app.presentation.concert.ConcertParcelable
import com.yesferal.hornsapp.app.util.load
import kotlinx.android.synthetic.main.activity_concert.*

const val EXTRA_PARAM_PARCELABLE = "EXTRA_PARAM_PARCELABLE"

class ConcertActivity : AppCompatActivity() {

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
