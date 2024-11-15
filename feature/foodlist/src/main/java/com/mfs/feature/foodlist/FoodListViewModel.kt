package com.mfs.feature.foodlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mfs.core.model.Food
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel @Inject constructor(
    private val foodRepository: com.mfs.core.domain.repository.FoodRepository
): ViewModel() {

    private val _foodList = MutableLiveData<List<FoodUi>>()
    val foodList: LiveData<List<FoodUi>> = _foodList

    fun fetchFoods() {
        viewModelScope.launch(Dispatchers.IO) {
            _foodList.postValue(foodRepository.getAllFoods().map(Food::toUi))
        }
    }

    fun searchFoods(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (query.isBlank()) {
                fetchFoods()
            } else {
                _foodList.postValue(foodRepository.searchFoods(query).map(Food::toUi))
            }
        }
    }

}
