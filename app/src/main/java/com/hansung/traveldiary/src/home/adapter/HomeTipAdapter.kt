package com.hansung.traveldiary.src.home.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hansung.traveldiary.databinding.ItemTipBinding
import com.hansung.traveldiary.src.home.GimpoAirportTipActivity
import com.hansung.traveldiary.src.home.TipData


class HomeTipAdapter(private val cautionData: ArrayList<TipData>) : RecyclerView.Adapter<HomeTipAdapter.ViewHolder>() {
    private lateinit var binding : ItemTipBinding

    class ViewHolder(val binding: ItemTipBinding) : RecyclerView.ViewHolder(binding.root) {
        val cautionImage : ImageView
        val cautionContent : TextView

        init {
            cautionImage = binding.cautionImage
            cautionContent = binding.cautionContent
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val binding = ItemTipBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = cautionData[position]

        Glide.with(holder.itemView.context).load(data.image).apply(RequestOptions()).into(holder.cautionImage)
        holder.cautionContent.text = data.content

        holder.itemView.setOnClickListener {
            var intent = Intent(it.context, GimpoAirportTipActivity::class.java)
            startActivity(it.context, intent, null)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = cautionData.size

}