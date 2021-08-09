package com.hansung.traveldiary.src.bulletin

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ItemBulletinImageBinding
import com.hansung.traveldiary.src.MainActivity

class BulletinViewPagerAdapter(private val index: Int): RecyclerView.Adapter<BulletinViewPagerAdapter.PagerViewHolder>() {

    class PagerViewHolder(val binding : ItemBulletinImageBinding) : RecyclerView.ViewHolder(binding.root) {
        var image : ImageView = binding.image

        var title = binding.btItemTvTitle
        var contents=binding.btItmeTvContents
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val binding = ItemBulletinImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val context = holder.itemView.context
        val data= MainActivity.bulletinDiaryArray[index]

        if(position == 0){
            holder.title.text = data.userDiaryData.baseData.title
            holder.contents.text="아직 미정이예요"
            val mainImagePath = data.userDiaryData.baseData.mainImage
            if(mainImagePath != "") {
                Glide.with(context).load(mainImagePath).apply(RequestOptions().fitCenter()).into(holder.image)
            }else{
                Glide.with(context).load(ResourcesCompat.getDrawable(context.resources,R.drawable.img_beach,null)).apply(RequestOptions().centerCrop()).into(holder.image)
            }
        }else{
            val day = position-1
            holder.title.text = data.userDiaryData.diaryArray[day].diaryInfo.diaryTitle
            holder.contents.text=data.userDiaryData.diaryArray[day].diaryInfo.diaryContents
            println("BulletinView: Title: ${data.userDiaryData.diaryArray[day].diaryInfo.diaryTitle}")
            println("BulletinView: Contents: ${data.userDiaryData.diaryArray[day].diaryInfo.diaryContents}")

            if(data.userDiaryData.diaryArray[day].diaryInfo.imagePathArray.size!=0) {
                val url = data.userDiaryData.diaryArray[day].diaryInfo.imagePathArray[0]
                Glide.with(context).load(url).apply(RequestOptions().fitCenter()).into(holder.image)
            }else{
                Glide.with(context).load(ResourcesCompat.getDrawable(context.resources,R.drawable.img_beach,null)).apply(RequestOptions().centerCrop()).into(holder.image)
            }

        }

        holder.itemView.setOnClickListener{
            println("index: ${position} 넘겨줌")
            val intent = Intent(context,BulletinDaySectionActivity::class.java)
            intent.putExtra("index", index)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = MainActivity.bulletinDiaryArray[index].userDiaryData.diaryArray.size+1
}