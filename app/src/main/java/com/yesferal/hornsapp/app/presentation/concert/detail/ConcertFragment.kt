package com.yesferal.hornsapp.app.presentation.concert.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        titleTextView.setUpWith(concert.name)
        descriptionTextView.setUpWith(concert.description)
        dateTextView.setUpWith(concert.date)
    }

    fun show(concert: Concert) {
        //TODO("Implement new view")
        descriptionTextView.setUpWith(concert.bands.toString())
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