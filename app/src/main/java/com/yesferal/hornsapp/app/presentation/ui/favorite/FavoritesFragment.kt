package com.yesferal.hornsapp.app.presentation.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.ui.BaseFragment
import com.yesferal.hornsapp.app.presentation.common.ui.custom.HornsAdapter
import com.yesferal.hornsapp.app.presentation.common.ui.custom.RecyclerViewVerticalDecorator
import com.yesferal.hornsapp.app.presentation.common.ui.custom.fadeIn
import com.yesferal.hornsapp.app.presentation.common.ui.custom.fadeOut
import com.yesferal.hornsapp.app.presentation.ui.concert.detail.ConcertActivity
import com.yesferal.hornsapp.app.presentation.ui.concert.detail.EXTRA_PARAM_PARCELABLE
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewData
import com.yesferal.hornsapp.hada.container.resolve
import kotlinx.android.synthetic.main.custom_error.*
import kotlinx.android.synthetic.main.custom_view_progress_bar.*
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment
    : BaseFragment<FavoritesViewState>() {

    private lateinit var hornsAdapter: HornsAdapter

    override val actionListener by lazy {
        container.resolve<FavoritesPresenter>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        hornsAdapter = HornsAdapter(instanceAdapterListener())

        concertsRecyclerView.also {
            it.adapter = hornsAdapter
            it.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
            it.addItemDecoration(RecyclerViewVerticalDecorator())
        }
    }

    override fun onResume() {
        super.onResume()
        actionListener.onViewCreated()
    }

    override fun render(viewState: FavoritesViewState) {
        viewState.concerts?.let { concerts ->
            showConcerts(concerts)
        }

        viewState.errorMessage?.let {
            showError(messageId =  viewState.errorMessage)
        }

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

    private fun showConcerts(concerts: List<UpcomingViewData>) {
        hornsAdapter.setItems(concerts)
    }

    private fun showError(
        @StringRes messageId: Int
    ) {
        stubView.visibility = View.VISIBLE
        errorTextView.text = getString(messageId)
    }

    companion object {
        fun newInstance() = FavoritesFragment()
    }
}

interface Listener:
    UpcomingViewData.Listener

private fun FavoritesFragment.instanceAdapterListener() =
    object : Listener {
        override fun onClick(upcomingViewData: UpcomingViewData) {
            val intent = Intent(
                activity,
                ConcertActivity::class.java
            )

            intent.putExtra(
                EXTRA_PARAM_PARCELABLE,
                upcomingViewData.asParcelable()
            )

            startActivity(intent)
        }
    }