package com.mfs.feature.foodconverter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mfs.core.model.Food
import com.mfs.feature.foodconverter.databinding.FragmentFoodNutrientsConverterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodNutrientsConverterFragment: Fragment() {

    private var _binding: FragmentFoodNutrientsConverterBinding? = null
    private val binding get() = _binding!!

    private val foodNutrientsConverterViewModel by viewModels<FoodNutrientsConverterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFoodNutrientsConverterBinding
        .inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObservables()
    }

    private fun setupView() = binding.run {
        etPrimaryFoodQuantity.doAfterTextChanged { text ->
            if (etPrimaryFoodQuantity.hasFocus()) {
                foodNutrientsConverterViewModel.setPrimaryFoodQuantity(text.toString().toDoubleOrNull())
            }
        }
        etSecondaryFoodQuantity.doAfterTextChanged { text ->
            if (etSecondaryFoodQuantity.hasFocus()) {
                foodNutrientsConverterViewModel.setSecondaryFoodQuantity(text.toString().toDoubleOrNull())
            }
        }
    }

    private fun setupObservables() = foodNutrientsConverterViewModel.run {
        uiState.observe(viewLifecycleOwner, ::handleUiState)
    }

    private fun handleUiState(uiState: FoodNutrientsUiState) {
        handleFoodList(uiState.foodList)
        handlePrimaryFoodQuantity(uiState.selectedPrimaryFoodQuantity)
        handleSecondaryFoodQuantity(uiState.selectedSecondaryFoodQuantity)

    }

    private fun handleFoodList(foodList: List<Food>?) = binding.run {
        actvPrimaryFoodName.setup(foodList, foodNutrientsConverterViewModel::setPrimaryFood)
        actvSecondaryFoodName.setup(foodList, foodNutrientsConverterViewModel::setSecondaryFood)
    }

    private fun handlePrimaryFoodQuantity(quantity: Double?) = binding.run {
        if (!etPrimaryFoodQuantity.hasFocus()) {
            etPrimaryFoodQuantity.setText(quantity?.toString())
        }
    }

    private fun handleSecondaryFoodQuantity(quantity: Double?) = binding.run {
        if (!etSecondaryFoodQuantity.hasFocus()) {
            etSecondaryFoodQuantity.setText(quantity?.toString())
        }
    }

    private fun AutoCompleteTextView.setup(foodList: List<Food>?, onItemSelected: (Food) -> Unit) {
        foodList ?: return

        val foodNamesList = foodList.map { it.name }
        val foodAdapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, foodNamesList)
        setAdapter(foodAdapter)
        threshold = 1

        setOnItemClickListener { _, _, position, _ ->
            onItemSelected(foodList[position])
        }

        setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                setText(foodList.find { it.name == text.toString() }?.name)
            } else {
                dismissDropDown()
                requestFocus()
                postDelayed({
                    showDropDown()
                }, 500)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}