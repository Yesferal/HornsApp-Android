package com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.ui.custom.setUpWith
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.ErrorViewData
import kotlinx.android.synthetic.main.custom_error.view.*

class ErrorViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    constructor(
        parent: ViewGroup
    ) : this(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.custom_error, parent, false)
    )

    fun bind(viewData: ErrorViewData) {
        itemView.errorTextView.let {
            it.setUpWith(it.context.getString(viewData.errorMessage))
        }
    }
}