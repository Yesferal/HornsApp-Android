package com.yesferal.hornsapp.app.presentation.concert

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.ads.AdView
import com.google.android.material.tabs.TabLayoutMediator
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.util.HornsAppBottomSheetFragment
import com.yesferal.hornsapp.app.presentation.common.BaseFragment
import com.yesferal.hornsapp.app.presentation.item.adapter.ItemAdapter
import com.yesferal.hornsapp.app.presentation.item.adapter.Item
import com.yesferal.hornsapp.app.presentation.item.adapter.mapToBaseItem
import com.yesferal.hornsapp.app.presentation.concert.adapter.ConcertAdapter
import com.yesferal.hornsapp.app.presentation.concert.detail.ConcertActivity
import com.yesferal.hornsapp.app.presentation.concert.detail.EXTRA_PARAM_PARCELABLE
import com.yesferal.hornsapp.app.presentation.common.asParcelable
import com.yesferal.hornsapp.app.util.*
import com.yesferal.hornsapp.domain.entity.Category
import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.hada.container.resolve
import kotlinx.android.synthetic.main.custom_error.*
import kotlinx.android.synthetic.main.custom_view_progress_bar.*
import kotlinx.android.synthetic.main.fragment_concerts.*
import java.net.URI

class ConcertsFragment
    : BaseFragment() {

    private lateinit var concertAdapter: ConcertAdapter
    private lateinit var categoryAdapter: ItemAdapter
    private lateinit var stubViewInflated: View

    override val actionListener by lazy {
        container.resolve<ConcertsPresenter>()
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

        concertAdapter = ConcertAdapter(instanceConcertAdapterListener())
        concertsViewPager.also {
            it.adapter = concertAdapter
            it.setPageTransformer(PageTransformation())
        }

        TabLayoutMediator(tabLayout, concertsViewPager) { _,_ -> }
            .attach()

        categoryAdapter = ItemAdapter(instanceItemAdapterListener())
        categoryRecyclerView.also {
            it.adapter = categoryAdapter
            it.layoutManager = linearLayoutManager
            it.addItemDecoration(RecyclerViewDecorator())
        }

        actionListener.onViewCreated()
    }

    fun showProgress() {
        customProgressBar.fadeIn()
    }

    fun hideProgress() {
        customProgressBar.fadeOut()
    }

    fun show(concerts: List<Concert>) {
        concertAdapter.setItem(concerts)

        concertsViewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    val concert = concerts[position]
                    concertImageView.load(concert.headlinerImage)
                    concertImageView.fadeIn()
                }
            }
        )
    }

    fun showCategories(categories: List<Category>) {
        //TODO("Move to presenter")
        val items = categories.map { it.mapToBaseItem() }
        categoryAdapter.setItem(items)
    }

    fun show(adView: AdView) {
        adContainerLayout.removeAllViews()
        adContainerLayout.addView(adView)
    }

    fun showError(@StringRes messageId: Int) {
        if (!::stubViewInflated.isInitialized) {
            stubViewInflated = stubView.inflate()
        }
        stubViewInflated.visibility = View.VISIBLE
        errorTextView.text = getString(messageId)
        tryAgainTextView.visibility = View.VISIBLE
        tryAgainTextView.setOnClickListener {
            actionListener.onRefresh()
            tryAgainTextView.visibility = View.GONE
        }
    }

    fun hideError() {
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

        override fun onFacebookButtonClick(uri: URI) {
            val event = uri.path.replace("/events", "event")
            val fbUri = Uri.parse("fb://$event")
            startExternalActivity(fbUri, getString(R.string.facebook_package)) {
                startExternalActivity(uri)
            }
        }

        override fun onYoutubeButtonClick(uri: URI) {
            startExternalActivity(uri)
        }

        override fun onFavoriteButtonClick(concert: Concert) {
            actionListener.onFavoriteButtonClick(concert)
        }
    }

private fun ConcertsFragment.instanceItemAdapterListener() =
    object : ItemAdapter.Listener {
        override fun onClick(item: Item) {
            childFragmentManager.let {
                val bundle = Bundle()
                bundle.putParcelable(EXTRA_PARAM_PARCELABLE, item.asParcelable())

                HornsAppBottomSheetFragment.newInstance(bundle).apply {
                    show(it, tag)
                }
            }
        }
    }