package com.hrv.swordchallenge.ui.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hrv.swordchallenge.ui.model.CatBreedUIModel.Companion.CAT_BREED_TABLE

@Entity(tableName = CAT_BREED_TABLE)
data class CatBreedUIModel(
    @PrimaryKey val id: String,
    val name: String,
    val temperament: String,
    val origin: String,
    val countryCode: String,
    val description: String,
    val imageUrl: String?,
    var isFavorite: Boolean = false
) {
    companion object {
        const val CAT_BREED_TABLE = "cat_breeds"
    }
}
