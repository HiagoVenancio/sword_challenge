package com.hrv.swordchallenge.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrv.swordchallenge.data.CatRepository
import com.hrv.swordchallenge.ui.model.CatBreedUIModel
import com.hrv.swordchallenge.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CatRepository) : ViewModel() {
    private val _catBreeds = MutableStateFlow<Resource<List<CatBreedUIModel>>>(Resource.Loading())
    val catBreeds: StateFlow<Resource<List<CatBreedUIModel>>> get() = _catBreeds

    private val _favorites = MutableStateFlow<Resource<List<CatBreedUIModel>>>(Resource.Loading())
    val favorites: StateFlow<Resource<List<CatBreedUIModel>>> get() = _favorites

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> get() = _message

    private val currentPage = MutableStateFlow<Int>(0)

    init {
        fetchCatBreeds()
        fetchFavoriteCatBreeds()
    }

    private fun fetchCatBreeds() {
        viewModelScope.launch {
            repository.getCatBreeds().collect { resource ->
                _catBreeds.value = resource
            }
        }
    }

    fun refreshCatBreeds() {
        currentPage.value = 0
        _message.value = null
        viewModelScope.launch {
            repository.getCatBreeds().collect { resource ->
                _catBreeds.value = resource
            }
        }
    }

    fun loadMoreCatBreeds() {
        viewModelScope.launch {
            currentPage.value = (currentPage.value + 1)
            repository.loadMoreCatBreeds(currentPage.value).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _message.value = _catBreeds.value.message
                    }

                    is Resource.Success -> {
                        val currentBreeds =
                            (_catBreeds.value as? Resource.Success)?.data ?: emptyList()
                        val newBreeds = resource.data ?: emptyList()

                        if (newBreeds.isEmpty()) {
                            _message.value = "No more cats to fetch"
                        } else {
                            val updatedBreeds = (currentBreeds + newBreeds).distinctBy { it.id }
                            _catBreeds.value = Resource.Success(updatedBreeds)
                        }
                    }

                    is Resource.Message -> {
                        _message.value = _catBreeds.value.message
                    }

                    else -> {}
                }
            }

        }
    }

    fun toggleFavorite(breed: CatBreedUIModel) {
        viewModelScope.launch {
            repository.toggleFavorite(breed).collect { resource ->
                if (resource is Resource.Success) {
                    _catBreeds.update { catBreedsResource ->
                        if (catBreedsResource is Resource.Success) {
                            Resource.Success(catBreedsResource.data?.map {
                                if (it.id == breed.id) it.copy(isFavorite = !it.isFavorite) else it
                            } ?: emptyList())
                        } else {
                            catBreedsResource
                        }
                    }
                    fetchFavoriteCatBreeds()
                }
            }
        }
    }

    fun fetchFavoriteCatBreeds() {
        viewModelScope.launch {
            repository.getFavoriteCatBreeds().collect { resource ->
                _favorites.value = resource
            }
        }
    }

    fun isFavorite(breed: CatBreedUIModel): Boolean {
        return (_favorites.value as? Resource.Success)?.data?.any { it.id == breed.id } ?: false
    }
}
