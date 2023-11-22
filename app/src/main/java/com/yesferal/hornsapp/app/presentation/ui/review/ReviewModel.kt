/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.review

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.imageview.ShapeableImageView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.extension.fadeIn
import com.yesferal.hornsapp.app.presentation.common.extension.fadeOut
import com.yesferal.hornsapp.app.presentation.common.extension.load
import com.yesferal.hornsapp.app.presentation.common.extension.setAllCornersRounded
import com.yesferal.hornsapp.app.presentation.common.extension.setUpWith
import com.yesferal.hornsapp.delegate.abstraction.DelegateListener
import com.yesferal.hornsapp.delegate.delegate.InteractiveDelegate
import com.yesferal.hornsapp.delegate.delegate.NonInteractiveDelegate

data class ImageReviewViewData(
    val imageUrl: String?,
    val description: String?
): NonInteractiveDelegate {
    override val layout: Int
        get() = R.layout.item_review_image

    override fun onBindViewDelegate(view: View) {
        view.findViewById<TextView>(R.id.descriptionTextView).setUpWith(description)
        val reviewImageView = view.findViewById<ShapeableImageView>(R.id.reviewImageView)
        reviewImageView.setAllCornersRounded()
        reviewImageView.load(imageUrl)
    }
}

data class ProgressBarViewData(
    val visible: Boolean
): NonInteractiveDelegate {
    override val layout: Int
        get() = R.layout.custom_view_progress_bar

    override fun onBindViewDelegate(view: View) {
        val customProgressBar = view.findViewById<View>(R.id.customProgressBar)
        if (visible) {
            customProgressBar.fadeIn()
        }else {
            customProgressBar.fadeOut()
        }
    }
}

data class DescriptionReviewViewData(
    val reviewText: String?
): NonInteractiveDelegate {
    override val layout: Int
        get() = R.layout.item_review_description

    override fun onBindViewDelegate(view: View) {
        val textView = view.findViewById<TextView>(R.id.reviewTextView)
        textView.setUpWith(reviewText)
    }
}

data class TitleReviewViewData(
    val reviewText: String?
): InteractiveDelegate<TitleReviewViewData.Listener> {
    override val layout: Int
        get() = R.layout.item_review_title

    interface Listener : DelegateListener {
        fun onCloseClick()
    }

    override fun onBindViewDelegate(view: View, listener: Listener) {
        view.findViewById<TextView>(R.id.titleTextView).setUpWith(reviewText)

        view.findViewById<ImageView>(R.id.closeImageView).setOnClickListener {
            listener.onCloseClick()
        }
    }
}
