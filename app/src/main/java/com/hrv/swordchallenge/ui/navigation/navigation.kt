package com.hrv.swordchallenge.ui.navigation

sealed class Screen(val route: String) {
    object CatBreedsList : Screen("catBreedsList")
    object CatBreedDetail : Screen("catBreedDetail")
}