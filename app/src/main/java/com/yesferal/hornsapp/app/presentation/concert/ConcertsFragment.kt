package com.yesferal.hornsapp.app.presentation.concert

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.ads.AdView
import com.google.android.material.tabs.TabLayoutMediator
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.BaseFragment
import com.yesferal.hornsapp.app.presentation.concert.adapter.CategoryAdapter
import com.yesferal.hornsapp.app.presentation.concert.adapter.ConcertAdapter
import com.yesferal.hornsapp.app.presentation.concert.detail.ConcertActivity
import com.yesferal.hornsapp.app.presentation.concert.detail.EXTRA_PARAM_PARCELABLE
import com.yesferal.hornsapp.app.util.*
import com.yesferal.hornsapp.domain.entity.Category
import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.hada.container.resolve
import kotlinx.android.synthetic.main.fragment_concerts.*
import java.net.URI

class ConcertsFragment
    : BaseFragment() {

    private lateinit var concertAdapter: ConcertAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    override val actionListener by lazy {
        getContainer().resolve<ConcertsPresenter>()
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

        concertImageView.visibility = View.INVISIBLE

        concertAdapter = initConcertAdapter()
        concertsViewPager.also {
            it.visibility = View.INVISIBLE
            it.adapter = concertAdapter
            it.setPageTransformer(PageTransformation())
        }

        TabLayoutMediator(tabLayout, concertsViewPager) { _,_ -> }
            .attach()

        val viewManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        categoryAdapter = initCategoryAdapter()
        categoryRecyclerView.also {
            it.adapter = categoryAdapter
            it.layoutManager = viewManager
            it.addItemDecoration(RecyclerViewDecorator(context))
        }

        actionListener.onViewCreated()
    }

    fun showProgress() {
        progressBar.fadeIn()
    }

    fun hideProgress() {
        progressBar.fadeOut()
    }

    fun show(concerts: List<Concert>) {
        concertAdapter.setItem(concerts)

        concertsViewPager.fadeIn()
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
        categoryAdapter.setItem(categories)
        categoryRecyclerView.fadeIn()
    }

    fun show(adView: AdView) {
        adContainerLayout.removeAllViews()
        adContainerLayout.addView(adView)
    }

    companion object {
        fun newInstance() = ConcertsFragment()
    }
}

private fun ConcertsFragment.initConcertAdapter() =
    ConcertAdapter(object : ConcertAdapter.Listener {
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
            val androidUri = try {
                activity?.packageManager?.getPackageInfo(
                    getString(R.string.facebook_package),
                    0
                )
                val event = uri.path.replace("/events", "event")
                Uri.parse("fb://$event")
            } catch (e: Exception) {
                Uri.parse(uri.toString())
            }
            startExternalActivity(androidUri)
        }

        override fun onYoutubeButtonClick(uri: URI) {
            startExternalActivity(Uri.parse(uri.toString()))
        }

        private fun startExternalActivity(uri: Uri) {
            val intent = Intent(Intent.ACTION_VIEW,  uri)
            startActivity(intent)
        }

        override fun onFavoriteButtonClick(concert: Concert) {
            actionListener.onFavoriteButtonClick(concert)
        }
    })

private fun ConcertsFragment.initCategoryAdapter() =
    CategoryAdapter(object : CategoryAdapter.Listener {
        override fun openCategory(id: String) {
            showToast(R.string.app_name, Toast.LENGTH_LONG)
        }
    })