package com.core.data

import com.mfs.core.model.Food

interface FoodRepository {

    suspend fun getAllFoods(): List<Food>
}