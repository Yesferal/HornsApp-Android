package com.yesferal.hornsapp.app.presentation.ui.concert.newest

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.BaseFragment
import com.yesferal.hornsapp.app.presentation.common.base.ParcelableViewData
import com.yesferal.hornsapp.app.presentation.common.custom.RecyclerViewVerticalDecorator
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewData
import com.yesferal.hornsapp.app.presentation.ui.home.HomeFragmentDirections
import com.yesferal.hornsapp.multitype.DelegateAdapter
import com.yesferal.hornsapp.multitype.abstraction.Delegate

class NewestFragment : BaseFragment<NewestViewState>() {

    override val layout = R.layout.fragment_newest

    private lateinit var viewModel: NewestViewModel
    private lateinit var delegateAdapter: DelegateAdapter

    private lateinit var newestRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delegateAdapter = DelegateAdapter.Builder()
            .setListener(instanceAdapterListener())
            .build()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newestRecyclerView = view.findViewById(R.id.newestRecyclerView)
        newestRecyclerView.also {
            it.adapter = delegateAdapter
            it.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            it.addItemDecoration(RecyclerViewVerticalDecorator())
        }

        viewModel = ViewModelProvider(
            viewModelStore,
            hada().resolve<NewestViewModelFactory>()
        ).get(NewestViewModel::class.java)

        viewModel.stateNewest.observe(viewLifecycleOwner) {
            render(it)
        }
    }

    override fun render(viewState: NewestViewState) {
        viewState.items?.let { items ->
            showItems(items)
        }
    }

    private fun showItems(items: List<Delegate>) {
        delegateAdapter.updateItems(items)
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
            findNavController().navigate(
                HomeFragmentDirections
                    .actionHomeToConcert(parcelableViewData)
            )
        }
    }
