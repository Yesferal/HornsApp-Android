package com.yesferal.hornsapp.app.presentation.common

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.HornsApp
import java.net.URI

abstract class BaseFragment
    : Fragment(),
    BaseContract.View {

    abstract val actionListener: BaseContract.ActionListener?

    protected val container by lazy {
        (activity?.application as HornsApp).container
    }

    protected val linearLayoutManager by lazy {
        LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    protected val linearLayoutManagerVertical by lazy {
        LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        actionListener?.attach(view = this)
    }

    override fun onDestroyView() {
        actionListener?.detachView()
        super.onDestroyView()
    }

    fun showToast(
        @StringRes id: Int,
        duration: Int = Toast.LENGTH_SHORT
    ) {
        Toast.makeText(
            context,
            getString(id),
            duration
        ).show()
    }

    fun startExternalActivity(
        uri: URI?,
        externalPackage: String,
        onError: () -> Unit = { showToast(R.string.error_app_not_found) }
    ) {
        if (uri == null) return

        val androidUri = Uri.parse(uri.toString())
        val intent = Intent(Intent.ACTION_VIEW,  androidUri)

        intent.setPackage(externalPackage)
        activity?.let {
            intent.resolveActivity(it.packageManager)?.let {
                startActivity(intent)
            } ?: kotlin.run { onError() }
        }
    }

    fun startExternalActivity(uri: URI?) {
        if (uri == null) return

        val androidUri = Uri.parse(uri.toString())
        startActivity(Intent(Intent.ACTION_VIEW, androidUri))
    }
}