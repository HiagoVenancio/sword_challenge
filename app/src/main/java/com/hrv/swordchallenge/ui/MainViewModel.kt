package com.hrv.swordchallenge.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrv.swordchallenge.data.CatRepository
import com.hrv.swordchallenge.ui.model.CatBreedUIModel
import com.hrv.swordchallenge.util.Resource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CatRepository) : ViewModel() {
    private val _catBreeds = MutableStateFlow<Resource<List<CatBreedUIModel>>>(Resource.Loading())
    val catBreeds: StateFlow<Resource<List<CatBreedUIModel>>> get() = _catBreeds

    private val _favorites = MutableStateFlow<Set<String>>(emptySet())
    val favorites: StateFlow<Set<String>> get() = _favorites

    private var currentPage = 0

    init {
        fetchCatBreeds()
    }

    private fun fetchCatBreeds() {
        viewModelScope.launch {
            repository.getCatBreeds().collect { resource ->
                if (resource is Resource.Success) {
                    val breeds = resource.data?.map { breed ->
                        breed.copy(isFavorite = _favorites.value.contains(breed.id))
                    }
                    _catBreeds.value = Resource.Success(breeds ?: emptyList())
                } else {
                    _catBreeds.value = resource
                }
            }
        }
    }

    fun loadMoreCatBreeds() {
        Log.e("asdasd", "Chamei")
        viewModelScope.launch {
            currentPage++
            repository.loadMoreCatBreeds(currentPage).collect { resource ->
                if (resource is Resource.Success) {
                    val currentBreeds = (_catBreeds.value as? Resource.Success)?.data ?: emptyList()
                    val updatedBreeds = currentBreeds + (resource.data ?: emptyList())
                    _catBreeds.value = Resource.Success(updatedBreeds)
                } else {
                    _catBreeds.value = resource
                }
            }
        }
    }

    fun toggleFavorite(breed: CatBreedUIModel) {
        _favorites.update { currentFavorites ->
            if (currentFavorites.contains(breed.id)) {
                currentFavorites - breed.id
            } else {
                currentFavorites + breed.id
            }
        }
        _catBreeds.update { resource ->
            if (resource is Resource.Success) {
                Resource.Success(resource.data?.map {
                    if (it.id == breed.id) it.copy(isFavorite = !it.isFavorite) else it
                } ?: emptyList())
            } else {
                resource
            }
        }
    }

    fun isFavorite(breed: CatBreedUIModel): Boolean {
        return _favorites.value.contains(breed.id)
    }
}
