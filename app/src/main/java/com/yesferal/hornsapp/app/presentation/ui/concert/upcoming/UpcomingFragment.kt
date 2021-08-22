package com.yesferal.hornsapp.app.presentation.ui.concert.upcoming

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.BaseFragment
import com.yesferal.hornsapp.app.presentation.common.custom.RecyclerViewVerticalDecorator
import com.yesferal.hornsapp.app.presentation.common.extension.postDelayed
import com.yesferal.hornsapp.app.presentation.ui.filters.CategoryViewData
import com.yesferal.hornsapp.app.presentation.ui.home.HomeFragmentDirections
import com.yesferal.hornsapp.multitype.DelegateAdapter
import com.yesferal.hornsapp.multitype.abstraction.Delegate

class UpcomingFragment : BaseFragment<UpcomingViewState>() {

    override val layout = R.layout.fragment_upcoming

    lateinit var viewModel: UpcomingViewModel
    private lateinit var delegateAdapter: DelegateAdapter

    private lateinit var concertsRecyclerView: RecyclerView

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
            it.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
            it.addItemDecoration(RecyclerViewVerticalDecorator())
        }

        viewModel = ViewModelProvider(
            viewModelStore,
            hada().resolve<UpcomingViewModelFactory>()
        ).get(UpcomingViewModel::class.java)

        postDelayed {
            viewModel.stateUpcoming.observe(viewLifecycleOwner) {
                render(it)
            }
        }
    }

    override fun render(viewState: UpcomingViewState) {
        viewState.items?.let { items ->
            showItems(items)
        }
    }

    private fun showItems(
        items: List<Delegate>
    ) {
        delegateAdapter.updateItems(items)
    }

    companion object {
        fun newInstance() = UpcomingFragment()
    }
}

private fun UpcomingFragment.instanceAdapterListener() =
    object : CategoryViewData.Listener,
        UpcomingViewData.Listener {

        override fun onClick(upcomingViewData: UpcomingViewData) {
            findNavController().navigate(
                HomeFragmentDirections
                    .actionHomeToConcert(upcomingViewData.asParcelable())
            )
        }

        override fun onClick(categoryViewData: CategoryViewData) {
            viewModel.onCategoryClick(categoryViewData)
        }
    }
