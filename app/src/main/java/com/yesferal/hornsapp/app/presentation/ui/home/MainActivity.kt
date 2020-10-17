package com.yesferal.hornsapp.app.presentation.ui.home

import android.os.Bundle
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.BaseActivity
import com.yesferal.hornsapp.app.presentation.ui.setting.SettingBottomSheetFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val concertsFragment = HomeFragment.newInstance()
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