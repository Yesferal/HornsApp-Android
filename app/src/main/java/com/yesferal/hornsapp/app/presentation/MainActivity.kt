package com.yesferal.hornsapp.app.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.util.transparentStateBar
import com.yesferal.hornsapp.app.presentation.concert.ConcertsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        transparentStateBar()

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.contentLayout, ConcertsFragment.newInstance())
                .commit()
        }
    }
}