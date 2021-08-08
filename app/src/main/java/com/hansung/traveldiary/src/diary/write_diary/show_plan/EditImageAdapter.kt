package com.hansung.traveldiary.src.diary.write_diary.show_plan

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hansung.traveldiary.databinding.ItemEditDiaryImageBinding
import com.hansung.traveldiary.src.diary.write_diary.CountViewModel

class EditImageAdapter(val imageArrayList: ArrayList<String>, val countViewModel: CountViewModel) : RecyclerView.Adapter<EditImageAdapter.ViewHolder>() {
    class ViewHolder(val binding : ItemEditDiaryImageBinding) : RecyclerView.ViewHolder(binding.root) {
        var image : ImageView = binding.itemEditDiaryImage
        var deletebtn : LinearLayout = binding.deleteBtn
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEditDiaryImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        Glide.with(context).load(imageArrayList[position]).into(holder.image)

        holder.deletebtn.setOnClickListener {
            imageArrayList.removeAt(position)
            countViewModel.countDown()
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = imageArrayList.size
}