package com.example.medellintourguide.model

import androidx.annotation.DrawableRes
import com.example.medellintourguide.data.LocalPlaceDataProvider

data class PlacesUiState(

    // Category Selection, Attraction Selection

    val category: Category = LocalPlaceDataProvider.defaultCategory,
    val placeId: Int = 1,
    @DrawableRes val thumbnail: Int = -1,
    @DrawableRes val banner: Int = -1,
    val placesList: List<Place> = emptyList(),
    val currentPlace: Place = LocalPlaceDataProvider.defaultPlace


    )