package com.core.feature.foodview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodNutrientsViewModel @Inject constructor(
    private val foodRepository: com.mfs.core.domain.repository.FoodRepository
): ViewModel() {

    private val _food = MutableLiveData<FoodNutrientsUI>(FoodNutrientsUI.SelectFood)
    val food: LiveData<FoodNutrientsUI> = _food

    fun fetchFood(foodId: Int) {
        val foodExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            // Handle food fetch exceptions
            Log.d("FoodNutrientsViewModel", "Error fetching food: $throwable")
        }

        viewModelScope.launch(Dispatchers.IO + foodExceptionHandler) {
            if (foodId == -1) {
                _food.postValue(FoodNutrientsUI.SelectFood)
            } else {
                _food.postValue(FoodNutrientsUI.Loading)
                val food = foodRepository.getFoodById(foodId)
                _food.postValue(FoodNutrientsUI.ShowFood(food))
            }
        }
    }
}
