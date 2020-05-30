package com.yesferal.hornsapp.app.presentation.concerts

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
import com.yesferal.hornsapp.app.presentation.common.base.BaseFragment
import com.yesferal.hornsapp.app.presentation.concerts.adapter.ConcertAdapter
import com.yesferal.hornsapp.app.presentation.concerts.adapter.PageTransformation
import com.yesferal.hornsapp.app.presentation.common.base.State
import com.yesferal.hornsapp.app.presentation.common.extension.load
import com.yesferal.hornsapp.app.presentation.concertDetail.ConcertDetailActivity
import com.yesferal.hornsapp.app.presentation.concertDetail.EXTRA_PARAM_PARCELABLE
import com.yesferal.hornsapp.app.presentation.concerts.model.asParcelable
import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.hada.container.resolve
import kotlinx.android.synthetic.main.fragment_concerts.*

class ConcertsFragment
    : BaseFragment(),
    ConcertsContract.View,
    ConcertAdapter.Listener {

    private lateinit var concertAdapter: ConcertAdapter

    override val actionListener by lazy {
        getContainer().resolve<ConcertsContract.ActionListener>()
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
        actionListener.onCreate()
        concertAdapter = ConcertAdapter(this)
        concertsViewPager.adapter = concertAdapter
        concertsViewPager.setPageTransformer(PageTransformation())
    }

    override fun updateWith(state: State<List<Concert>>) {
        when(state) {
            is State.Success -> {
                show(concerts = state.data)
            }
        }
    }

    private fun show(concerts: List<Concert>) {
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

    override fun onConcertItemClick(
        concert: Concert,
        concertImageView: ImageView
    ) {
        activity?.let {
            val intent = Intent(it, ConcertDetailActivity::class.java)
            intent.putExtra(EXTRA_PARAM_PARCELABLE, concert.asParcelable())

            val options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(
                    it,
                    Pair(concertImageView, "concertImageView")
                )

            startActivity(intent, options.toBundle())
        }
    }

    companion object {
        fun newInstance() = ConcertsFragment()
    }
}
