package com.yesferal.hornsapp.app.presentation.ui.concert.newest

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.ParcelableViewData
import com.yesferal.hornsapp.app.presentation.common.base.BaseFragment
import com.yesferal.hornsapp.app.presentation.common.multitype.ViewHolderBinding
import com.yesferal.hornsapp.app.presentation.common.custom.*
import com.yesferal.hornsapp.app.presentation.ui.concert.detail.ConcertActivity
import com.yesferal.hornsapp.app.presentation.ui.concert.detail.EXTRA_PARAM_PARCELABLE
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewData
import com.yesferal.hornsapp.app.presentation.common.multitype.MultiTypeAdapter
import com.yesferal.hornsapp.hada.container.resolve
import kotlinx.android.synthetic.main.fragment_newest.*

class NewestFragment
    : BaseFragment<NewestViewState>() {

    private lateinit var multiTypeAdapter: MultiTypeAdapter

    override val layout: Int
        get() = R.layout.fragment_newest

    override val actionListener by lazy {
        container.resolve<NewestPresenter>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        multiTypeAdapter = MultiTypeAdapter(instanceAdapterListener())

        newestRecyclerView.also {
            it.adapter = multiTypeAdapter
            it.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
            it.addItemDecoration(RecyclerViewVerticalDecorator())
        }

        actionListener.onViewCreated()
    }

    override fun render(viewState: NewestViewState) {
        viewState.items?.let { items ->
            showItems(items)
        }
    }

    private fun showItems(items: List<ViewHolderBinding>) {
        multiTypeAdapter.setItems(items)
    }

    companion object {
        fun newInstance() = NewestFragment()
    }
}

private fun NewestFragment.instanceAdapterListener() =
    object : NewestViewData.Listener,
        UpcomingViewData.Listener {

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