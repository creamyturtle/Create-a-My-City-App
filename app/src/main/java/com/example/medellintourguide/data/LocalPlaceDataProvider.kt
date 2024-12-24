package com.example.medellintourguide.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.medellintourguide.R
import com.example.medellintourguide.model.Category
import com.example.medellintourguide.model.Place

object LocalPlaceDataProvider {

    val defaultPlace = getPlacesData()[0]


    fun getPlacesData(): List<Place> {
        return listOf(
            Place(
                id = 1,
                category = R.string.hiking,
                titleId = R.string.cerro_de_las_tres_cruces,
                typicalGroupSize = 8,
                free = true,
                thumbnailImageId = R.drawable.hiking1,
                headerImageId = R.drawable.hiking2,
                placeDetails = R.string.hiking_the_hill_at_tres_cruces
            ),
            Place(
                id = 2,
                category = R.string.hiking,
                titleId = R.string.backdoor_to_parque_arvi,
                typicalGroupSize = 8,
                free = true,
                thumbnailImageId = R.drawable.hiking4,
                headerImageId = R.drawable.hiking3,
                placeDetails = R.string.we_recommend_this_hike
            ),
            Place(
                id = 3,
                category = R.string.hiking,
                titleId = R.string.arenales_waterfall_hike,
                typicalGroupSize = 8,
                free = true,
                thumbnailImageId = R.drawable.hiking5,
                headerImageId = R.drawable.hiking6,
                placeDetails = R.string.this_is_probably_the_best_known
            ),


            Place(
                id = 4,
                category = R.string.tourism,
                titleId = R.string.comuna_13_tour,
                typicalGroupSize = 4,
                free = false,
                thumbnailImageId = R.drawable.comuna13_2,
                headerImageId = R.drawable.comuna13_1,
                placeDetails = R.string.explore_the_most_historic
            ),
            Place(
                id = 5,
                category = R.string.tourism,
                titleId = R.string.backdoor_to_parque_arvi,
                typicalGroupSize = 8,
                free = true,
                thumbnailImageId = R.drawable.hiking4,
                headerImageId = R.drawable.hiking3,
                placeDetails = R.string.we_recommend_this_hike
            ),
            Place(
                id = 6,
                category = R.string.tourism,
                titleId = R.string.arenales_waterfall_hike,
                typicalGroupSize = 8,
                free = true,
                thumbnailImageId = R.drawable.hiking4,
                headerImageId = R.drawable.hiking3,
                placeDetails = R.string.this_is_probably_the_best_known
            ),

            Place(
                id = 7,
                category = R.string.recreation,
                titleId = R.string.cerro_de_las_tres_cruces,
                typicalGroupSize = 8,
                free = true,
                thumbnailImageId = R.drawable.hiking1,
                headerImageId = R.drawable.hiking2,
                placeDetails = R.string.hiking_the_hill_at_tres_cruces
            ),
            Place(
                id = 8,
                category = R.string.recreation,
                titleId = R.string.backdoor_to_parque_arvi,
                typicalGroupSize = 8,
                free = true,
                thumbnailImageId = R.drawable.hiking4,
                headerImageId = R.drawable.hiking3,
                placeDetails = R.string.we_recommend_this_hike
            ),
            Place(
                id = 9,
                category = R.string.recreation,
                titleId = R.string.arenales_waterfall_hike,
                typicalGroupSize = 8,
                free = true,
                thumbnailImageId = R.drawable.hiking4,
                headerImageId = R.drawable.hiking3,
                placeDetails = R.string.this_is_probably_the_best_known
            )


        )
    }

    val defaultCategory = getCategoriesData()[0]

    fun getCategoriesData(): List<Category> {
        return listOf(
            Category(
                id = 1,
                name = R.string.hiking,
                imageId = R.drawable.hiking1,
            ),
            Category(
                id = 2,
                name = R.string.tourism,
                imageId = R.drawable.comuna13_2,
            ),
            Category(
                id = 3,
                name = R.string.recreation,
                imageId = R.drawable.atv1,
            )


        )
    }












}

