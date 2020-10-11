package com.yesferal.hornsapp.app.presentation.concert

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import com.google.android.gms.ads.AdView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.BaseFragment
import com.yesferal.hornsapp.app.presentation.concert.adapter.ConcertAdapter
import com.yesferal.hornsapp.app.presentation.concert.detail.ConcertActivity
import com.yesferal.hornsapp.app.presentation.concert.detail.EXTRA_PARAM_PARCELABLE
import com.yesferal.hornsapp.app.presentation.common.entity.asParcelable
import com.yesferal.hornsapp.app.presentation.concert.adapter.CategoryAdapter
import com.yesferal.hornsapp.app.util.*
import com.yesferal.hornsapp.domain.entity.Category
import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.hada.container.resolve
import kotlinx.android.synthetic.main.custom_error.*
import kotlinx.android.synthetic.main.custom_view_progress_bar.*
import kotlinx.android.synthetic.main.fragment_concerts.*

class ConcertsFragment
    : BaseFragment<ConcertsViewState>() {

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var concertAdapter: ConcertAdapter
    private lateinit var stubViewInflated: View

    override val actionListener by lazy {
        container.resolve<ConcertsPresenter>()
    }

    var listener: Listener? = null
    interface Listener {
        fun show(adView: AdView)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_concerts, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        categoryAdapter = CategoryAdapter(instanceItemAdapterListener())
        categoriesRecyclerView.also {
            it.adapter = categoryAdapter
            it.layoutManager = linearLayoutManager
            it.addItemDecoration(RecyclerViewHorizontalDecorator(16))
        }

        concertAdapter = ConcertAdapter(instanceConcertAdapterListener())

        concertsRecyclerView.also {
            it.adapter = concertAdapter
            it.layoutManager = linearLayoutManagerVertical
            it.addItemDecoration(RecyclerViewVerticalDecorator())
        }

        actionListener.onViewCreated()
    }

    override fun render(viewState: ConcertsViewState) {
        viewState.categories?.let { categories ->
            showCategories(categories = categories)
        }
        viewState.selectedCategory?.let { category ->
            showCategorySelected(category)
        }
        viewState.concerts?.let { concerts ->
            showConcerts(concerts)
        }
        viewState.adView?.let { adView ->
            showAd(adView)
        }

        viewState.errorMessage?.let {
            showError(
                messageId =  viewState.errorMessage,
                allowRetry = viewState.allowRetry
            )
        }?: kotlin.run { hideError() }

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

    private fun showConcerts(concerts: List<Concert>) {
        concertAdapter.setItem(concerts)
    }

    private fun showCategorySelected(category: Category) {
        categoryAdapter.setCategoryId(category._id)
    }

    private fun showCategories(categories: List<Category>) {
        categoryAdapter.setItems(categories)
    }

    private fun showAd(adView: AdView) {
        listener?.show(adView)
    }

    private fun showError(
        @StringRes messageId: Int,
        allowRetry: Boolean
    ) {
        if (!::stubViewInflated.isInitialized) {
            stubViewInflated = stubView.inflate()
        }
        stubViewInflated.visibility = View.VISIBLE
        errorTextView.text = getString(messageId)
        if (allowRetry) {
            tryAgainTextView.visibility = View.VISIBLE
        }
        tryAgainTextView.setOnClickListener {
            actionListener.onRefresh()
            tryAgainTextView.visibility = View.GONE
        }
    }

    private fun hideError() {
        if (::stubViewInflated.isInitialized) {
            stubViewInflated.visibility = View.GONE
        }
    }

    companion object {
        fun newInstance() = ConcertsFragment()
    }
}

private fun ConcertsFragment.instanceConcertAdapterListener() =
    object : ConcertAdapter.Listener {
        override fun onConcertItemClick(concert: Concert) {
            activity?.let {
                val intent = Intent(
                    it,
                    ConcertActivity::class.java
                )

                intent.putExtra(
                    EXTRA_PARAM_PARCELABLE,
                    concert.asParcelable()
                )

                startActivity(intent)
            }
        }
    }

private fun ConcertsFragment.instanceItemAdapterListener() =
    object : CategoryAdapter.Listener {
        override fun onClick(category: Category) {
            actionListener.onCategoryClick(category)
        }
    }