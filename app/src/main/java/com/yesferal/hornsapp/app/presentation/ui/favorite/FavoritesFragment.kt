package com.yesferal.hornsapp.app.presentation.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.BaseFragment
import com.yesferal.hornsapp.app.presentation.common.custom.*
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewData
import com.yesferal.hornsapp.app.presentation.ui.home.HomeFragmentDirections
import com.yesferal.hornsapp.multitype.MultiTypeAdapter
import com.yesferal.hornsapp.multitype.model.ViewHolderBinding
import kotlinx.android.synthetic.main.custom_view_progress_bar.*
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment
    : BaseFragment<FavoritesViewState>() {

    override val layout: Int
        get() = R.layout.fragment_favorites

    private lateinit var multiTypeAdapter: MultiTypeAdapter
    private lateinit var favoritesViewModel: FavoritesViewModel

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

        favoritesViewModel = ViewModelProvider(
            this,
            container.resolve<FavoritesViewModelFactory>()
        ).get(FavoritesViewModel::class.java)

        favoritesViewModel.state.observe(viewLifecycleOwner) {
            render(it)
        }
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
        multiTypeAdapter.setModels(items)
    }

    companion object {
        fun newInstance() = FavoritesFragment()
    }
}

private fun FavoritesFragment.instanceAdapterListener() =
    object : UpcomingViewData.Listener {
        override fun onClick(upcomingViewData: UpcomingViewData) {
            findNavController().navigate(
                HomeFragmentDirections
                    .actionHomeToConcert(upcomingViewData.asParcelable())
            )
        }
    }