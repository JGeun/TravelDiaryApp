package com.hansung.traveldiary.src.travel.AddBook.adapter

import android.app.Activity
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ItemSelectedUsersBinding
import com.hansung.traveldiary.src.travel.AddBook.AddTravelPlanActivity
import com.hansung.traveldiary.src.travel.AddBook.AtpAddFriendsActivity
import com.hansung.traveldiary.src.travel.AddBook.FriendInfo
import kotlin.coroutines.coroutineContext

class SelectedFriendsAdapter(val selectedusers: ArrayList<FriendInfo>, val atpAddFriendsActivity: AtpAddFriendsActivity?, val addTravelPlanActivity: AddTravelPlanActivity?) : RecyclerView.Adapter<SelectedFriendsAdapter.ViewHolder>() {
    class ViewHolder(binding: ItemSelectedUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        val userImage = binding.userImage
        val userName = binding.userName
        val DeleteBtn = binding.ivDelete
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemSelectedUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onBindViewHolder(holder: SelectedFriendsAdapter.ViewHolder, position: Int) {
//        var atpAddFriendsActivity: AtpAddFriendsActivity? = null
//        var addTravelPlanActivity: AddTravelPlanActivity? = null
//        Log.d("액티비티이름", holder.itemView.context.javaClass.toString())
//
//
//        if(holder.itemView.context.javaClass.toString() == "class com.hansung.traveldiary.src.travel.AddBook.AtpAddFriendsActivity"){
//            atpAddFriendsActivity = activity as AtpAddFriendsActivity
//            Log.d("친구리스트액티비티", atpAddFriendsActivity.toString())
//        }else{
//            addTravelPlanActivity = activity as AddTravelPlanActivity
//            Log.d("친구리스트액티비티", addTravelPlanActivity.toString())
//        }

        val context = holder.itemView.context
        val imagePath = selectedusers[position].imagePath
        if(imagePath != "")
            Glide.with(context).load(imagePath).circleCrop().into(holder.userImage)
        else
            Glide.with(context).load(ResourcesCompat.getDrawable(context.resources, R.drawable.img_basic_profile, null)).circleCrop().into(holder.userImage)
        holder.userName.text = selectedusers[position].nickname

        holder.DeleteBtn.setOnClickListener {
            var data = selectedusers[position]
            selectedusers.removeAt(position)
            notifyDataSetChanged()
            if (atpAddFriendsActivity != null) {
                if (atpAddFriendsActivity.friendArray.size!=0)
                    atpAddFriendsActivity.notifyFreindArr(data)
            }

            if (selectedusers.size==0)
                addTravelPlanActivity?.showBtn()

        }
    }

    override fun getItemCount(): Int = selectedusers.size
}