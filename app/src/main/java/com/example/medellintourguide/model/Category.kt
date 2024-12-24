package com.example.medellintourguide.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Category(
    val id: Int,
    @StringRes val name: Int,
    @DrawableRes val imageId: Int
)