package com.hrv.swordchallenge.ui.navigation

sealed class Screen(val route: String) {
    data object CatBreedsList : Screen("catBreedsList")
    data object CatBreedDetail : Screen("catBreedDetail")
    data object FavoriteCats : Screen("favorite_cats")
}