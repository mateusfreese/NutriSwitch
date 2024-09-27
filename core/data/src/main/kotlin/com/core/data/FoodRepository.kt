package com.core.data

import com.mfs.core.model.Food

interface FoodRepository {

    suspend fun getAllFoods(): List<Food>

    suspend fun searchFoods(query: String): List<Food>

    suspend fun getFoodById(foodId: Int): Food
}