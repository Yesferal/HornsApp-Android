package com.yesferal.hornsapp.app.util

import android.util.TypedValue
import android.widget.ImageView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.squareup.picasso.Picasso

fun ImageView.load(url: String?) {
    if (url.isNullOrEmpty()) {
        return
    }

    Picasso.get()
        .load(url)
        .into(this)
}

fun ShapeableImageView.setAllCornersRounded(dp: Int) {

    val cornerSize = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        resources.displayMetrics
    )

    shapeAppearanceModel = shapeAppearanceModel
        .toBuilder()
        .setAllCorners(CornerFamily.ROUNDED, cornerSize)
        .build()
}