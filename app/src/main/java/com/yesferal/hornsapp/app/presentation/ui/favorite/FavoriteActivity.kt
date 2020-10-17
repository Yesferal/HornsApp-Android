package com.yesferal.hornsapp.app.presentation.ui.favorite

import android.os.Bundle
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.BaseActivity
import kotlinx.android.synthetic.main.activity_concert.*

class FavoriteActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragmentContainerLayout,
                    FavoritesFragment.newInstance()
                )
                .commit()
        }

        closeImageView.setOnClickListener {
            finish()
        }
    }
}