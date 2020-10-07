package com.yesferal.hornsapp.app.presentation

import android.os.Bundle
import com.google.android.gms.ads.AdView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.BaseActivity
import com.yesferal.hornsapp.app.presentation.concert.ConcertsFragment
import com.yesferal.hornsapp.app.presentation.setting.SettingBottomSheetFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val concertsFragment = ConcertsFragment.newInstance().apply {
                listener = instanceConcertsFragmentListener()
            }
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.concertsLayout, concertsFragment)
                .commit()
        }

        menuImageView.setOnClickListener {
            supportFragmentManager.let {
                SettingBottomSheetFragment.newInstance(Bundle()).apply {
                    show(it, tag)
                }
            }
        }
    }
}

private fun MainActivity.instanceConcertsFragmentListener() =
    object : ConcertsFragment.Listener {
        override fun show(adView: AdView) {
            adContainerLayout.removeAllViews()
            adContainerLayout.addView(adView)
        }
    }