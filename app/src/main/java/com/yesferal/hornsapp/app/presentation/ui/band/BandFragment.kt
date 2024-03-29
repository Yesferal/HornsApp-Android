package com.yesferal.hornsapp.app.presentation.ui.band

import android.os.Bundle
import android.view.View
import android.view.ViewStub
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import com.google.android.material.imageview.ShapeableImageView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.framework.adMob.AdUnitIds
import com.yesferal.hornsapp.app.framework.adMob.BusinessModelFactoryProducer
import com.yesferal.hornsapp.app.presentation.common.base.ParcelableViewData
import com.yesferal.hornsapp.app.presentation.common.extension.addBottomView
import com.yesferal.hornsapp.app.presentation.common.extension.fadeIn
import com.yesferal.hornsapp.app.presentation.common.extension.fadeOut
import com.yesferal.hornsapp.app.presentation.common.extension.load
import com.yesferal.hornsapp.app.presentation.common.extension.setTopCornersRounded
import com.yesferal.hornsapp.app.presentation.common.extension.setUpWith
import com.yesferal.hornsapp.app.presentation.common.render.RenderFragment
import com.yesferal.hornsapp.app.presentation.ui.concert.detail.EXTRA_PARAM_PARCELABLE
import com.yesferal.hornsapp.core.domain.entity.Band
import com.yesferal.hornsapp.hadi_android.getViewModel
import com.yesferal.hornsapp.hadi_android.hadi

class BandFragment : RenderFragment<BandViewState>() {

    override val layout = R.layout.fragment_band

    private lateinit var bandViewModel: BandViewModel
    private lateinit var titleTextView: TextView
    private lateinit var membersImageView: ShapeableImageView
    private lateinit var logoImageView: ImageView
    private lateinit var genreTextView: TextView
    private lateinit var countryTextView: TextView
    private lateinit var firstAdLayout: FrameLayout
    private lateinit var descriptionTextView: TextView
    private lateinit var customProgressBar: View
    private lateinit var stubView: ViewStub

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val item = arguments?.getParcelable<ParcelableViewData>(
            EXTRA_PARAM_PARCELABLE
        )

        if (item == null) {
            activity?.onBackPressed()
            return
        }

        titleTextView = view.findViewById(R.id.titleTextView)
        membersImageView = view.findViewById(R.id.membersImageView)
        logoImageView = view.findViewById(R.id.logoImageView)
        genreTextView = view.findViewById(R.id.genreTextView)
        countryTextView = view.findViewById(R.id.countryTextView)
        firstAdLayout = view.findViewById(R.id.firstAdLayout)
        descriptionTextView = view.findViewById(R.id.descriptionTextView)
        customProgressBar = view.findViewById(R.id.customProgressBar)
        stubView = view.findViewById(R.id.stubView)

        titleTextView.text = item.name
        membersImageView.setTopCornersRounded(dp = 32)

        bandViewModel = getViewModel<BandViewModel, BandViewModelFactory>(param = item.id)

        bandViewModel.state.observe(viewLifecycleOwner) {
            render(it)
        }

        val abstractViewFactory = hadi().resolve<BusinessModelFactoryProducer>().getViewFactory()
        firstAdLayout.addBottomView(abstractViewFactory, AdUnitIds.Type.FIRST_BAND_DETAIL, 50)
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
        membersImageView.visibility = View.GONE
        logoImageView.visibility = View.GONE
        stubView.visibility = View.VISIBLE
        view?.findViewById<TextView>(R.id.errorTextView)?.text = getString(messageId)
    }

    companion object {
        fun newInstance(
            bundle: Bundle
        ): BandFragment {
            return BandFragment().apply {
                arguments = bundle
            }
        }
    }
}