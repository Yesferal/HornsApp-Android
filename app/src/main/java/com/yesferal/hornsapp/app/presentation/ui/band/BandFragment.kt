package com.yesferal.hornsapp.app.presentation.ui.band

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.BaseFragment
import com.yesferal.hornsapp.app.presentation.common.base.ParcelableViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.detail.EXTRA_PARAM_PARCELABLE
import com.yesferal.hornsapp.app.presentation.common.extension.*
import com.yesferal.hornsapp.domain.entity.Band
import com.yesferal.hornsapp.hada.parameter.Parameters
import kotlinx.android.synthetic.main.custom_error.*
import kotlinx.android.synthetic.main.custom_view_progress_bar.*
import kotlinx.android.synthetic.main.fragment_band.*

class BandFragment
    : BaseFragment<BandViewState>() {

    override val layout: Int
        get() = R.layout.fragment_band

    private lateinit var bandViewModel: BandViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val item = arguments?.getParcelable<ParcelableViewData>(
            EXTRA_PARAM_PARCELABLE
        )

        if (item == null) {
            activity?.onBackPressed()
            return
        }

        titleTextView.text = item.name
        membersImageView.setTopCornersRounded()

        bandViewModel = ViewModelProvider(
            this,
            hada().resolve<BandViewModelFactory>(params = Parameters(item.id))
        ).get(BandViewModel::class.java)

        bandViewModel.state.observe(viewLifecycleOwner) {
            render(it)
        }
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