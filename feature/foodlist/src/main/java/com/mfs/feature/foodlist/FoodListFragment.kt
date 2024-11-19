package com.mfs.feature.foodlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mfs.feature.foodlist.databinding.FragmentFoodListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodListFragment : Fragment() {

    private var _binding: FragmentFoodListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FoodListViewModel by viewModels()

    private var foodAdapter = FoodAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFoodListBinding
        .inflate(layoutInflater)
        .also { _binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupSearch()
        setupObservables()
        viewModel.fetchFoods()
    }

    private fun setupView() = binding.apply {
        rvFoodList.adapter = foodAdapter
        rvFoodListSearch.adapter = foodAdapter
    }

    private fun setupSearch() = with(binding) {
        searchView.editText.doOnTextChanged { text, _, _, _ ->
            viewModel.searchFoods(text.toString())
        }
        searchView.editText.setOnEditorActionListener { textView, _, _ ->
            searchView.hide()
            searchBar.setText(textView.text)

            return@setOnEditorActionListener true
        }
    }

    private fun setupObservables() {
        viewModel.foodList.observe(viewLifecycleOwner) {
            foodAdapter.submitList(it)
            foodAdapter.onItemClicked = { food ->
                findNavController().navigate(
                    R.id.food_detail, bundleOf(
                    "foodId" to food.id
                ))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}