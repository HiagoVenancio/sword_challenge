package com.hrv.swordchallenge.data.api

import com.hrv.swordchallenge.data.model.CatBreed
import retrofit2.http.GET
import retrofit2.http.Headers

interface CatApi {

    @Headers("x-api-key: live_I7o5DrxsMrTKhA8qOCHpTUsWDDpC7nUDcA1rn6bIWjYsWg02TJ7UAv5iOIsTxYA5")
    @GET("v1/breeds")
    suspend fun getCatBreeds(): List<CatBreed>
}