package com.hrv.swordchallenge.data

import android.util.Log
import com.hrv.swordchallenge.data.api.CatApi
import com.hrv.swordchallenge.data.dao.CatBreedDao
import com.hrv.swordchallenge.data.model.CatImage
import com.hrv.swordchallenge.ui.model.CatBreedUIModel
import com.hrv.swordchallenge.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class CatRepository(
    private val api: CatApi,
    private val dao: CatBreedDao
) {

    companion object {
        const val LIMIT_SIZE = 15
    }

    fun getCatBreeds(refreshWithLoad: Boolean = false): Flow<Resource<List<CatBreedUIModel>>> =
        flow {
            if (refreshWithLoad) emit(Resource.Loading())
            try {
                val breeds = api.getCatBreeds(LIMIT_SIZE, 0)
                val breedsWithImages = breeds?.map { breed ->
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
                breedsWithImages?.let {
                    dao.insertAll(breedsWithImages)
                    emit(Resource.Success(dao.getCatBreeds().first()))
                }
            } catch (e: Exception) {
                Log.e("Error", e.localizedMessage ?: "An error occurred")
            }
        }

    fun loadMoreCatBreeds(page: Int): Flow<Resource<List<CatBreedUIModel>>> = flow {
        try {
            val breeds = api.getCatBreeds(LIMIT_SIZE, page)
            if (breeds.isNullOrEmpty()) {
                emit(Resource.Message(null, "No more cats to fetch"))
                return@flow
            }
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

            if (breedsWithImages.isNotEmpty()) {
                dao.insertAll(breedsWithImages)
                val updatedBreeds = dao.getCatBreeds().first()
                emit(Resource.Success(updatedBreeds))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    fun toggleFavorite(catBreed: CatBreedUIModel) = flow {
        try {
            dao.updateFavoriteStatus(catBreed.id, !catBreed.isFavorite)
            val updatedBreeds = dao.getCatBreeds().first()
            emit(Resource.Success(updatedBreeds))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    fun getFavoriteCatBreeds(): Flow<Resource<List<CatBreedUIModel>>> = flow {
        emit(Resource.Loading())
        try {
            val favorites = dao.getFavoriteCatBreeds().first()
            emit(Resource.Success(favorites))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
        }
    }
}
