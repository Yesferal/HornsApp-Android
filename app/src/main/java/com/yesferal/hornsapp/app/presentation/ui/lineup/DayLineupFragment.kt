/* Copyright © 2025 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.lineup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.framework.adMob.AdUnitIds
import com.yesferal.hornsapp.app.framework.adMob.BusinessModelFactoryProducer
import com.yesferal.hornsapp.app.presentation.common.extension.addBottomView
import com.yesferal.hornsapp.app.presentation.common.extension.fadeIn
import com.yesferal.hornsapp.app.presentation.common.extension.fadeOut
import com.yesferal.hornsapp.app.presentation.common.extension.setUpWith
import com.yesferal.hornsapp.app.presentation.common.render.RenderFragment
import com.yesferal.hornsapp.app.presentation.ui.home.HomeViewState
import com.yesferal.hornsapp.hadi_android.getViewModel
import com.yesferal.hornsapp.hadi_android.hadi

class DayLineupFragment: RenderFragment<HomeViewState>() {
    override val layout = R.layout.fragment_day_lineup_beta

    private lateinit var viewModel: LineupViewModel
    private val args: DayLineupFragmentArgs by navArgs()

    private lateinit var customProgressBar: View
    private lateinit var closeImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var firstAdLayout: FrameLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val stageLineupFragment = StageLineupFragment.newInstance()

        val transaction = getChildFragmentManager().beginTransaction()

        transaction.replace(R.id.stageLineupFragment, stageLineupFragment)
            .commit()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lineup = args.lineup
        if (lineup?.id == null) {
            activity?.onBackPressedDispatcher?.onBackPressed()
            return
        }
        customProgressBar = view.findViewById(R.id.customProgressBar)
        titleTextView = view.findViewById(R.id.titleTextView)
        closeImageView = view.findViewById(R.id.closeImageView)
        closeImageView.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        viewModel = getViewModel<LineupViewModel, LineupViewModelFactory>(param = lineup.id)
        viewModel.state.observe(viewLifecycleOwner) {
            titleTextView.setUpWith(it.day)
            render(HomeViewState())
        }

        firstAdLayout = view.findViewById(R.id.firstAdLayout)
        val abstractViewFactory = hadi().resolve<BusinessModelFactoryProducer>().getViewFactory()
        firstAdLayout.addBottomView(abstractViewFactory, AdUnitIds.Type.FIRST_CONCERT_DETAIL, 50)

    }

    override fun render(viewState: HomeViewState) {
        if (viewState.isLoading) {
            showProgress()
        } else {
            hideProgress()
        }
    }

    private fun showProgress() {
        customProgressBar.fadeIn()
    }

    private fun hideProgress() {
        customProgressBar.fadeOut()
    }


    companion object {
        fun newInstance() = DayLineupFragment()
    }
}
