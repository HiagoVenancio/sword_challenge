package com.hrv.swordchallenge.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrv.swordchallenge.data.CatRepository
import com.hrv.swordchallenge.data.model.CatBreed
import com.hrv.swordchallenge.util.Resource
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(repository: CatRepository) : ViewModel() {

    val catBreeds: StateFlow<Resource<List<CatBreed>>> = repository.getCatBreeds().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        Resource.Loading()
    )
}