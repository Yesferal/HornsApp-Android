package com.yesferal.hornsapp.app.presentation.ui.concert.newest

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.ParcelableViewData
import com.yesferal.hornsapp.app.presentation.common.base.BaseFragment
import com.yesferal.hornsapp.app.presentation.common.multitype.ViewHolderData
import com.yesferal.hornsapp.app.presentation.common.custom.*
import com.yesferal.hornsapp.app.presentation.ui.concert.detail.ConcertActivity
import com.yesferal.hornsapp.app.presentation.ui.concert.detail.EXTRA_PARAM_PARCELABLE
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewData
import com.yesferal.hornsapp.app.presentation.common.multitype.HornsAdapter
import com.yesferal.hornsapp.hada.container.resolve
import kotlinx.android.synthetic.main.custom_error.*
import kotlinx.android.synthetic.main.custom_view_progress_bar.*
import kotlinx.android.synthetic.main.fragment_newest.*
import kotlinx.android.synthetic.main.fragment_newest.stubView

class NewestFragment
    : BaseFragment<NewestViewState>() {

    private lateinit var hornsAdapter: HornsAdapter

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

        hornsAdapter = HornsAdapter(instanceAdapterListener())

        newestRecyclerView.also {
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

    private fun showConcerts(concerts: List<ViewHolderData>) {
        hornsAdapter.setItems(concerts)
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

interface Listener:
    NewestViewData.Listener,
    UpcomingViewData.Listener

private fun NewestFragment.instanceAdapterListener() =
    object : Listener {
        override fun onClick(upcomingViewData: UpcomingViewData) {
            startConcertActivity(upcomingViewData.asParcelable())
        }

        override fun onClick(newestViewData: NewestViewData) {
            startConcertActivity(newestViewData.asParcelable())
        }

        private fun startConcertActivity(parcelableViewData: ParcelableViewData) {
            val intent = Intent(
                activity,
                ConcertActivity::class.java
            )

            intent.putExtra(
                EXTRA_PARAM_PARCELABLE,
                parcelableViewData
            )

            startActivity(intent)
        }
    }