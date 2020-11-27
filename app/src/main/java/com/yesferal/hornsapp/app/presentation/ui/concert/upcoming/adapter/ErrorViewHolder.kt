package com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.adapter

import android.view.View
import com.yesferal.hornsapp.app.presentation.common.multitype.HornsViewHolder
import com.yesferal.hornsapp.app.presentation.common.custom.setUpWith
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.ErrorViewData
import kotlinx.android.synthetic.main.custom_error.view.*

class ErrorViewHolder(
    itemView: View
) : HornsViewHolder<ErrorViewData>(itemView) {

    override fun bind(viewData: ErrorViewData) {
        itemView.errorTextView.let {
            it.setUpWith(it.context.getString(viewData.errorMessage))
        }
    }
}