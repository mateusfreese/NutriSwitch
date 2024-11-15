package com.mfs.feature.foodlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mfs.feature.foodlist.databinding.ItemFoodBinding

class FoodAdapter(
    var onItemClicked: (FoodUi) -> Unit = {}
): ListAdapter<FoodUi, FoodAdapter.FoodViewHolder>(FoodDiffCallback) {

    private object FoodDiffCallback: DiffUtil.ItemCallback<FoodUi>() {
        override fun areItemsTheSame(oldItem: FoodUi, newItem: FoodUi): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: FoodUi, newItem: FoodUi): Boolean {
            return oldItem == newItem
        }

    }

    inner class FoodViewHolder(private val foodView: ItemFoodBinding) : ViewHolder(foodView.root) {
        fun bind(foodUi: FoodUi) {
            foodView.tvFoodName.text = foodUi.name
            foodView.root.setOnClickListener {
                onItemClicked(foodUi)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        return FoodViewHolder(
            ItemFoodBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
