package com.hrv.swordchallenge.di

import com.hrv.swordchallenge.data.CatRepository
import com.hrv.swordchallenge.data.api.CatApi
import com.hrv.swordchallenge.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatApi::class.java)
    }

    single { CatRepository(get()) }

    viewModel { MainViewModel(get()) }
}
