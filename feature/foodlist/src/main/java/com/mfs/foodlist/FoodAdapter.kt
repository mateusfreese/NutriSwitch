package com.mfs.foodlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mfs.foodlist.databinding.ItemFoodBinding

class FoodAdapter(
    private var foodList: List<String> = listOf()
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    fun updateList(newFoodList: List<String>) {
        this.foodList = newFoodList
    }

    inner class FoodViewHolder(private val foodView: ItemFoodBinding) : ViewHolder(foodView.root) {
        fun bind(foodName: String) {
            foodView.tvFoodName.text = foodName
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

    override fun getItemCount(): Int = foodList.size

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(foodList[position])
    }
}