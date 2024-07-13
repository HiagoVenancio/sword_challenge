package com.hrv.swordchallenge.data.model

data class CatBreed(
    val id: String,
    val name: String,
    val temperament: String,
    val origin: String,
    val country_code: String,
    val description: String,
    val reference_image_id: String?
)