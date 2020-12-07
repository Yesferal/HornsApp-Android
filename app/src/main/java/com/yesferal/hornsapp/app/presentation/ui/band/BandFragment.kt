package com.yesferal.hornsapp.app.presentation.ui.band

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.BaseFragment
import com.yesferal.hornsapp.app.presentation.common.base.Parcelable
import com.yesferal.hornsapp.app.presentation.ui.concert.detail.EXTRA_PARAM_PARCELABLE
import com.yesferal.hornsapp.app.presentation.common.custom.*
import com.yesferal.hornsapp.domain.entity.Band
import com.yesferal.hornsapp.hada.container.resolve
import kotlinx.android.synthetic.main.custom_error.*
import kotlinx.android.synthetic.main.custom_view_progress_bar.*
import kotlinx.android.synthetic.main.fragment_band.*

class BandFragment
    : BaseFragment<BandViewState>() {

    override val layout: Int
        get() = R.layout.fragment_band

    override val actionListener by lazy {
        container.resolve<BandPresenter>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val item = arguments?.getParcelable<Parcelable.ViewData>(
            EXTRA_PARAM_PARCELABLE
        )

        if (item == null) {
            activity?.finish()
            return
        }

        titleTextView.text = item.name
        membersImageView.setTopCornersRounded()

        actionListener.onViewCreated(item.id)
    }

    override fun render(viewState: BandViewState) {
        viewState.band?.let {
            show(band = it)
        }

        viewState.errorMessageId?.let {
            showError(messageId = it)
        }

        if (viewState.isLoading) {
            showProgress()
        } else {
            hideProgress()
        }
    }

    private fun show(band: Band) {
        membersImageView.load(band.membersImage)
        logoImageView.load(band.logoImage)
        genreTextView.setUpWith(band.genre)
        countryTextView.setUpWith(band.country)
        descriptionTextView.setUpWith(band.description)
    }

    private fun showProgress() {
        customProgressBar.fadeIn()
    }

    private fun hideProgress() {
        customProgressBar.fadeOut()
    }

    private fun showError(@StringRes messageId: Int) {
        stubView.visibility = View.VISIBLE
        errorTextView.text = getString(messageId)
    }

    companion object {
        fun newInstance(
            bundle: Bundle
        ) : BandFragment {
            return BandFragment().apply {
                arguments = bundle
            }
        }
    }
}