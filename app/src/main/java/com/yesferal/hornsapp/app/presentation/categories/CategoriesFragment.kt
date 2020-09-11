package com.yesferal.hornsapp.app.presentation.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.BaseFragment
import com.yesferal.hornsapp.app.presentation.common.asParcelable
import com.yesferal.hornsapp.app.presentation.concert.detail.EXTRA_PARAM_PARCELABLE
import com.yesferal.hornsapp.app.presentation.item.ItemBottomSheetFragment
import com.yesferal.hornsapp.app.presentation.item.adapter.Item
import com.yesferal.hornsapp.app.presentation.item.adapter.ItemAdapter
import com.yesferal.hornsapp.app.presentation.item.adapter.mapToBaseItem
import com.yesferal.hornsapp.app.util.RecyclerViewDecorator
import com.yesferal.hornsapp.domain.entity.Category
import com.yesferal.hornsapp.hada.container.resolve
import kotlinx.android.synthetic.main.fragment_categories.*

class CategoriesFragment
    : BaseFragment() {
    private lateinit var categoryAdapter: ItemAdapter

    override val actionListener by lazy {
        container.resolve<CategoriesPresenter>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryAdapter = ItemAdapter(instanceItemAdapterListener())
        categoriesRecyclerView.also {
            it.adapter = categoryAdapter
            it.layoutManager = linearLayoutManager
            it.addItemDecoration(RecyclerViewDecorator())
        }

        actionListener.onViewCreated()
    }

    fun show(categories: List<Category>) {
        //TODO("Move to presenter")
        val items = categories.map { it.mapToBaseItem() }
        categoryAdapter.setItem(items)
    }

    companion object {
        fun newInstance() = CategoriesFragment()
    }
}

private fun CategoriesFragment.instanceItemAdapterListener() =
    object : ItemAdapter.Listener {
        override fun onClick(item: Item) {
            childFragmentManager.let {
                val bundle = Bundle()
                bundle.putParcelable(EXTRA_PARAM_PARCELABLE, item.asParcelable())

                ItemBottomSheetFragment.newInstance(bundle).apply {
                    show(it, tag)
                }
            }
        }
    }