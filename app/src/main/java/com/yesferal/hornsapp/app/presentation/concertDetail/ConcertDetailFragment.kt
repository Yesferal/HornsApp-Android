package com.yesferal.hornsapp.app.presentation.concertDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.BaseFragment
import com.yesferal.hornsapp.app.presentation.common.extension.setUpWith
import com.yesferal.hornsapp.app.presentation.concerts.model.ConcertParcelable
import kotlinx.android.synthetic.main.fragment_concert_detail.*

class ConcertDetailFragment
    : BaseFragment() {
    // TODO: Instance presenter by container
    override val actionListener = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_concert_detail, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val concert = arguments?.getParcelable<ConcertParcelable>(EXTRA_PARAM_PARCELABLE)
        titleTextView.setUpWith(concert?.name)
        descriptionTextView.setUpWith(concert?.description)
        timeTextView.setUpWith(concert?.time)
        dateTextView.setUpWith(concert?.date)
    }

    companion object {
        fun newInstance(
            concert: ConcertParcelable
        ) : ConcertDetailFragment {
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_PARAM_PARCELABLE, concert)

            return ConcertDetailFragment().apply {
                arguments = bundle
            }
        }
    }
}