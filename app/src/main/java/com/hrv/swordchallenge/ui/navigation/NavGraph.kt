package com.hrv.swordchallenge.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hrv.swordchallenge.ui.MainViewModel
import com.hrv.swordchallenge.ui.screen.CatBreedDetailScreen
import com.hrv.swordchallenge.ui.screen.CatBreedsListScreen
import com.hrv.swordchallenge.ui.screen.FavoriteCatsScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    viewModel: MainViewModel,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screen.CatBreedsList.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(route = Screen.CatBreedsList.route) {
            CatBreedsListScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            route = Screen.CatBreedDetail.route + "/{breedId}",
            arguments = listOf(navArgument("breedId") { type = NavType.StringType })
        ) { backStackEntry ->
            CatBreedDetailScreen(viewModel = viewModel, backStackEntry = backStackEntry)
        }
        composable(Screen.FavoriteCats.route) {
            FavoriteCatsScreen(navController = navController, viewModel = viewModel)
        }
    }
}