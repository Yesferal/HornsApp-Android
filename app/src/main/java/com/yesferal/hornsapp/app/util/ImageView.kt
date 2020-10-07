package com.yesferal.hornsapp.app.util

import android.util.TypedValue
import android.widget.ImageView
import androidx.core.content.ContextCompat
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

fun ImageView.tintWith(color: Int) {
    setColorFilter(
        ContextCompat.getColor(
            context,
            color
        )
    )
}

fun ShapeableImageView.setAllCornersRounded(dp: Int = 16) {

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

fun ShapeableImageView.setTopCornersRounded(dp: Int = 16) {

    val cornerSize = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        resources.displayMetrics
    )

    shapeAppearanceModel = shapeAppearanceModel
        .toBuilder()
        .setTopLeftCorner(CornerFamily.ROUNDED, cornerSize)
        .setTopRightCorner(CornerFamily.ROUNDED, cornerSize)
        .build()
}