package com.yesferal.hornsapp.app.presentation.concert.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.BaseFragment
import com.yesferal.hornsapp.app.presentation.common.adapter.BaseAdapter
import com.yesferal.hornsapp.app.presentation.common.adapter.mapToBaseItem
import com.yesferal.hornsapp.app.util.setUpWith
import com.yesferal.hornsapp.app.presentation.concert.ConcertParcelable
import com.yesferal.hornsapp.app.util.RecyclerViewDecorator
import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.hada.container.resolve
import kotlinx.android.synthetic.main.fragment_concert.*

class ConcertFragment
    : BaseFragment() {

    private lateinit var bandAdapter: BaseAdapter

    override val actionListener by lazy {
        container.resolve<ConcertPresenter>()
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

        bandAdapter = BaseAdapter(instanceBaseAdapterListener())
        bandRecyclerView.also {
            it.adapter = bandAdapter
            it.layoutManager = linearLayoutManager
            it.addItemDecoration(RecyclerViewDecorator(padding = 8))
        }

        actionListener.onViewCreated(concert.id)
    }

    fun show(concert: Concert) {
        listener?.show(concert)

        descriptionTextView.setUpWith(concert.description)
        localTextView.setUpWith(concert.local?.name)
        timeTextView.setUpWith(concert.datetime)

        val items = concert.bands?.map {
            it.mapToBaseItem()
        }
        bandAdapter.setItem(items)
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

private fun ConcertFragment.instanceBaseAdapterListener() =
    object : BaseAdapter.Listener {
        override fun onClick(id: String) {
            showToast(R.string.app_name)
        }
    }