package com.mfs.feature.foodconverter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.mfs.core.model.Food
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodNutrientsConverterViewModel @Inject constructor(
    private val foodRepository: com.mfs.core.domain.repository.FoodRepository
) : ViewModel() {

    private val _uiState = MutableLiveData(FoodNutrientsUiState())
    val uiState: LiveData<FoodNutrientsUiState> = _uiState.map {
        updateNutrients(it)
        it
    }

    init {
        fetchFoodList()
    }

    private fun fetchFoodList() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.updateUiState {
                it.copy(foodList = foodRepository.getAllFoods())
            }
        }
    }

    fun setPrimaryFood(food: Food) {
        viewModelScope.launch {
            _uiState.updateUiState {
                it.copy(
                    selectedPrimaryFood = food,
                    selectedSecondaryFoodQuantity = null
                )
            }
        }
    }

    fun setPrimaryFoodQuantity(quantity: Double?) {
        viewModelScope.launch {
            _uiState.updateUiState {
                it.copy(
                    selectedPrimaryFoodQuantity = quantity,
                    selectedSecondaryFoodQuantity = null
                )
            }
        }
    }

    fun setSecondaryFood(food: Food) {
        viewModelScope.launch {
            _uiState.updateUiState {
                it.copy(
                    selectedSecondaryFood = food,
                    selectedSecondaryFoodQuantity = null
                )
            }
        }
    }

    fun setSecondaryFoodQuantity(quantity: Double?) {
        viewModelScope.launch {
            _uiState.updateUiState {
                it.copy(
                    selectedSecondaryFoodQuantity = quantity,
                    selectedPrimaryFoodQuantity = null
                )
            }
        }
    }

    private fun updateNutrients(newUiState: FoodNutrientsUiState) = newUiState.apply {
        val primaryFood = selectedPrimaryFood ?: return@apply
        val secondaryFood = selectedSecondaryFood ?: return@apply

        when {
            selectedPrimaryFoodQuantity != null && selectedSecondaryFoodQuantity == null -> {
                _uiState.updateUiState {
                    it.copy(
                        selectedSecondaryFoodQuantity = convertFoodQuantityByKcal(
                            primaryFood,
                            secondaryFood,
                            selectedPrimaryFoodQuantity
                        )
                    )
                }
            }

            selectedPrimaryFoodQuantity == null && selectedSecondaryFoodQuantity != null -> {
                _uiState.updateUiState {
                    it.copy(
                        selectedPrimaryFoodQuantity = convertFoodQuantityByKcal(
                            secondaryFood,
                            primaryFood,
                            selectedSecondaryFoodQuantity
                        )
                    )
                }
            }
        }
    }

    private fun convertFoodQuantityByKcal(
        primaryFood: Food,
        secondaryFood: Food,
        primaryQuantity: Double
    ): Double {
        return (primaryFood.calories ?: 0.0) * primaryQuantity / (secondaryFood.calories ?: 0.0)
    }


    private inline fun MutableLiveData<FoodNutrientsUiState>.updateUiState(
        uiStateCallback: (FoodNutrientsUiState) -> FoodNutrientsUiState
    ) {
        this@updateUiState.postValue(
            uiStateCallback(
                this@updateUiState.value ?: FoodNutrientsUiState()
            )
        )
    }
}
