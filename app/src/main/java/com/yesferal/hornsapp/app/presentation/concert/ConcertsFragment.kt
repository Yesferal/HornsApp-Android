package com.yesferal.hornsapp.app.presentation.concert

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import com.google.android.gms.ads.AdView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.BaseFragment
import com.yesferal.hornsapp.app.presentation.concert.adapter.ConcertAdapter
import com.yesferal.hornsapp.app.presentation.concert.detail.ConcertActivity
import com.yesferal.hornsapp.app.presentation.concert.detail.EXTRA_PARAM_PARCELABLE
import com.yesferal.hornsapp.app.presentation.common.asParcelable
import com.yesferal.hornsapp.app.util.*
import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.hada.container.resolve
import kotlinx.android.synthetic.main.custom_error.*
import kotlinx.android.synthetic.main.custom_view_progress_bar.*
import kotlinx.android.synthetic.main.fragment_concerts.*

class ConcertsFragment
    : BaseFragment() {

    private lateinit var concertAdapter: ConcertAdapter
    private lateinit var stubViewInflated: View

    override val actionListener by lazy {
        container.resolve<ConcertsPresenter>()
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

        concertAdapter = ConcertAdapter(instanceConcertAdapterListener())

        concertsRecyclerView.also {
            it.adapter = concertAdapter
            it.layoutManager = linearLayoutManagerVertical
            it.addItemDecoration(RecyclerViewVerticalDecorator())
        }

        actionListener.onViewCreated()
    }

    fun showProgress() {
        customProgressBar.fadeIn()
    }

    fun hideProgress() {
        customProgressBar.fadeOut()
    }

    fun show(concerts: List<Concert>) {
        concertAdapter.setItem(concerts)
    }

    fun show(adView: AdView) {
        adContainerLayout.removeAllViews()
        adContainerLayout.addView(adView)
    }

    fun showError(@StringRes messageId: Int) {
        if (!::stubViewInflated.isInitialized) {
            stubViewInflated = stubView.inflate()
        }
        stubViewInflated.visibility = View.VISIBLE
        errorTextView.text = getString(messageId)
        tryAgainTextView.visibility = View.VISIBLE
        tryAgainTextView.setOnClickListener {
            actionListener.onRefresh()
            tryAgainTextView.visibility = View.GONE
        }
    }

    fun hideError() {
        if (::stubViewInflated.isInitialized) {
            stubViewInflated.visibility = View.GONE
        }
    }

    companion object {
        fun newInstance() = ConcertsFragment()
    }
}

private fun ConcertsFragment.instanceConcertAdapterListener() =
    object : ConcertAdapter.Listener {
        override fun onConcertItemClick(concert: Concert) {
            activity?.let {
                val intent = Intent(
                    it,
                    ConcertActivity::class.java
                )

                intent.putExtra(
                    EXTRA_PARAM_PARCELABLE,
                    concert.asParcelable()
                )

                startActivity(intent)
            }
        }
    }