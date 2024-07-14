package com.hrv.swordchallenge

import com.hrv.swordchallenge.ui.model.CatBreedUIModel

object Mocks {
    val favoriteBreeds = listOf(
        CatBreedUIModel(
            id = "1",
            name = "Breed 1",
            temperament = "Calm",
            origin = "USA",
            countryCode = "US",
            description = "Description",
            imageUrl = "url",
            isFavorite = true
        )
    )

    val initialBreeds = listOf(
        CatBreedUIModel(
            id = "1",
            name = "Breed 1",
            temperament = "Calm",
            origin = "USA",
            countryCode = "US",
            description = "Description",
            imageUrl = "url",
            isFavorite = false
        )
    )
    val newBreeds = listOf(
        CatBreedUIModel(
            id = "2",
            name = "Breed 2",
            temperament = "Energetic",
            origin = "UK",
            countryCode = "GB",
            description = "Description 2",
            imageUrl = "url2",
            isFavorite = false
        )
    )

    val searchResult = listOf(
        CatBreedUIModel(
            id = "2",
            name = "Breed 2",
            temperament = "Energetic",
            origin = "UK",
            countryCode = "GB",
            description = "Description 2",
            imageUrl = "url2",
            isFavorite = false
        )
    )
}
