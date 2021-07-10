package com.hansung.traveldiary.src.home.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hansung.traveldiary.databinding.ItemSaleBinding

data class SaleData(val image: Drawable, val location: String, val price: String)

class HomeSaleAdapter(private val SaleData: ArrayList<SaleData>) : RecyclerView.Adapter<HomeSaleAdapter.ViewHolder>() {
    private lateinit var binding : ItemSaleBinding

    class ViewHolder(val binding: ItemSaleBinding) : RecyclerView.ViewHolder(binding.root) {
        val image : ImageView
        val price : TextView
        val location : TextView

        init {
            image = binding.saleIvBackground
            price = binding.saleTvPrice
            location = binding.saleTvLocation
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val binding = ItemSaleBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data =SaleData[position]
        Glide.with(holder.itemView.context).load(data.image).into(holder.image)
        holder.price.text = data.price
        holder.location.text = data.location
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = SaleData.size

}