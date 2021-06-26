package com.hansung.traveldiary.src.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityMainBinding.inflate
import com.hansung.traveldiary.databinding.ItemRecommandBinding
import com.hansung.traveldiary.src.home.RecommandLocationData

class RecommandAdapter(private val locationData: ArrayList<RecommandLocationData>) : RecyclerView.Adapter<RecommandAdapter.ViewHolder>() {
    private lateinit var binding : ItemRecommandBinding

    class ViewHolder(val binding: ItemRecommandBinding) : RecyclerView.ViewHolder(binding.root) {
        val locationImage : ImageView
        val locationName: TextView

        init {
            // Define click listener for the ViewHolder's View.
            locationImage = binding.itemImage
            locationName = binding.itemLocation
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val binding = ItemRecommandBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(locationData[position].image).apply(RequestOptions().circleCrop()).into(holder.locationImage)
        holder.locationName.text = locationData[position].name
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = locationData.size

}