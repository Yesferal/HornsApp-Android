package com.yesferal.hornsapp.app.presentation.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.custom.RecyclerViewVerticalDecorator
import com.yesferal.hornsapp.app.presentation.common.extension.postDelayed
import com.yesferal.hornsapp.app.presentation.common.render.RenderFragment
import com.yesferal.hornsapp.app.presentation.di.hada.getViewModel
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewData
import com.yesferal.hornsapp.app.presentation.ui.home.HomeFragmentDirections
import com.yesferal.hornsapp.multitype.DelegateAdapter
import com.yesferal.hornsapp.multitype.abstraction.Delegate

class FavoritesFragment : RenderFragment<FavoritesViewState>() {

    override val layout = R.layout.fragment_favorites

    private lateinit var concertsRecyclerView: RecyclerView
    private lateinit var delegateAdapter: DelegateAdapter
    private lateinit var viewModel: FavoritesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delegateAdapter = DelegateAdapter.Builder()
            .setListener(instanceAdapterListener())
            .build()
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        concertsRecyclerView = view.findViewById(R.id.concertsRecyclerView)
        concertsRecyclerView.also {
            it.adapter = delegateAdapter
            it.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            it.addItemDecoration(RecyclerViewVerticalDecorator())
        }

        viewModel = getViewModel<FavoritesViewModel, FavoritesViewModelFactory>()

        viewModel.stateFavorite.observe(viewLifecycleOwner) {
            render(it)
        }

        postDelayed {
            viewModel.getFavoriteConcerts()
        }
    }

    override fun render(viewState: FavoritesViewState) {
        viewState.items?.let { items ->
            showItems(items)
        }
    }

    private fun showItems(items: List<Delegate>) {
        delegateAdapter.updateItems(items)
    }

    companion object {
        fun newInstance() = FavoritesFragment()
    }
}

private fun FavoritesFragment.instanceAdapterListener() =
    object : UpcomingViewData.Listener {
        override fun onClick(upcomingViewData: UpcomingViewData) {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeToConcert(upcomingViewData.asParcelable())
            )
        }
    }
