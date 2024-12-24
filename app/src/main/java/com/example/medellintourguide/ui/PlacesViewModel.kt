package com.example.medellintourguide.ui

import androidx.lifecycle.ViewModel
import com.example.medellintourguide.data.LocalPlaceDataProvider
import com.example.medellintourguide.model.Category
import com.example.medellintourguide.model.Place
import com.example.medellintourguide.model.PlacesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PlacesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        PlacesUiState(
            placesList = LocalPlaceDataProvider.getPlacesData(),
            currentPlace = LocalPlaceDataProvider.getPlacesData().getOrElse(0) {
                LocalPlaceDataProvider.defaultPlace
            }
        )
    )
    val uiState: StateFlow<PlacesUiState> = _uiState.asStateFlow()


    fun updateCurrentPlace(selectedPlace: Place) {
        _uiState.update {
            it.copy(currentPlace = selectedPlace)
        }
    }

    fun updateCurrentCategory(selectedCategory: Category) {
        _uiState.update {
            it.copy(category = selectedCategory)
        }
    }




}