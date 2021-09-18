package com.yesferal.hornsapp.app.presentation.ui.onboarding

import android.view.View
import android.widget.TextView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.extension.setUpWith
import com.yesferal.hornsapp.multitype.abstraction.Delegate
import com.yesferal.hornsapp.multitype.abstraction.DelegateListener
import com.yesferal.hornsapp.multitype.delegate.NonInteractiveDelegate

data class OnBoardingViewState(
    val categories: List<Delegate>? = null,
    val isLoading: Boolean = false
)

data class OnBoardingCategoryViewData(
    val category: String,
    val amount: Int
) : NonInteractiveDelegate {

    override val layout = R.layout.item_on_boarding_category

    override fun onBindViewDelegate(view: View, listener: DelegateListener) {
        view.findViewById<TextView>(R.id.titleTextView).setUpWith(amount.toString())
        view.findViewById<TextView>(R.id.subtitleTextView).setUpWith(category)
    }
}
