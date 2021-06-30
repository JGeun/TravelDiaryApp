package com.hansung.traveldiary.src.bulletin

import android.content.Intent
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.hansung.traveldiary.databinding.ItemListBinding

class CustomAdapter(private val viewModel: ArrayList<PosData>):RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemListBinding):RecyclerView.ViewHolder(binding.root){
        fun setContents(position: Int){
            with(viewModel[position]){
                binding.ivProfile.setImageResource(viewModel.get(position).iv_profile)
                binding.Contents.text=tv_Contents
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        val binding= ItemListBinding.inflate(layoutInflater,parent,false)
        binding.ivEheart.setOnClickListener{
            println("asdasda")
        }

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setContents(position)
        holder.itemView.setTag(position)
       holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView?.context,PostActivity::class.java)
            ContextCompat.startActivity(holder.itemView.context,intent,null)
            println("aaaaaaaaa")
        }
    }

    override fun getItemCount(): Int {
        return viewModel.size
    }

}