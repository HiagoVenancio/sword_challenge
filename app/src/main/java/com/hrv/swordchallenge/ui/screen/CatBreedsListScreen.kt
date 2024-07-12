package com.hrv.swordchallenge.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.hrv.swordchallenge.ui.MainViewModel
import com.hrv.swordchallenge.ui.navigation.Screen
import com.hrv.swordchallenge.util.Resource

@Composable
fun CatBreedsListScreen(navController: NavController, viewModel: MainViewModel) {
    val catBreeds by viewModel.catBreeds.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when (catBreeds) {
            is Resource.Loading -> {
                // Show loading state
                Text(text = "Loading...")
            }
            is Resource.Success -> {
                LazyColumn {
                    items(catBreeds.data ?: emptyList()) { breed ->
                        Text(
                            text = breed.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(Screen.CatBreedDetail.route + "/${breed.id}")
                                }
                        )
                    }
                }
            }
            is Resource.Error -> {
                // Show error state
                Text(text = "Error: ${catBreeds.message}")
            }
        }
    }
}
