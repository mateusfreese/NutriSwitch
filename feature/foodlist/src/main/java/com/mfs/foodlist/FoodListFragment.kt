package com.mfs.foodlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mfs.foodlist.databinding.FragmentFoodListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodListFragment : Fragment() {

    private lateinit var binding: FragmentFoodListBinding

    private val viewModel: FoodListViewModel by viewModels()

    private var foodAdapter = FoodAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFoodListBinding.inflate(layoutInflater)
        .apply {
            binding = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservables()
        viewModel.fetchFoods()
    }

    private fun setupRecyclerView() = binding.apply {
        rvFoodList.adapter = foodAdapter
    }

    private fun setupObservables() {
        viewModel.foodList.observe(viewLifecycleOwner) {
            foodAdapter.submitList(it)
        }
    }
}