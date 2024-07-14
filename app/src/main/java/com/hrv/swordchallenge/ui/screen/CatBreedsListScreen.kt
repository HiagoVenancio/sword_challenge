package com.hrv.swordchallenge.ui.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.hrv.swordchallenge.ui.MainViewModel
import com.hrv.swordchallenge.ui.model.CatBreedUIModel
import com.hrv.swordchallenge.ui.navigation.Screen
import com.hrv.swordchallenge.util.Resource
import kotlinx.coroutines.launch

@Composable
fun CatBreedsListScreen(navController: NavController, viewModel: MainViewModel) {
    val catBreeds by viewModel.catBreeds.collectAsState()
    val errorMessage by viewModel.message.collectAsState()
    val listState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    var searchQuery by remember { mutableStateOf("") }
    val loading by viewModel.loading.collectAsState()


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            Row(modifier = Modifier.padding(16.dp)) {
                TextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        if (searchQuery.isEmpty() || it.isEmpty()) {
                            viewModel.refreshCatBreeds(true)
                        }
                    },
                    label = { Text("Search breeds") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    if (searchQuery.isEmpty()) {
                        viewModel.refreshCatBreeds(true)
                    } else {
                        viewModel.updateSearchQuery(searchQuery)
                    }
                }) {
                    Text("Search")
                }
            }

            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = loading),
                onRefresh = {
                    coroutineScope.launch {
                        viewModel.refreshCatBreeds()
                    }
                }
            ) {

                when (catBreeds) {
                    is Resource.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            if (loading.not()) CircularProgressIndicator()
                        }
                    }

                    is Resource.Success -> {
                        LazyVerticalGrid(
                            state = listState,
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(16.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(catBreeds.data ?: emptyList()) { breed ->
                                CatBreedItem(
                                    breed = breed,
                                    onClick = {
                                        navController.navigate(Screen.CatBreedDetail.route + "/${breed.id}")
                                    },
                                    onFavoriteToggle = {
                                        viewModel.toggleFavorite(breed)
                                    },
                                    isFavorite = breed.isFavorite
                                )
                            }
                        }

                        listState.OnBottomReached {
                            viewModel.loadMoreCatBreeds()
                        }
                    }

                    is Resource.Error -> {}
                    is Resource.Message -> {}
                }
                if (errorMessage.isNullOrEmpty().not()) {
                    Toast.makeText(
                        navController.context,
                        errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}

@Composable
fun CatBreedItem(
    breed: CatBreedUIModel,
    onClick: () -> Unit,
    onFavoriteToggle: () -> Unit,
    isFavorite: Boolean
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Image(
            painter = rememberImagePainter(data = breed.imageUrl),
            contentDescription = breed.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = breed.name,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = if (isFavorite) "Unfavorite" else "Favorite",
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onFavoriteToggle()
                    }
            )
        }
    }
}

@Composable
fun LazyGridState.OnBottomReached(
    buffer: Int = 2,
    loadMore: () -> Unit
) {
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem =
                layoutInfo.visibleItemsInfo.lastOrNull() ?: return@derivedStateOf false
            lastVisibleItem.index >= layoutInfo.totalItemsCount - buffer
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value) {
            loadMore()
        }
    }
}
