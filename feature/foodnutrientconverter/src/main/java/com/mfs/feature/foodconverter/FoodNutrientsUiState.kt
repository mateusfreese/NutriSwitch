package com.mfs.feature.foodconverter

import com.mfs.core.model.Food

data class FoodNutrientsUiState(
    val foodList: List<Food>? = null,
    val selectedPrimaryFood: Food? = null,
    val selectedPrimaryFoodQuantity: Double? = null,
    val selectedSecondaryFood: Food? = null,
    val selectedSecondaryFoodQuantity: Double? = null
)