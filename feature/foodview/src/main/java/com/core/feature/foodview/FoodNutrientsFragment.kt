package com.core.feature.foodview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mfs.core.model.Food
import com.mfs.feature.foodview.R
import com.mfs.feature.foodview.databinding.FragmentFoodNutrientsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodNutrientsFragment : Fragment() {

    private var _binding: FragmentFoodNutrientsBinding? = null
    private val binding get() = _binding!!

    private val foodNavArgs: FoodNutrientsFragmentArgs by navArgs<FoodNutrientsFragmentArgs>()

    private val viewModel: FoodNutrientsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFoodNutrientsBinding
        .inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservables()
        viewModel.fetchFood(foodNavArgs.foodId)
    }

    private fun setupObservables() {
        viewModel.food.observe(viewLifecycleOwner, ::handleFood)
    }

    private fun handleFood(food: Food) = binding.apply {
        tvName.text = getString(R.string.name, food.name)
        tvCalories.text = getString(R.string.calories, food.calories)
        tvCarbohydrate.text = getString(R.string.carbohydrate, food.carbohydrate)
        tvProtein.text = getString(R.string.protein, food.protein)
        tvFat.text = getString(R.string.fat, food.fat)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}