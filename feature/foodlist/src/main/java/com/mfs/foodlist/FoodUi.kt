package com.mfs.foodlist

import com.mfs.core.model.Food

data class FoodUi(
    val id: Int,
    val name: String
)

fun Food.toUi() = FoodUi(
    id = id,
    name = name
)
