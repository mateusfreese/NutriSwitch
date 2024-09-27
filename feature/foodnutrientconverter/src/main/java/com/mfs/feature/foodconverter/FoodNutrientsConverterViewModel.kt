package com.mfs.feature.foodconverter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.data.FoodRepository
import com.mfs.core.model.Food
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodNutrientsConverterViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : ViewModel() {

    private val _foodList = MutableLiveData<List<Food>>()
    val foodList: LiveData<List<Food>> = _foodList

    private val _selectedPrimaryFood = MutableLiveData<Food?>()
    val selectedPrimaryFood: LiveData<Food?> = _selectedPrimaryFood

    private val _selectedPrimaryFoodQuantity = MutableLiveData<Double?>()
    val selectedPrimaryFoodQuantity: LiveData<Double?> = _selectedPrimaryFoodQuantity

    private val _selectedSecondaryFood = MutableLiveData<Food?>()
    val selectedSecondaryFood: LiveData<Food?> = _selectedSecondaryFood

    private val _selectedSecondaryFoodQuantity = MutableLiveData<Double?>()
    val selectedSecondaryFoodQuantity: LiveData<Double?> = _selectedSecondaryFoodQuantity

    val mediatorLiveData = MediatorLiveData<Any>()

    init {
        fetchFoodList()

        mediatorLiveData.apply {
            addSource(selectedPrimaryFood) {
                updateNutrients()
            }
            addSource(selectedPrimaryFoodQuantity) {
                updateNutrients()
            }
            addSource(selectedSecondaryFood) {
                updateNutrients()
            }
            addSource(selectedSecondaryFoodQuantity) {
                updateNutrients()
            }
        }
    }

    private fun fetchFoodList() {
        viewModelScope.launch(Dispatchers.IO) {
            _foodList.postValue(foodRepository.getAllFoods())
        }
    }

    fun setPrimaryFood(food: Food) {
        viewModelScope.launch {
            _selectedPrimaryFood.postValue(food)
        }
    }

    fun setPrimaryFoodQuantity(quantity: Double?) {
        _selectedPrimaryFoodQuantity.value = quantity
        _selectedSecondaryFoodQuantity.value = null
    }

    fun setSecondaryFood(food: Food) {
        viewModelScope.launch {
            _selectedSecondaryFood.postValue(food)
        }
    }

    fun setSecondaryFoodQuantity(quantity: Double?) {
        viewModelScope.launch {
            _selectedSecondaryFoodQuantity.postValue(quantity)
            _selectedPrimaryFoodQuantity.postValue(null)
        }
    }

    private fun updateNutrients() {
        val primaryFood = selectedPrimaryFood.value ?: return
        val secondaryFood = selectedSecondaryFood.value ?: return
        val primaryQuantity = selectedPrimaryFoodQuantity.value
        val secondaryQuantity = selectedSecondaryFoodQuantity.value

        when {
            primaryQuantity != null && secondaryQuantity == null -> {
                _selectedSecondaryFoodQuantity.postValue(
                    convertFoodQuantityByKcal(
                        primaryFood,
                        secondaryFood,
                        primaryQuantity
                    )
                )
            }

            primaryQuantity == null && secondaryQuantity != null -> {
                _selectedPrimaryFoodQuantity.postValue(
                    convertFoodQuantityByKcal(
                        secondaryFood,
                        primaryFood,
                        secondaryQuantity
                    )
                )
            }
        }
    }

    private fun convertFoodQuantityByKcal(
        primaryFood: Food,
        secondaryFood: Food,
        primaryQuantity: Double
    ): Double {
        fun Food.kcalPerGram() = calories?.div(100) ?: 0.0

        val primaryFoodTotalKcal = primaryFood.kcalPerGram() * primaryQuantity

        return primaryFoodTotalKcal / secondaryFood.kcalPerGram()
    }
}
