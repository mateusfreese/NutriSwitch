package com.core.data

import com.mfs.core.database.FoodDao
import com.mfs.core.database.FoodEntity
import com.mfs.core.database.asModel
import com.mfs.core.model.Food
import javax.inject.Inject

class FoodDataSource @Inject constructor(
    private val foodDao: FoodDao
) : FoodRepository {
    override suspend fun getAllFoods(): List<Food> {
        return foodDao.getAll().map(FoodEntity::asModel)
    }

    override suspend fun searchFoods(query: String): List<Food> {
        return foodDao.searchByName(query).map(FoodEntity::asModel)
    }
}