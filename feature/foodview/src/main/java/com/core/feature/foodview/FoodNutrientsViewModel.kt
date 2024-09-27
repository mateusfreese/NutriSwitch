package com.core.feature.foodview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.data.FoodRepository
import com.mfs.core.model.Food
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodNutrientsViewModel @Inject constructor(
    private val foodRepository: FoodRepository
): ViewModel() {

    private val _food = MutableLiveData<Food>()
    val food: LiveData<Food> = _food

    fun fetchFood(foodId: Int) {
        val foodExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            // Handle food fetch exceptions
            Log.d("FoodNutrientsViewModel", "Error fetching food: $throwable")
        }

        viewModelScope.launch(Dispatchers.IO + foodExceptionHandler) {
            _food.postValue(foodRepository.getFoodById(foodId))
        }
    }


}
