package com.yesferal.hornsapp.app.presentation.ui.concert.upcoming

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.BaseFragment
import com.yesferal.hornsapp.app.presentation.common.custom.*
import com.yesferal.hornsapp.app.presentation.ui.filters.CategoryViewData
import com.yesferal.hornsapp.app.presentation.ui.home.HomeFragmentDirections
import com.yesferal.hornsapp.app.presentation.ui.home.HomeViewModel
import com.yesferal.hornsapp.app.presentation.ui.home.HomeViewModelFactory
import com.yesferal.hornsapp.multitype.MultiTypeAdapter
import com.yesferal.hornsapp.multitype.model.ViewHolderBinding
import kotlinx.android.synthetic.main.custom_view_progress_bar.*
import kotlinx.android.synthetic.main.fragment_upcoming.*

class UpcomingFragment
    : BaseFragment<UpcomingViewState>() {

    override val layout: Int
        get() = R.layout.fragment_upcoming

    private lateinit var multiTypeAdapter: MultiTypeAdapter
    lateinit var homeViewModel: HomeViewModel

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

        Handler(Looper.getMainLooper()).postDelayed({
            activity?.viewModelStore?.let { viewModelStore ->
                homeViewModel = ViewModelProvider(
                    viewModelStore,
                    container.resolve<HomeViewModelFactory>()
                ).get(HomeViewModel::class.java)

                homeViewModel.stateUpcoming.observe(viewLifecycleOwner) {
                    render(it)
                }
            }
        }, 333)
    }

    override fun render(viewState: UpcomingViewState) {
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

    private fun showItems(
        items: List<ViewHolderBinding>
    ) {
        multiTypeAdapter.setModels(items)
    }

    companion object {
        fun newInstance() = UpcomingFragment()
    }
}

private fun UpcomingFragment.instanceAdapterListener() =
    object : FiltersViewData.Listener,
        UpcomingViewData.Listener {

        override fun onClick(upcomingViewData: UpcomingViewData) {
            findNavController().navigate(
                HomeFragmentDirections
                    .actionHomeToConcert(upcomingViewData.asParcelable())
            )
        }

        override fun onClick(categoryViewData: CategoryViewData) {
            homeViewModel.onFilterClick(categoryViewData)
        }
    }