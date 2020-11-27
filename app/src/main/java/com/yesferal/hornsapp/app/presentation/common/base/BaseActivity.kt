package com.yesferal.hornsapp.app.presentation.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yesferal.hornsapp.app.R

abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBackgroundColor()
    }

    private fun setBackgroundColor() {
        window.decorView.setBackgroundColor(getColor(R.color.background))
    }
}