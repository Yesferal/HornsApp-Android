package com.yesferal.hornsapp.app.presentation.ui.concert.upcoming

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.ui.multitype.ViewHolderData
import com.yesferal.hornsapp.app.presentation.common.ui.BaseFragment
import com.yesferal.hornsapp.app.presentation.common.ui.multitype.HornsAdapter
import com.yesferal.hornsapp.app.presentation.ui.concert.detail.ConcertActivity
import com.yesferal.hornsapp.app.presentation.ui.concert.detail.EXTRA_PARAM_PARCELABLE
import com.yesferal.hornsapp.app.presentation.common.ui.custom.*
import com.yesferal.hornsapp.app.presentation.ui.filters.CategoryViewData
import com.yesferal.hornsapp.hada.container.resolve
import kotlinx.android.synthetic.main.custom_view_progress_bar.*
import kotlinx.android.synthetic.main.fragment_upcoming.*

class ConcertsFragment
    : BaseFragment<UpcomingViewState>() {

    private lateinit var hornsAdapter: HornsAdapter

    override val actionListener by lazy {
        container.resolve<UpcomingPresenter>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_upcoming, container, false)
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

        val handler = Handler()
        handler.postDelayed({
            actionListener.onViewCreated()
        }, 333)
    }

    override fun render(viewState: UpcomingViewState) {

        viewState.items?.let { concerts ->
            showConcerts(concerts)
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

    private fun showConcerts(
        concerts: List<ViewHolderData>
    ) {
        hornsAdapter.setItems(concerts)
    }

    companion object {
        fun newInstance() = ConcertsFragment()
    }
}

interface Listener:
    FiltersViewData.Listener,
    UpcomingViewData.Listener

private fun ConcertsFragment.instanceAdapterListener() =
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

        override fun onClick(categoryViewData: CategoryViewData) {
            actionListener.onFilterClick(categoryViewData)
        }
    }