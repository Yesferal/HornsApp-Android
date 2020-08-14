package com.yesferal.hornsapp.app.presentation.common

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
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

    fun showToast(
        @StringRes id: Int,
        duration: Int = Toast.LENGTH_SHORT
    ) {
        Toast.makeText(
            this,
            getString(id),
            duration
        ).show()
    }

    fun startExternalActivity(
        uri: Uri,
        externalPackage: String,
        onError: () -> Unit = { showToast(R.string.app_not_found) }
    ) {
        val intent = Intent(Intent.ACTION_VIEW,  uri)
        intent.setPackage(externalPackage)
        intent.resolveActivity(packageManager)?.let {
            startActivity(intent)
        }?: kotlin.run { onError() }
    }
}