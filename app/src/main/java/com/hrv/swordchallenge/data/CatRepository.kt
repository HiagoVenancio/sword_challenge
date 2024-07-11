package com.hrv.swordchallenge.data

import com.hrv.swordchallenge.data.api.CatApi
import com.hrv.swordchallenge.data.model.CatBreed
import com.hrv.swordchallenge.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CatRepository(
    private val api: CatApi,
) {

    fun getCatBreeds(): Flow<Resource<List<CatBreed>>> = flow {
        emit(Resource.Loading())
        try {
            val breeds = api.getCatBreeds()
            emit(Resource.Success(breeds))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
        }
    }
}