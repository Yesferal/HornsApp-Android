package com.yesferal.hornsapp.app.presentation.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.BaseFragment
import com.yesferal.hornsapp.app.presentation.common.custom.*
import com.yesferal.hornsapp.app.presentation.common.multitype.MultiTypeAdapter
import com.yesferal.hornsapp.app.presentation.common.multitype.ViewHolderBinding
import com.yesferal.hornsapp.app.presentation.ui.concert.detail.ConcertActivity
import com.yesferal.hornsapp.app.presentation.ui.concert.detail.EXTRA_PARAM_PARCELABLE
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewData
import com.yesferal.hornsapp.hada.container.resolve
import kotlinx.android.synthetic.main.custom_view_progress_bar.*
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment
    : BaseFragment<FavoritesViewState>() {

    private lateinit var multiTypeAdapter: MultiTypeAdapter

    override val actionListener by lazy {
        container.resolve<FavoritesPresenter>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        multiTypeAdapter = MultiTypeAdapter(instanceAdapterListener())

        concertsRecyclerView.also {
            it.adapter = multiTypeAdapter
            it.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
            it.addItemDecoration(RecyclerViewVerticalDecorator())
        }
    }

    override fun onResume() {
        super.onResume()
        actionListener.onViewCreated()
    }

    override fun render(viewState: FavoritesViewState) {
        viewState.items?.let { items ->
            showItems(items)
        }

        if (viewState.isLoading) {
            showProgress()
        } else {
            hideProgress()
        }
    }

    private fun showProgress() {
        customProgressBar.fadeIn()
    }

    private fun hideProgress() {
        customProgressBar.fadeOut()
    }

    private fun showItems(items: List<ViewHolderBinding>) {
        multiTypeAdapter.setItems(items)
    }

    companion object {
        fun newInstance() = FavoritesFragment()
    }
}

interface Listener:
    UpcomingViewData.Listener

private fun FavoritesFragment.instanceAdapterListener() =
    object : Listener {
        override fun onClick(upcomingViewData: UpcomingViewData) {
            val intent = Intent(
                activity,
                ConcertActivity::class.java
            )

            intent.putExtra(
                EXTRA_PARAM_PARCELABLE,
                upcomingViewData.asParcelable()
            )

            startActivity(intent)
        }
    }