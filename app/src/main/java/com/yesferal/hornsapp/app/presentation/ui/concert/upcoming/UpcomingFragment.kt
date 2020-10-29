package com.yesferal.hornsapp.app.presentation.ui.concert.upcoming

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.BaseFragment
import com.yesferal.hornsapp.app.presentation.common.custom.*
import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.hada.container.resolve
import kotlinx.android.synthetic.main.custom_date_text_view.*
import kotlinx.android.synthetic.main.custom_error.*
import kotlinx.android.synthetic.main.custom_view_progress_bar.*
import kotlinx.android.synthetic.main.fragment_upcoming.*
import kotlinx.android.synthetic.main.item_concert.*

class UpcomingFragment
    : BaseFragment<UpcomingViewState>() {

    override val actionListener by lazy {
        container.resolve<UpcomingPresenter>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_upcoming, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val handler = Handler()
        handler.postDelayed({
            actionListener.onViewCreated()
        }, 333)
    }

    override fun render(viewState: UpcomingViewState) {
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

    private fun showConcerts(concerts: List<Concert>) {
        val concert = concerts[1]
        concertImageView.load(concert.headlinerImage)
        concertImageView.setAllCornersRounded()

        titleTextView.setUpWith(concert.name)
        timeTextView.setUpWith(concert.time)
        genreTextView.setUpWith(concert.genre)

        dayTextView.setUpWith(concert.day)
        monthTextView.setUpWith(concert.month)

        ticketTextView.apply {
            setImageView(R.drawable.ic_ticket)
            setText(getString(R.string.available_in))
        }
        buyTicketsTextView.setUpWith(concert.ticketingHost)
    }

    private fun showError(
        @StringRes messageId: Int
    ) {
        stubView.visibility = View.VISIBLE
        errorTextView.text = getString(messageId)
    }

    companion object {
        fun newInstance() = UpcomingFragment()
    }
}