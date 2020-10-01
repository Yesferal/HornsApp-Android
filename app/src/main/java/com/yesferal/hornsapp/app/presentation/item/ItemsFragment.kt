package com.yesferal.hornsapp.app.presentation.item

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.BaseFragment
import com.yesferal.hornsapp.app.presentation.common.ItemParcelable
import com.yesferal.hornsapp.app.presentation.common.asParcelable
import com.yesferal.hornsapp.app.presentation.item.adapter.ItemAdapter
import com.yesferal.hornsapp.app.presentation.item.adapter.Item
import com.yesferal.hornsapp.app.presentation.concert.detail.ConcertActivity
import com.yesferal.hornsapp.app.presentation.concert.detail.EXTRA_PARAM_PARCELABLE
import com.yesferal.hornsapp.app.util.RecyclerViewDecorator
import com.yesferal.hornsapp.app.util.fadeIn
import com.yesferal.hornsapp.app.util.fadeOut
import com.yesferal.hornsapp.hada.container.resolve
import kotlinx.android.synthetic.main.custom_error.*
import kotlinx.android.synthetic.main.custom_view_progress_bar.*
import kotlinx.android.synthetic.main.fragment_items.*

class ItemsFragment
    : BaseFragment() {

    private lateinit var itemAdapter: ItemAdapter

    override val actionListener by lazy {
        container.resolve<ItemsPresenter>()
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

        itemAdapter = ItemAdapter(instanceAdapterListener())
        itemsRecyclerView.also {
            it.adapter = itemAdapter
            it.layoutManager = linearLayoutManager
            it.addItemDecoration(RecyclerViewDecorator())
        }

        actionListener.onViewCreated(item.id)
    }

    fun showProgress() {
        customProgressBar.fadeIn()
    }

    fun hideProgress() {
        customProgressBar.fadeOut()
    }

    fun show(items: List<Item>) {
        itemAdapter.setItem(items)
    }

    fun showError(@StringRes messageId: Int) {
        stubView.visibility = View.VISIBLE
        errorTextView.text = getString(messageId)
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

private fun ItemsFragment.instanceAdapterListener() =
    object : ItemAdapter.Listener {
        override fun onClick(item: Item) {
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