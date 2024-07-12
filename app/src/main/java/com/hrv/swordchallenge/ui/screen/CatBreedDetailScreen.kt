package com.hrv.swordchallenge.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import com.hrv.swordchallenge.ui.MainViewModel

@Composable
fun CatBreedDetailScreen(
    viewModel: MainViewModel,
    backStackEntry: NavBackStackEntry
) {
    val breedId = backStackEntry.arguments?.getString("breedId") ?: return
    val catBreeds by viewModel.catBreeds.collectAsState()
    val breed = catBreeds.data?.find { it.id == breedId }

    breed?.let {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                Text(text = "Name: ${it.name}")
                Text(text = "Origin: ${it.origin}")
                Text(text = "Temperament: ${it.temperament}")
                Text(text = "Description: ${it.description}")
            }
        }
    }
}
