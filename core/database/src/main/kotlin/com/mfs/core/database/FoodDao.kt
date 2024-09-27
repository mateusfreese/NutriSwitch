package com.mfs.core.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface FoodDao {
    @Query("SELECT * FROM food")
    suspend fun getAll(): List<FoodEntity>

    @Query("SELECT * FROM food WHERE name LIKE :name LIMIT 1")
    suspend fun findByName(name: String): FoodEntity

    @Query("""
        SELECT * 
        FROM food
        JOIN food_fts ON food_fts.name = food.name
        WHERE food_fts MATCH :name
    """)
    suspend fun searchByName(name: String): List<FoodEntity>
}