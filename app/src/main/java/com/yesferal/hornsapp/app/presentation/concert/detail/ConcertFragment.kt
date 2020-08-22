package com.yesferal.hornsapp.app.presentation.concert.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.BaseFragment
import com.yesferal.hornsapp.app.presentation.item.adapter.ItemAdapter
import com.yesferal.hornsapp.app.presentation.item.adapter.HornsAppItem
import com.yesferal.hornsapp.app.presentation.item.adapter.mapToBaseItem
import com.yesferal.hornsapp.app.util.setUpWith
import com.yesferal.hornsapp.app.presentation.concert.ItemParcelable
import com.yesferal.hornsapp.app.util.RecyclerViewDecorator
import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.hada.container.resolve
import kotlinx.android.synthetic.main.fragment_concert.*

class ConcertFragment
    : BaseFragment() {

    private lateinit var bandAdapter: ItemAdapter

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

        val item = arguments?.getParcelable<ItemParcelable>(
            EXTRA_PARAM_PARCELABLE
        )

        if (item == null) {
            activity?.finish()
            return
        }

        bandAdapter = ItemAdapter(instanceBaseAdapterListener())
        bandRecyclerView.also {
            it.adapter = bandAdapter
            it.layoutManager = linearLayoutManager
            it.addItemDecoration(RecyclerViewDecorator(padding = 8))
        }

        actionListener.onViewCreated(item.id)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun show(concert: Concert) {
        listener?.show(concert)

        descriptionTextView.setUpWith(concert.description)

        localTextView.apply {
            setImageView(R.drawable.ic_map)
            setText(concert.local?.name)
        }

        datetimeTextView.apply {
            setImageView(R.drawable.ic_calendar)
            setText(concert.datetime)
        }

        genreTextView.apply {
            setImageView(R.drawable.ic_hornsapp)
            setText(getString(R.string.default_genre))
        }

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
            item: ItemParcelable
        ) : ConcertFragment {
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_PARAM_PARCELABLE, item)

            return ConcertFragment().apply {
                arguments = bundle
            }
        }
    }
}

private fun ConcertFragment.instanceBaseAdapterListener() =
    object : ItemAdapter.Listener {
        override fun onClick(item: HornsAppItem) {
            showToast(R.string.app_name)
        }
    }