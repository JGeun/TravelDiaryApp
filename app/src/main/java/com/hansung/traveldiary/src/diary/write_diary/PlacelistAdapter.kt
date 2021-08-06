package com.hansung.traveldiary.src.diary.write_diary

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.config.EditBottomDialogFragment
import com.hansung.traveldiary.databinding.ItemPlacelistBinding
import com.hansung.traveldiary.src.plan.PlacelistFragment
import com.hansung.traveldiary.src.plan.ScheduleFragment
import com.hansung.traveldiary.src.plan.TravelPlanBaseActivity
import com.hansung.traveldiary.src.plan.adapter.ScheduleAdapter
import com.hansung.traveldiary.src.plan.model.SharedPlaceViewModel

class PlacelistAdapter(private val placeViewModel: SharedPlaceViewModel, private var finishText: TextView) : RecyclerView.Adapter<PlacelistAdapter.ViewHolder>() {
    private var db: FirebaseFirestore? = null
    private var user: FirebaseUser? = null
    var title: String? = null

    class ViewHolder(val binding : ItemPlacelistBinding) : RecyclerView.ViewHolder(binding.root){
        val location: TextView = binding.itemScheduleLocation
        val editIcon: ImageView = binding.itemScheduleEdit
        val topBar: View = binding.topBar
        val bottomBar: View = binding.bottomBar
        val dotImg: ImageView = binding.dotImg
        val upBtn: ImageView = binding.ivMoveup
        val downBtn: ImageView = binding.ivMovedown
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPlacelistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        user = Firebase.auth.currentUser
        db = Firebase.firestore
        Log.d("DB title", title.toString())

        val context = holder.itemView.context

        if (PlacelistFragment.checked) {
            holder.editIcon.visibility = View.INVISIBLE
            holder.upBtn.visibility = View.VISIBLE
            holder.downBtn.visibility = View.VISIBLE

        } else {
            holder.editIcon.visibility = View.VISIBLE
            holder.upBtn.visibility = View.GONE
            holder.downBtn.visibility = View.GONE
        }

        holder.upBtn.setOnClickListener {
            if(position != 0 ){
                placeViewModel.moveUp(ShowPlacelistActivity.index, position)
                notifyDataSetChanged()
            }
        }

        holder.downBtn.setOnClickListener {
            if(position != itemCount -1){
                placeViewModel.moveDown(ShowPlacelistActivity.index, position)
                notifyDataSetChanged()
            }
        }

        holder.location.text =
            placeViewModel.items.dayPlaceList[ShowPlacelistActivity.index].placeInfoArray[position].placeName
        holder.editIcon.setOnClickListener {
            val editBtmSheetDialogFragment = EditBottomDialogFragment {
                when (it) {
                    0 -> {
                        PlacelistFragment.checked = true
                        finishText.visibility = View.VISIBLE
                        notifyDataSetChanged()
                    }
                    1 -> {
                        placeViewModel.removePlace(ShowPlacelistActivity.index, position)
                        val userDocRef = db!!.collection("User").document("UserData")
                        userDocRef.collection(user!!.email.toString()).document("Diary")
                            .collection(title!!).document("PlanPlaceInfo")
                            .set(TravelPlanBaseActivity.placeInfoFolder)
                        notifyDataSetChanged()
                    }
                }
            }
            editBtmSheetDialogFragment.show(
                (context as ShowPlacelistActivity).supportFragmentManager,
                editBtmSheetDialogFragment.tag
            )
        }

        if (position == 0)
            holder.topBar.visibility = View.INVISIBLE
        if (position == placeViewModel.items.dayPlaceList[ShowPlacelistActivity.index].placeInfoArray.size - 1)
            holder.bottomBar.visibility = View.INVISIBLE

    }

    override fun getItemCount(): Int =
        placeViewModel.items.dayPlaceList[ShowPlacelistActivity.index].placeInfoArray.size

}