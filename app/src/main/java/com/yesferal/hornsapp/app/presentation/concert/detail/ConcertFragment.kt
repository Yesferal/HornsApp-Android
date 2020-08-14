package com.yesferal.hornsapp.app.presentation.concert.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.BaseFragment
import com.yesferal.hornsapp.app.util.setUpWith
import com.yesferal.hornsapp.app.presentation.concert.ConcertParcelable
import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.hada.container.resolve
import kotlinx.android.synthetic.main.fragment_concert.*

class ConcertFragment
    : BaseFragment() {

    override val actionListener by lazy {
        getContainer().resolve<ConcertPresenter>()
    }

    var listener: Listener? = null
    interface Listener {
        fun show(adView: AdView)
        fun show(concert: Concert)
        fun showProgress()
        fun hideProgress()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_concert, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val concert = arguments?.getParcelable<ConcertParcelable>(
            EXTRA_PARAM_PARCELABLE
        )

        if (concert == null) {
            activity?.finish()
            return
        }

        actionListener.onViewCreated(concert.id)
    }

    fun show(concert: Concert) {
        listener?.show(concert)
        //TODO("Implement new view")
        descriptionTextView.setUpWith(concert.description)
        descriptionTextView.setUpWith(
            concert.bands.toString()
                +concert.bands.toString()
                +concert.bands.toString()
                +concert.bands.toString()
                +concert.bands.toString()
                +concert.bands.toString()
                +concert.bands.toString()
                +concert.bands.toString()
                +concert.bands.toString()
                +concert.bands.toString()
                +concert.bands.toString()
                +concert.bands.toString()
        )
    }

    fun show(adView: AdView) {
        listener?.show(adView)
    }

    fun showProgress() {
        listener?.showProgress()
    }

    fun hideProgress() {
        listener?.hideProgress()
    }

    companion object {
        fun newInstance(
            concert: ConcertParcelable
        ) : ConcertFragment {
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_PARAM_PARCELABLE, concert)

            return ConcertFragment().apply {
                arguments = bundle
            }
        }
    }
}