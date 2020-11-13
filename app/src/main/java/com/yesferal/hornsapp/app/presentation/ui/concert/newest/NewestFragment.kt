package com.yesferal.hornsapp.app.presentation.ui.concert.newest

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.BaseFragment
import com.yesferal.hornsapp.app.presentation.common.ViewData
import com.yesferal.hornsapp.app.presentation.common.custom.*
import com.yesferal.hornsapp.app.presentation.common.entity.asParcelable
import com.yesferal.hornsapp.app.presentation.ui.concert.detail.ConcertActivity
import com.yesferal.hornsapp.app.presentation.ui.concert.detail.EXTRA_PARAM_PARCELABLE
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.adapter.NewestAdapter
import com.yesferal.hornsapp.app.presentation.ui.concert.search.ConcertViewData
import com.yesferal.hornsapp.hada.container.resolve
import kotlinx.android.synthetic.main.custom_error.*
import kotlinx.android.synthetic.main.custom_view_progress_bar.*
import kotlinx.android.synthetic.main.fragment_newest.*
import kotlinx.android.synthetic.main.fragment_newest.stubView

class NewestFragment
    : BaseFragment<NewestViewState>() {

    private lateinit var newestAdapter: NewestAdapter

    override val actionListener by lazy {
        container.resolve<NewestPresenter>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_newest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newestAdapter = NewestAdapter(instanceNewestAdapterListener())

        newestRecyclerView.also {
            it.adapter = newestAdapter
            it.layoutManager = linearLayoutManagerVertical
            it.addItemDecoration(RecyclerViewVerticalDecorator())
        }

        val handler = Handler()
        handler.postDelayed({
            actionListener.onViewCreated()
        }, 333)
    }

    override fun render(viewState: NewestViewState) {
        viewState.concerts?.let { concerts ->
            showConcerts(concerts)
        }

        viewState.errorMessage?.let {
            showError(messageId =  viewState.errorMessage)
        }

        if (viewState.isLoading) {
            customProgressBar.fadeIn()

        } else {
            customProgressBar.fadeOut()
        }
    }

    private fun showConcerts(concerts: List<ViewData>) {
        newestAdapter.setItem(concerts)
    }

    private fun showError(
        @StringRes messageId: Int
    ) {
        stubView.visibility = View.VISIBLE
        errorTextView.text = getString(messageId)
    }

    companion object {
        fun newInstance() = NewestFragment()
    }
}

private fun NewestFragment.instanceNewestAdapterListener() =
    object : NewestAdapter.Listener {
        override fun onClick(concertViewData: ConcertViewData) {
            startConcertActivity(concertViewData)
        }

        override fun onClick(newestViewData: NewestViewData) {
            startConcertActivity(newestViewData)
        }

        private fun startConcertActivity(viewData: ViewData) {
            val intent = Intent(
                activity,
                ConcertActivity::class.java
            )

            intent.putExtra(
                EXTRA_PARAM_PARCELABLE,
                viewData.asParcelable()
            )

            startActivity(intent)
        }
    }