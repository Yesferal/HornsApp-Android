package com.yesferal.hornsapp.app.presentation.common.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.HornsApp
import com.yesferal.hornsapp.app.presentation.common.RenderState
import com.yesferal.hornsapp.app.presentation.common.ViewState
import java.net.URI

abstract class BaseFragment<VIEW_STATE: ViewState>
    : Fragment(),
    RenderState<VIEW_STATE>,
    BaseContract.View {

    abstract val actionListener: BaseContract.ActionListener?

    protected val container by lazy {
        (activity?.application as HornsApp).container
    }

    abstract override fun render(viewState: VIEW_STATE)

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

    protected fun showToast(
        @StringRes id: Int,
        duration: Int = Toast.LENGTH_SHORT
    ) {
        Toast.makeText(
            context,
            getString(id),
            duration
        ).show()
    }

    protected fun startExternalActivity(
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

    protected fun startExternalActivity(uri: URI?) {
        if (uri == null) return

        val androidUri = Uri.parse(uri.toString())
        startActivity(Intent(Intent.ACTION_VIEW, androidUri))
    }
}