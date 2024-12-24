package com.example.medellintourguide

import android.graphics.text.LineBreaker
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.MedellinTourGuideTheme
import com.example.medellintourguide.ui.PlacesViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medellintourguide.data.LocalPlaceDataProvider
import com.example.medellintourguide.model.Category
import com.example.medellintourguide.model.Place
import com.example.medellintourguide.utils.PlacesContentType


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MedellinTourGuideTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val windowSize = calculateWindowSizeClass(this)
                    Guide(
                        windowSize = windowSize.widthSizeClass,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlacesAppBar(
    pageNumber: Int,
    @StringRes currentScreen: Int,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {


        CenterAlignedTopAppBar(
            title = { Text(stringResource(currentScreen)) },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                //containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = modifier.padding(24.dp),
            navigationIcon = {
                if (canNavigateBack) {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back Button"
                        )
                    }
                }
            },

            )


}



@Composable
fun Guide(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {

    val viewModel: PlacesViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    var pageNumber by remember { mutableStateOf(1)}

    var currentScreen = R.string.medellin_tour_guide


    if (pageNumber == 1) {
        currentScreen = R.string.medellin_tour_guide
    } else if (pageNumber == 2) {
        currentScreen = uiState.category.name
    } else if (pageNumber == 3) {
        currentScreen = R.string.experience
    } else {
        currentScreen = R.string.medellin_tour_guide
    }

    var canNav = false

    if (pageNumber == 1) {
        canNav = false
    } else {
        canNav = true
    }

    val contentType: PlacesContentType


    when (windowSize) {

        WindowWidthSizeClass.Compact -> {
            contentType = PlacesContentType.ListOnly
        }
        WindowWidthSizeClass.Medium -> {
            contentType = PlacesContentType.ListOnly
        }
        WindowWidthSizeClass.Expanded -> {
            contentType = PlacesContentType.ListAndDetail
        }
        else -> {
            contentType = PlacesContentType.ListOnly
        }

    }

    Scaffold(
        topBar = {
            PlacesAppBar(
                pageNumber = pageNumber,
                currentScreen = currentScreen,
                canNavigateBack = canNav,
                navigateUp = { pageNumber = pageNumber - 1  }
            )
        },
    ) { innerPadding ->


        if (contentType == PlacesContentType.ListAndDetail) {


            if (pageNumber == 1) {



                CategoriesList(categories = LocalPlaceDataProvider.getCategoriesData(),
                    onClick = {
                        viewModel.updateCurrentCategory(it)
                        //viewModel.navigateToDetailPage()
                        pageNumber = 2
                    },
                    contentPadding = innerPadding,
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(R.dimen.padding_medium))

                )

            } else if (pageNumber == 2) {

                Row() {


                    CategoriesList(categories = LocalPlaceDataProvider.getCategoriesData(),
                        onClick = {
                            viewModel.updateCurrentCategory(it)
                            //viewModel.navigateToDetailPage()
                            pageNumber = 2
                        },
                        contentPadding = innerPadding,
                        modifier = Modifier
                            .weight(2f)
                            .padding(horizontal = dimensionResource(R.dimen.padding_medium))

                    )




                    PlacesList(
                        selectedCategory = uiState.category,
                        places = uiState.placesList,
                        onClick = {
                            viewModel.updateCurrentPlace(it)
                            //viewModel.navigateToDetailPage()
                            pageNumber = 3
                        },
                        onBackPressed = {
                            //viewModel.navigateToListPage()
                            pageNumber = 1
                        },
                        contentPadding = innerPadding,
                        modifier = Modifier
                            .weight(3f)
                            .padding(horizontal = dimensionResource(R.dimen.padding_medium))

                    )
                    
                    

                }





            } else if (pageNumber == 3) {

                
                Row() {


                    PlacesList(
                        selectedCategory = uiState.category,
                        places = uiState.placesList,
                        onClick = {
                            viewModel.updateCurrentPlace(it)
                            //viewModel.navigateToDetailPage()
                            pageNumber = 3
                        },
                        onBackPressed = {
                            //viewModel.navigateToListPage()
                            pageNumber = 1
                        },
                        contentPadding = innerPadding,
                        modifier = Modifier
                            .weight(2f)
                            .padding(horizontal = dimensionResource(R.dimen.padding_medium))

                    )



                    PlaceDetail(
                        selectedPlace = uiState.currentPlace,
                        contentPadding = innerPadding,
                        onBackPressed = {
                            //viewModel.navigateToListPage()
                            pageNumber = 2
                        },
                        modifier = Modifier
                            .weight(3f)
                    )

                }



            } else {

                CategoriesList(categories = LocalPlaceDataProvider.getCategoriesData(),
                    onClick = {
                        viewModel.updateCurrentCategory(it)
                        //viewModel.navigateToDetailPage()
                        pageNumber = 2
                    },
                    contentPadding = innerPadding,
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(R.dimen.padding_medium))

                )

            }




        } else {
            if (pageNumber == 1) {



                CategoriesList(categories = LocalPlaceDataProvider.getCategoriesData(),
                    onClick = {
                        viewModel.updateCurrentCategory(it)
                        //viewModel.navigateToDetailPage()
                        pageNumber = 2
                    },
                    contentPadding = innerPadding,
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(R.dimen.padding_medium))

                )

            } else if (pageNumber == 2) {

                PlacesList(
                    selectedCategory = uiState.category,
                    places = uiState.placesList,
                    onClick = {
                        viewModel.updateCurrentPlace(it)
                        //viewModel.navigateToDetailPage()
                        pageNumber = 3
                    },
                    onBackPressed = {
                        //viewModel.navigateToListPage()
                        pageNumber = 1
                    },
                    contentPadding = innerPadding,
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(R.dimen.padding_medium))

                )



            } else if (pageNumber == 3) {

                PlaceDetail(
                    selectedPlace = uiState.currentPlace,
                    contentPadding = innerPadding,
                    onBackPressed = {
                        //viewModel.navigateToListPage()
                        pageNumber = 2
                    }
                )

            } else {

                CategoriesList(categories = LocalPlaceDataProvider.getCategoriesData(),
                    onClick = {
                        viewModel.updateCurrentCategory(it)
                        //viewModel.navigateToDetailPage()
                        pageNumber = 2
                    },
                    contentPadding = innerPadding,
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(R.dimen.padding_medium))

                )

            }
        }





    }

}




@Composable
private fun CategoriesList(
    categories: List<Category>,
    onClick: (Category) -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier
) {

    LazyColumn(
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        modifier = modifier,
    ) {
        items(categories, key = { category -> category.id }) { category ->
            CategoryListItem(
                category = category,
                onItemClick = onClick
            )
        }
    }


}







@Composable
private fun PlacesList(
    places: List<Place>,
    selectedCategory: Category,
    onClick: (Place) -> Unit,
    onBackPressed: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier
) {
    BackHandler {
        onBackPressed()
    }

    val filteredPlaces = places.filter { it.category == selectedCategory.name }

    LazyColumn(
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        modifier = modifier,
    ) {
        items(filteredPlaces, key = { place -> place.id }) { place ->
            PlacesListItem(
                place = place,
                onItemClick = onClick
            )
        }
    }


}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryListItem(
    category: Category,
    onItemClick: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(),
        modifier = modifier,
        shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius)),
        onClick = { onItemClick(category) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .size(dimensionResource(R.dimen.card_image_height))
        ) {
            CategoriesListImageItem(
                category = category,
                modifier = Modifier.size(dimensionResource(R.dimen.card_image_height))
            )
            Column(
                modifier = Modifier
                    .padding(
                        vertical = dimensionResource(R.dimen.padding_small),
                        horizontal = dimensionResource(R.dimen.padding_medium)
                    )
                    .weight(1f)
            ) {
                Spacer(Modifier.weight(1f))

                Text(
                    text = stringResource(category.name),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.card_text_vertical_space))
                )

                Spacer(Modifier.weight(1f))


            }
        }
    }
}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlacesListItem(
    place: Place,
    onItemClick: (Place) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(),
        modifier = modifier,
        shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius)),
        onClick = { onItemClick(place) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .size(dimensionResource(R.dimen.card_image_height))
        ) {
            PlacesListImageItem(
                place = place,
                modifier = Modifier.size(dimensionResource(R.dimen.card_image_height))
            )
            Column(
                modifier = Modifier
                    .padding(
                        vertical = dimensionResource(R.dimen.padding_small),
                        horizontal = dimensionResource(R.dimen.padding_medium)
                    )
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(place.titleId),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.card_text_vertical_space))
                )
                Text(
                    text = stringResource(place.placeDetails),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
                Spacer(Modifier.weight(1f))
                Row {
                    Text(
                        text = "Typical Group Size: ${place.typicalGroupSize.toString()}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(Modifier.weight(1f))
                    if (place.free) {
                        Text(
                            text = "FREE",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PlacesListImageItem(place: Place, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(place.thumbnailImageId),
            contentDescription = null,
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop
        )
    }
}


@Composable
private fun CategoriesListImageItem(category: Category, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(category.imageId),
            contentDescription = null,
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop
        )
    }
}



@Composable
private fun PlaceDetail(
    selectedPlace: Place,
    onBackPressed: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    BackHandler {
        onBackPressed()
    }
    val scrollState = rememberScrollState()
    val layoutDirection = LocalLayoutDirection.current
    Box(
        modifier = modifier
            .verticalScroll(state = scrollState)
            .padding(top = contentPadding.calculateTopPadding())
    ) {
        Column(
            modifier = Modifier
                .padding(
                    bottom = contentPadding.calculateTopPadding(),
                    start = contentPadding.calculateStartPadding(layoutDirection),
                    end = contentPadding.calculateEndPadding(layoutDirection)
                )
        ) {
            Box {
                Box {
                    Image(
                        painter = painterResource(selectedPlace.headerImageId),
                        contentDescription = null,
                        alignment = Alignment.TopCenter,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Column(
                    Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, MaterialTheme.colorScheme.scrim),
                                0f,
                                400f
                            )
                        )
                ) {
                    Text(
                        text = stringResource(selectedPlace.titleId),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                        modifier = Modifier
                            .padding(horizontal = dimensionResource(R.dimen.padding_small))
                    )
                    Spacer(Modifier.weight(0.1f))
                    Row(
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                    ) {
                        Text(
                            text = "Typical Group Size: ${selectedPlace.typicalGroupSize.toString()}",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                        )
                        Spacer(Modifier.weight(1f))

                        if (selectedPlace.free) {
                            Text(
                                text = "FREE",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.inverseOnSurface
                            )
                        }

                    }
                }
            }
            Text(
                text = stringResource(selectedPlace.placeDetails),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(
                    vertical = dimensionResource(R.dimen.padding_detail_content_vertical),
                    horizontal = dimensionResource(R.dimen.padding_detail_content_horizontal)
                )
            )
        }
    }
}









@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MedellinTourGuideTheme {
        Guide(windowSize = WindowWidthSizeClass.Compact)
    }
}