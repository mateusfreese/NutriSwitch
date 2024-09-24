package com.mfs.core.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface FoodDao {
    @Query("SELECT * FROM food")
    suspend fun getAll(): List<Food>

    @Query("SELECT * FROM food WHERE name LIKE :name LIMIT 1")
    suspend fun findByName(name: String): Food
}