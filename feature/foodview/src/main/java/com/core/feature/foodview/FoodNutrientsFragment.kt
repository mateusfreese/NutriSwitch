package com.core.feature.foodview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mfs.core.model.Food
import com.mfs.feature.foodview.R
import com.mfs.feature.foodview.databinding.FragmentFoodNutrientsBinding
import com.mfs.feature.foodview.databinding.NutrientItemBinding
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
        setupView()
        setupObservables()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchFood(foodNavArgs.foodId)
    }

    private fun setupView() = with(binding) {
        btnSelectFood.setOnClickListener {
            findNavController().navigate(R.id.food_list)
        }
    }

    private fun setupObservables() {
        viewModel.food.observe(viewLifecycleOwner, ::handleFood)
    }

    private fun handleFood(foodNutrientsUI: FoodNutrientsUI) = binding.apply {
        when (foodNutrientsUI) {
            is FoodNutrientsUI.Loading -> showLoadingContainer()
            is FoodNutrientsUI.SelectFood -> showSelectFoodContainer()
            is FoodNutrientsUI.ShowFood -> showFood(foodNutrientsUI.food)
        }
    }

    private fun showLoadingContainer() = with(binding) {
        llLoadingContainer.visibility = View.VISIBLE
    }

    private fun showSelectFoodContainer() = with(binding) {
        llLoadingContainer.visibility = View.GONE
        llFoodContainer.visibility = View.GONE

        llSelectFood.visibility = View.VISIBLE
    }

    private fun showFoodContainer() = with(binding) {
        llLoadingContainer.visibility = View.GONE
        llSelectFood.visibility = View.GONE

        llFoodContainer.visibility = View.VISIBLE
    }

    private fun showFood(food: Food) = with(binding) {
        showFoodContainer()

        val nutrients = listOf(
            Triple(R.string.calories, food.calories, R.string.amount_kcal),
            Triple(R.string.protein, food.protein, R.string.amount_grams),
            Triple(R.string.carbohydrate, food.carbohydrate, R.string.amount_grams),
            Triple(R.string.fat, food.fat, R.string.amount_grams)
        )

        tvName.text = food.name
        lvMacronutrients.adapter = object : BaseAdapter() {
            override fun getCount(): Int = nutrients.size

            override fun getItem(position: Int): Any {
                return nutrients[position]
            }

            override fun getItemId(position: Int): Long {
                return nutrients[position].first.toLong()
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                return convertView ?: NutrientItemBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                ).bindNutrientItem(
                    nutrients[position]
                ).root
            }

            fun NutrientItemBinding.bindNutrientItem(
                nutrient: Triple<Int, Double?, Int>
            ): NutrientItemBinding {
                return this@bindNutrientItem.apply {
                    tvNutrientName.setText(nutrient.first)
                    tvNutrientValue.text = getString(nutrient.third, nutrient.second)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}