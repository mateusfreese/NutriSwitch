package com.mfs.core.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mfs.core.model.Food

@Entity(
    tableName = "food"
)
data class FoodEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "calories") val calories: Double?,
    @ColumnInfo(name = "carbohydrate") val carbohydrate: Double?,
    @ColumnInfo(name = "protein") val protein: Double?,
    @ColumnInfo(name = "fat") val fat: Double?,
)

fun FoodEntity.asModel() = Food(name = this.name)
