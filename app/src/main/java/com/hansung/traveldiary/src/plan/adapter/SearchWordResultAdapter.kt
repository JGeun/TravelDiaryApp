package com.hansung.traveldiary.src.plan.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hansung.traveldiary.databinding.ItemWordSearchResultBinding
import com.hansung.traveldiary.src.plan.SearchWordResultActivity
import com.hansung.traveldiary.src.plan.model.SearchWordResultInfo
import com.hansung.traveldiary.src.profile.edit_info.EditInfoActivity
import com.hansung.traveldiary.src.profile.gallery.SelectPictureActivity

class SearchWordResultAdapter(private val dataList:ArrayList<SearchWordResultInfo>): RecyclerView.Adapter<SearchWordResultAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemWordSearchResultBinding): RecyclerView.ViewHolder(binding.root){
        var title = binding.itemWsrTitle
        var address = binding.itemWsrAddress
        var distance = binding.worldSearchPlaceKinds
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val binding= ItemWordSearchResultBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.title.text = data.title
        holder.address.text = data.address
        holder.distance.text = data.distance.toString()
        val context = holder.itemView.context
        holder.itemView.setOnClickListener{
            val intent = Intent()
            intent.putExtra("index", position)

            (context as SearchWordResultActivity).setResult(Activity.RESULT_OK, intent)
            context.finish()
        }
    }

    override fun getItemCount() = dataList.size
}