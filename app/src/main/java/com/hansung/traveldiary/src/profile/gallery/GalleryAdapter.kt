package com.hansung.traveldiary.src.profile.gallery

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hansung.traveldiary.databinding.ItemGalleryBinding
import com.hansung.traveldiary.databinding.ItemHomebulletinBinding
import com.hansung.traveldiary.src.home.adapter.HomeBulletinAdapter

class GalleryAdapter(val context: Context, val images: ArrayList<String>) :
    RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemGalleryBinding) : RecyclerView.ViewHolder(binding.root) {
        val image : ImageView

        init{
            image = binding.image
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(images[position]).into(holder.image)

        holder.itemView.setOnClickListener{
            //TODO 클릭
            Log.d("확인", images[position].toString())
        }
    }

    override fun getItemCount(): Int = images.size
}