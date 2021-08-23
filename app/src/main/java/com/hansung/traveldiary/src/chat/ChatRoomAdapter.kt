package com.hansung.traveldiary.src.chat

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ItemChatRoomBinding
import com.hansung.traveldiary.src.ChatIdxFolder
import com.hansung.traveldiary.src.travel.AddBook.AddTravelPlanActivity

class ChatRoomAdapter(private val chatIdxFolder: ChatIdxFolder):RecyclerView.Adapter<ChatRoomAdapter.ViewHolder>(){
    private var user: FirebaseUser? = null
    private var db: FirebaseFirestore? = null

    class ViewHolder(binding: ItemChatRoomBinding):RecyclerView.ViewHolder(binding.root) {
        val userProfileImage: ImageView
        val userName: TextView
        val userPreview: TextView
        val currentTime: TextView

        init {
            userProfileImage = binding.userProfileImage
            userName = binding.userName
            userPreview = binding.userPreview
            currentTime = binding.currentTime
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChatRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        user = Firebase.auth.currentUser
        db = Firebase.firestore

        val data = chatIdxFolder.chatIdxFolder
        val context = holder.itemView.context
        val tempImage = ResourcesCompat.getDrawable(context.resources, R.drawable.img_beach, null)
        if(data[position].image != ""){
            Glide.with(context).load(data[position].image).apply(RequestOptions().circleCrop()).into(holder.userProfileImage)
        }else{
            Glide.with(context).load(tempImage).apply(RequestOptions().circleCrop()).into(holder.userProfileImage)
        }

        holder.userName.text = data[position].title
        holder.userPreview.text = data[position].preview

        var ampm = ""
        var hour = data[position].lastTime.substring(11, 13).toInt()
        if(hour>=12){
            ampm = "오후 "
            hour -= 12
        }else{
            ampm = "오전 "
        }
        var time = ampm + hour.toString() + data[position].lastTime.substring(13, 16)
        holder.currentTime.text = time
        
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("idx", data[position].idx)
            context.startActivity(intent)
        }

        holder.itemView.setOnLongClickListener {
            val intent = Intent(context, EditChatroomActivity::class.java)
//            intent.putExtra("data", chatIdxFolder.chatIdxFolder)
            intent.putExtra("position", position)
            intent.putExtra("username", data[position].title)

            context.startActivity(intent)

            notifyDataSetChanged()

            return@setOnLongClickListener false //다음 이벤트 계속 진행: false, 이벤트 완료 true
        }
    }

    fun del(){

    }

    override fun getItemCount(): Int = chatIdxFolder.chatIdxFolder.size

}