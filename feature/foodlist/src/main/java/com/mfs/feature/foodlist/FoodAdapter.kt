package com.mfs.feature.foodlist

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class FoodAdapter(
    var onItemClicked: (FoodUi) -> Unit = {}
) : ListAdapter<FoodUi, FoodAdapter.FoodViewHolder>(FoodDiffCallback) {

    private object FoodDiffCallback : DiffUtil.ItemCallback<FoodUi>() {
        override fun areItemsTheSame(oldItem: FoodUi, newItem: FoodUi): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: FoodUi, newItem: FoodUi): Boolean {
            return oldItem == newItem
        }

    }

    inner class FoodViewHolder(private val foodView: View) : ViewHolder(foodView) {
        fun bind(foodUi: FoodUi) = (foodView as TextView).run {
            text = foodUi.name
            setOnClickListener {
                onItemClicked(foodUi)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val displayItem = TextView
            .inflate(parent.context, android.R.layout.simple_list_item_1, null)
        return FoodViewHolder(displayItem)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
