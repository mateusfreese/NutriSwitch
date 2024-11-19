package com.core.feature.foodview

import com.mfs.core.model.Food

sealed interface FoodNutrientsUI {

    data object Loading : FoodNutrientsUI
    data object SelectFood : FoodNutrientsUI
    data class ShowFood(val food: Food) : FoodNutrientsUI
}