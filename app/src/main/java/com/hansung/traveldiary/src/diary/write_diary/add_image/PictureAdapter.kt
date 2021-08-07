package com.hansung.traveldiary.src.diary.write_diary.add_image

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hansung.traveldiary.databinding.ItemGalleryBinding
import com.hansung.traveldiary.src.diary.write_diary.DiaryImageEditActivity
import com.hansung.traveldiary.src.diary.write_diary.EditImageAdapter
import com.hansung.traveldiary.src.profile.edit_info.EditInfoActivity
import com.hansung.traveldiary.src.profile.gallery.SelectPictureActivity

class PictureAdapter(val context: Context, val images: ArrayList<String>) :
    RecyclerView.Adapter<PictureAdapter.ViewHolder>() {

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
            val intent = Intent(holder.itemView.context, DiaryImageEditActivity::class.java).apply{
                putExtra("imagePath", images[position])
            }
            (holder.itemView.context as AddPictureActivity).setResult(Activity.RESULT_OK, intent)
            (holder.itemView.context as AddPictureActivity).finish()
        }
    }

    override fun getItemCount(): Int = images.size
}