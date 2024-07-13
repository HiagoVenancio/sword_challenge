package com.hrv.swordchallenge.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hrv.swordchallenge.data.dao.CatBreedDao
import com.hrv.swordchallenge.ui.model.CatBreedUIModel

@Database(entities = [CatBreedUIModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun catBreedDao(): CatBreedDao
}