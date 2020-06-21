package com.yesferal.hornsapp.app.presentation.concert

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.viewpager2.widget.ViewPager2
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.BaseFragment
import com.yesferal.hornsapp.app.presentation.concert.adapter.ConcertAdapter
import com.yesferal.hornsapp.app.presentation.concert.adapter.PageTransformation
import com.yesferal.hornsapp.app.common.load
import com.yesferal.hornsapp.app.presentation.concert.detail.ConcertDetailActivity
import com.yesferal.hornsapp.app.presentation.concert.detail.EXTRA_PARAM_PARCELABLE
import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.hada.container.resolve
import kotlinx.android.synthetic.main.fragment_concerts.*

class ConcertsFragment
    : BaseFragment() {

    private lateinit var concertAdapter: ConcertAdapter

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
        actionListener.onViewCreated()
        concertAdapter = initAdapter()
        concertsViewPager.adapter = concertAdapter
        concertsViewPager.setPageTransformer(PageTransformation())
    }

    fun show(concerts: List<Concert>) {
        concertAdapter.setItem(concerts)

        concertsViewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    val concert = concerts[position]
                    concertImageView.load(concert.headlinerImage)
                }
            }
        )
    }

    companion object {
        fun newInstance() = ConcertsFragment()
    }
}

fun ConcertsFragment.initAdapter() =
    ConcertAdapter(
        object :
            ConcertAdapter.Listener {
            override fun onConcertItemClick(concert: Concert, concertImageView: ImageView) {
                activity?.let {
                    val intent = Intent(
                        it,
                        ConcertDetailActivity::class.java
                    )
                    intent.putExtra(
                        EXTRA_PARAM_PARCELABLE,
                        concert.asParcelable()
                    )

                    val options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(
                            it,
                            Pair(
                                concertImageView,
                                getString(R.string.transitionNameConcertImageView)
                            )
                        )

                    startActivity(intent, options.toBundle())
                }
            }
        })