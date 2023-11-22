package com.yesferal.hornsapp.app.presentation.common.delegate

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.render.RenderFragment
import com.yesferal.hornsapp.app.presentation.ui.review.ProgressBarViewData
import com.yesferal.hornsapp.delegate.DelegateAdapter
import com.yesferal.hornsapp.delegate.abstraction.DelegateListener

abstract class DelegateAdapterFragment : RenderFragment<DelegateViewState>(), DelegateListener {

    private lateinit var delegateAdapter: DelegateAdapter

    protected lateinit var delegateRecyclerView: RecyclerView

    override val layout: Int
        get() = R.layout.fragment_delegate_adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delegateAdapter = DelegateAdapter.Builder()
            .addItem(ProgressBarViewData(visible = true))
            .setListener(this)
            .build()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        delegateRecyclerView = view.findViewById(R.id.delegateRecyclerView)

        delegateRecyclerView.also {
            it.adapter = delegateAdapter
            it.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun render(viewState: DelegateViewState) {
        viewState.delegates?.let {
            delegateAdapter.updateDelegates(it)
        }
    }
}
