package com.hrv.swordchallenge.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hrv.swordchallenge.ui.MainViewModel
import com.hrv.swordchallenge.util.Resource

@Composable
fun FavoriteCatsScreen(navController: NavController, viewModel: MainViewModel) {
    LaunchedEffect(Unit) {
        viewModel.fetchFavoriteCatBreeds()
    }

    val favorites by viewModel.favorites.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when (favorites) {
            is Resource.Loading -> {
                Text(text = "Loading...")
            }

            is Resource.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(favorites.data ?: emptyList()) { breed ->
                        CatBreedItem(
                            breed = breed,
                            onClick = {
                                navController.navigate("cat_breed_detail/${breed.id}")
                            },
                            onFavoriteToggle = {
                                viewModel.toggleFavorite(breed)
                            },
                            isFavorite = breed.isFavorite
                        )
                    }
                }
            }

            is Resource.Error -> {
                Text(text = "Error: ${favorites.message}")
            }
        }
    }
}
