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
import com.hansung.traveldiary.databinding.ItemHomebulletinBinding
import com.hansung.traveldiary.databinding.ItemRecommandBinding
import com.hansung.traveldiary.src.home.HomeBulletinData
import com.hansung.traveldiary.src.home.RecommandLocationData

class HomeBulletinAdapter(private val bulletinData: ArrayList<HomeBulletinData>) : RecyclerView.Adapter<HomeBulletinAdapter.ViewHolder>() {
    private lateinit var binding : ItemRecommandBinding

    class ViewHolder(val binding: ItemHomebulletinBinding) : RecyclerView.ViewHolder(binding.root) {
        val bulletinImage : ImageView
        val bulletinTitle: TextView
        val bulletinContents: TextView

        init {
            // Define click listener for the ViewHolder's View.
            bulletinImage = binding.itemHbImage
            bulletinTitle = binding.itemHbTitle
            bulletinContents = binding.itemHbContents
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val binding = ItemHomebulletinBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(bulletinData[position].image).apply(RequestOptions()).into(holder.bulletinImage)
        holder.bulletinTitle.text = bulletinData[position].title
        holder.bulletinContents.text = bulletinData[position].contents
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = bulletinData.size

}