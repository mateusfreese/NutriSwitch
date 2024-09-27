package com.mfs.core.model

data class Food(
    val id: Int,
    val name: String,
    val calories: Double?,
    val carbohydrate: Double?,
    val protein: Double?,
    val fat: Double?,
)