package com.hrv.swordchallenge.data.api

import com.hrv.swordchallenge.data.model.CatBreed
import com.hrv.swordchallenge.data.model.CatImage
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface CatApi {

    @Headers("x-api-key: live_I7o5DrxsMrTKhA8qOCHpTUsWDDpC7nUDcA1rn6bIWjYsWg02TJ7UAv5iOIsTxYA5")
    @GET("v1/breeds")
    suspend fun getCatBreeds(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): List<CatBreed>?

    @Headers("x-api-key: live_I7o5DrxsMrTKhA8qOCHpTUsWDDpC7nUDcA1rn6bIWjYsWg02TJ7UAv5iOIsTxYA5")
    @GET("v1/images/{image_id}")
    suspend fun getCatImage(@Path("image_id") imageId: String): CatImage

}