package com.yesferal.hornsapp.app.presentation.ui.concert.upcoming

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.ui.BaseFragment
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.adapter.UpcomingAdapter
import com.yesferal.hornsapp.app.presentation.ui.concert.detail.ConcertActivity
import com.yesferal.hornsapp.app.presentation.ui.concert.detail.EXTRA_PARAM_PARCELABLE
import com.yesferal.hornsapp.app.presentation.common.ui.custom.*
import com.yesferal.hornsapp.app.presentation.ui.filters.CategoryViewData
import com.yesferal.hornsapp.hada.container.resolve
import kotlinx.android.synthetic.main.custom_error.*
import kotlinx.android.synthetic.main.custom_view_progress_bar.*
import kotlinx.android.synthetic.main.fragment_concerts.*
import kotlinx.android.synthetic.main.fragment_concerts.stubView

class ConcertsFragment
    : BaseFragment<UpcomingViewState>() {

    private lateinit var concertAdapter: UpcomingAdapter

    override val actionListener by lazy {
        container.resolve<UpcomingPresenter>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_concerts, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        concertAdapter = UpcomingAdapter(instanceConcertAdapterListener())

        concertsRecyclerView.also {
            it.adapter = concertAdapter
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
        viewState.categories?.let {
            showCategories(it)
        }

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

    private fun showCategories(filtersViewData: FiltersViewData) {
        concertAdapter.setCategories(filtersViewData)
    }

    private fun showConcerts(concerts: List<ConcertViewData>) {
        concertAdapter.setConcerts(concerts)
        concertsRecyclerView.scrollToPosition(1)
    }

    private fun showError(
        @StringRes messageId: Int
    ) {
        stubView.visibility = View.VISIBLE
        errorTextView.text = getString(messageId)
    }

    companion object {
        fun newInstance() = ConcertsFragment()
    }
}

private fun ConcertsFragment.instanceConcertAdapterListener() =
    object : UpcomingAdapter.Listener {

        override fun onClick(concertViewData: ConcertViewData) {
            val intent = Intent(
                activity,
                ConcertActivity::class.java
            )

            intent.putExtra(
                EXTRA_PARAM_PARCELABLE,
                concertViewData.asParcelable()
            )

            startActivity(intent)
        }

        override fun onClick(categoryViewData: CategoryViewData) {
            actionListener.onFilterClick(categoryViewData)
        }
    }