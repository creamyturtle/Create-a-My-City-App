package com.example.medellintourguide.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Place(
    val id: Int,
    @StringRes val category: Int,
    @StringRes val titleId: Int,
    val typicalGroupSize: Int,
    val free: Boolean,
    @DrawableRes val thumbnailImageId: Int,
    @DrawableRes val headerImageId: Int,
    @StringRes val placeDetails: Int
)
