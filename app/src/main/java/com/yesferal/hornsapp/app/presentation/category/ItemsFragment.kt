package com.yesferal.hornsapp.app.presentation.category

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.BaseFragment
import com.yesferal.hornsapp.app.presentation.common.adapter.BaseAdapter
import com.yesferal.hornsapp.app.presentation.common.adapter.HornsAppItem
import com.yesferal.hornsapp.app.presentation.concert.*
import com.yesferal.hornsapp.app.presentation.concert.detail.ConcertActivity
import com.yesferal.hornsapp.app.presentation.concert.detail.EXTRA_PARAM_PARCELABLE
import com.yesferal.hornsapp.app.util.RecyclerViewDecorator
import kotlinx.android.synthetic.main.fragment_items.*

class ItemsFragment
    : BaseFragment() {

    private lateinit var itemAdapter: BaseAdapter

    override val actionListener by lazy {
        null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val item = arguments?.getParcelable<ItemParcelable>(
            EXTRA_PARAM_PARCELABLE
        )

        if (item == null) {
            activity?.finish()
            return
        }

        titleTextView.text = item.name

        itemAdapter = BaseAdapter(instanceBaseAdapterListener())
        itemsRecyclerView.also {
            it.adapter = itemAdapter
            it.layoutManager = linearLayoutManager
            it.addItemDecoration(RecyclerViewDecorator())
        }
        itemAdapter.setItem(listOf(
            HornsAppItem("1", "Item 1", "", true),
            HornsAppItem("2", "Item 2", "", true),
            HornsAppItem("3", "Item 3", "", true),
            HornsAppItem("4", "Item 4", "", true),
            HornsAppItem("5", "Item 5", "", true),
            HornsAppItem("6", "Item 6", "", true)
        ))
    }

    companion object {
        fun newInstance(
            bundle: Bundle
        ) : ItemsFragment {
            return ItemsFragment().apply {
                arguments = bundle
            }
        }
    }
}

private fun ItemsFragment.instanceBaseAdapterListener() =
    object : BaseAdapter.Listener {
        override fun onClick(item: HornsAppItem) {
            activity?.let {
                val intent = Intent(
                    it,
                    ConcertActivity::class.java
                )

                intent.putExtra(
                    EXTRA_PARAM_PARCELABLE,
                    item.asParcelable()
                )

                startActivity(intent)
            }
        }
    }