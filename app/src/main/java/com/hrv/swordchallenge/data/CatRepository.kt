package com.hrv.swordchallenge.data

import com.hrv.swordchallenge.data.api.CatApi
import com.hrv.swordchallenge.data.model.CatImage
import com.hrv.swordchallenge.ui.model.CatBreedUIModel
import com.hrv.swordchallenge.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CatRepository(
    private val api: CatApi,
) {

    fun getCatBreeds(): Flow<Resource<List<CatBreedUIModel>>> = flow {
        emit(Resource.Loading())
        try {
            val breeds = api.getCatBreeds()
            val breedsWithImages = breeds.map { breed ->
                var image: CatImage? = null
                if (breed.reference_image_id.isNullOrEmpty().not()) {
                    image = api.getCatImage(breed.reference_image_id ?: "")
                }

                CatBreedUIModel(
                    id = breed.id,
                    name = breed.name,
                    temperament = breed.temperament,
                    origin = breed.origin,
                    countryCode = breed.country_code,
                    description = breed.description,
                    imageUrl = image?.url
                )
            }
            emit(Resource.Success(breedsWithImages))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
        }
    }
}


