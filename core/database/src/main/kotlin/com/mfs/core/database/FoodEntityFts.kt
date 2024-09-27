package com.mfs.core.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4

@Fts4(contentEntity = FoodEntity::class)
@Entity(tableName = "food_fts")
data class FoodEntityFts(
    @ColumnInfo(name = "name")
    val name: String
)