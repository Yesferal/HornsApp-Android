package com.yesferal.hornsapp.app.presentation.common.delegate

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.framework.adMob.AdUnitIds
import com.yesferal.hornsapp.app.framework.adMob.BusinessModelFactoryProducer
import com.yesferal.hornsapp.app.presentation.common.extension.addBottomView
import com.yesferal.hornsapp.app.presentation.common.render.RenderFragment
import com.yesferal.hornsapp.app.presentation.ui.screen_render.ProgressBarViewData
import com.yesferal.hornsapp.delegate.DelegateAdapter
import com.yesferal.hornsapp.delegate.abstraction.DelegateListener
import com.yesferal.hornsapp.hadi_android.hadi

abstract class DelegateAdapterFragment : RenderFragment<DelegateViewState>(), DelegateListener {

    private lateinit var delegateAdapter: DelegateAdapter

    protected lateinit var delegateRecyclerView: RecyclerView

    private lateinit var firstAdLayout: FrameLayout

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
            it.layoutManager = getLayoutManager()
        }

        firstAdLayout = view.findViewById(R.id.firstAdLayout)
    }

    override fun render(viewState: DelegateViewState) {
        viewState.delegates?.let {
            delegateAdapter.updateDelegates(it)
        }

        if (viewState.renderAd) {
            renderAd()
        } else {
            firstAdLayout.removeAllViews()
        }
    }

    private fun renderAd() {
        val abstractViewFactory = hadi().resolve<BusinessModelFactoryProducer>().getViewFactory()
        firstAdLayout.addBottomView(abstractViewFactory, AdUnitIds.Type.FIRST_CONCERT_DETAIL, 50)
    }

    open fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}
