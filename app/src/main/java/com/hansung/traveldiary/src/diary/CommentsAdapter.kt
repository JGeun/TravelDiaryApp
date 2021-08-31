package com.hansung.traveldiary.src.diary

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.R
import com.hansung.traveldiary.config.CommentBottomFragment
import com.hansung.traveldiary.config.MyCommentBottomFragment
import com.hansung.traveldiary.databinding.ItemCommentsListBinding
import com.hansung.traveldiary.src.CommentsData
import com.hansung.traveldiary.src.CommentsFolder
import com.hansung.traveldiary.src.MainActivity

class CommentsAdapter(val commentsList: CommentsFolder, val index: Int):RecyclerView.Adapter<CommentsAdapter.PagerViewHolder>() {
    class PagerViewHolder(val binding:ItemCommentsListBinding)
        :RecyclerView.ViewHolder(binding.root){
        val profileImage=binding.ivProfile
        val nickname=binding.tvNickname
        val comment=binding.tvCommet
        val date=binding.tvDate
        val editBtn=binding.ivEdit
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val binding = ItemCommentsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val data = commentsList.commentsFolder[position]
        var userNickname = "user1"
        var userImage = ""
        for(userInfo in MainActivity.userInfoList){
            if(userInfo.email == data.userEmail){
                userNickname = userInfo.nickname
                userImage = userInfo.profileImage
                break
            }
        }
        holder.nickname.text = userNickname
        if(userImage == "")
            Glide.with(holder.itemView.context).load(R.drawable.img_basic_profile).circleCrop().into(holder.profileImage)
        else
            Glide.with(holder.itemView.context).load(userImage).circleCrop().into(holder.profileImage)

        holder.comment.text = data.contents
        holder.date.text = data.date

        var context = holder.itemView.context
        val user = Firebase.auth.currentUser
        val db = Firebase.firestore

        holder.editBtn.setOnClickListener {
            if (data.userEmail == user!!.email.toString()) {
                val commentBtmSheetDialogFragment = MyCommentBottomFragment {//내 댓글일때
                    when (it) {
                        0 -> {  //신고
                            val builder = AlertDialog.Builder(context)
                            builder.setMessage("신고하시겠습니까?")

                            var listener = object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface?, which: Int) {
                                    when (which) {
                                        DialogInterface.BUTTON_POSITIVE -> return
                                        DialogInterface.BUTTON_NEGATIVE -> return
                                    }
                                }
                            }

                            builder.setPositiveButton("취소", listener)
                            builder.setNegativeButton("확인", listener)

                            builder.show()
                        }
                        1 -> { //삭제
                            var idx = 0L
                            commentsList.commentsFolder.removeAt(position)
                            idx = MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.idx
                            MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.comments.commentsFolder.remove(data)
                            Log.d("체크", "$idx")

                            Log.d("유저 이메일", data.userEmail)
                        db.collection("Diary").document(data.userEmail)
                            .collection("DiaryData").document(idx.toString())
                            .set(MainActivity.bulletinDiaryArray[index].userDiaryData.baseData).addOnSuccessListener {
                                Log.d("댓글 삭제", "성공")
                            }

                            notifyDataSetChanged()
                        }
                    }
                }
                commentBtmSheetDialogFragment.show(
                    (context as CommentListActivity).supportFragmentManager,
                    commentBtmSheetDialogFragment.tag
                )
            }else {
                val commentBtmSheetDialogFragment = CommentBottomFragment { //남의 댓글일때
                    when (it) {
                        0 -> {  //신고
                            val builder = AlertDialog.Builder(context)
                            builder.setMessage("신고하시겠습니까?")

                            var listener = object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface?, which: Int) {
                                    when (which) {
                                        DialogInterface.BUTTON_POSITIVE -> return
                                        DialogInterface.BUTTON_NEGATIVE -> return
                                    }
                                }
                            }

                            builder.setPositiveButton("취소", listener)
                            builder.setNegativeButton("확인", listener)

                            builder.show()
                        }
                    }
                }
                commentBtmSheetDialogFragment.show(
                    (context as CommentListActivity).supportFragmentManager,
                    commentBtmSheetDialogFragment.tag
                )
            }
        }
    }

    override fun getItemCount(): Int = commentsList.commentsFolder.size

}