package com.mfs.foodlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel @Inject constructor(): ViewModel() {

    private val _foodList = MutableLiveData<List<String>>()
    val foodList: LiveData<List<String>> = _foodList

    fun fetchFoods() {
        viewModelScope.launch(Dispatchers.IO) {
            _foodList.postValue(listOf("apple", "banana", "orange"))
        }
    }

}
