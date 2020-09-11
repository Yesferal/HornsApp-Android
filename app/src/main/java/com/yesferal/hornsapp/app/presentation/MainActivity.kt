package com.yesferal.hornsapp.app.presentation

import android.os.Bundle
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.categories.CategoriesFragment
import com.yesferal.hornsapp.app.presentation.common.BaseActivity
import com.yesferal.hornsapp.app.presentation.concert.ConcertsFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.concertsLayout, ConcertsFragment.newInstance())
                .commit()

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.categoriesLayout, CategoriesFragment.newInstance())
                .commit()
        }
    }
}