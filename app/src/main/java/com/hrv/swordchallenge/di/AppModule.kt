package com.hrv.swordchallenge.di

import androidx.room.Room
import com.hrv.swordchallenge.data.AppDatabase
import com.hrv.swordchallenge.data.CatRepository
import com.hrv.swordchallenge.data.api.CatApi
import com.hrv.swordchallenge.ui.MainViewModel
import org.koin.android.ext.koin.androidContext
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

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, "cat_database"
        ).build()
    }

    single { get<AppDatabase>().catBreedDao() }

    single { CatRepository(get(), get()) }

    viewModel { MainViewModel(get()) }
}
