package com.hrv.swordchallenge.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hrv.swordchallenge.ui.model.CatBreedUIModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CatBreedDao {
    @Query("SELECT * FROM cat_breeds")
    fun getCatBreeds(): Flow<List<CatBreedUIModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(breeds: List<CatBreedUIModel>)

    @Query("DELETE FROM cat_breeds")
    suspend fun deleteAll()

    @Query("UPDATE cat_breeds SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteStatus(id: String, isFavorite: Boolean)

    @Query("SELECT * FROM cat_breeds WHERE isFavorite = 1")
    fun getFavoriteCatBreeds(): Flow<List<CatBreedUIModel>>
}