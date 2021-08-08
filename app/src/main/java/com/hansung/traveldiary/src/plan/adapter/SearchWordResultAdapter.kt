package com.hansung.traveldiary.src.plan.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ItemWordSearchResultBinding
import com.hansung.traveldiary.src.plan.SearchWordResultActivity
import com.hansung.traveldiary.src.plan.TravelPlanMapFragment
import com.hansung.traveldiary.src.plan.model.SearchWordResultInfo
import com.hansung.traveldiary.src.profile.edit_info.EditInfoActivity
import com.hansung.traveldiary.src.profile.gallery.SelectPictureActivity

class SearchWordResultAdapter(private val dataList:ArrayList<SearchWordResultInfo>): RecyclerView.Adapter<SearchWordResultAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemWordSearchResultBinding): RecyclerView.ViewHolder(binding.root){
        var title = binding.itemWsrTitle
        var address = binding.itemWsrAddress
        var place_image=binding.placeImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val binding= ItemWordSearchResultBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.title.text = data.title
        holder.address.text = data.address
        val context = holder.itemView.context
        val place_code_key=data.groupCode
        //장소에 따른 이미지 설정
        if(data.place_code.containsKey(place_code_key)) {
            if(place_code_key.equals("MT1")){
                holder.place_image.setImageResource(R.drawable.ic_market)
            }else if(place_code_key.equals("CS2"))
            {
                holder.place_image.setImageResource(R.drawable.ic_convenience_store)
            }else if(place_code_key.equals("PS3"))
            {
                holder.place_image.setImageResource(R.drawable.ic_kindergarten)
            }else if(place_code_key.equals("SC4"))
            {
                holder.place_image.setImageResource(R.drawable.ic_school)
            }else if(place_code_key.equals("AC5"))
            {
                holder.place_image.setImageResource(R.drawable.ic_convenience_store)
            }else if(place_code_key.equals("PK6"))
            {
                holder.place_image.setImageResource(R.drawable.ic_parkingarea)
            }else if(place_code_key.equals("OL7"))
            {
                holder.place_image.setImageResource(R.drawable.ic_oil_station)
            }else if(place_code_key.equals("SW8"))
            {
                holder.place_image.setImageResource(R.drawable.ic_subway)
            }else if(place_code_key.equals("BK9"))
            {
                holder.place_image.setImageResource(R.drawable.ic_bank)
            }else if(place_code_key.equals("AG2"))
            {
                holder.place_image.setImageResource(R.drawable.ic_academy)
            }else if(place_code_key.equals("AD5"))
            {
                holder.place_image.setImageResource(R.drawable.ic_hotel)
            }else if(place_code_key.equals("FD6"))
            {
                holder.place_image.setImageResource(R.drawable.ic_restaurant)
            }else if(place_code_key.equals("AT4"))
            {
                holder.place_image.setImageResource(R.drawable.ic_tourist_attraction)
            }else if(place_code_key.equals("CS2"))
            {
                holder.place_image.setImageResource(R.drawable.ic_coffee)
            }else if(place_code_key.equals("CE7"))
            {
                holder.place_image.setImageResource(R.drawable.ic_convenience_store)
            }else if(place_code_key.equals("HP8"))
            {
                holder.place_image.setImageResource(R.drawable.ic_hospital)
            }else if(place_code_key.equals("AG2"))
            {
                holder.place_image.setImageResource(R.drawable.ic_cultural_facilities)
            }
        }
        holder.itemView.setOnClickListener{
            val intent = Intent()
            intent.putExtra("index", position)

            (context as SearchWordResultActivity).setResult(Activity.RESULT_OK, intent)
            context.finish()
        }
    }

    override fun getItemCount() = dataList.size
}