package com.hrv.swordchallenge.ui.model

data class CatBreedUIModel(
    val id: String,
    val name: String,
    val temperament: String,
    val origin: String,
    val countryCode: String,
    val description: String,
    val imageUrl: String?,
    val isFavorite: Boolean = false
)
