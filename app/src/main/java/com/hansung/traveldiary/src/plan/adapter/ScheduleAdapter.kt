package com.hansung.traveldiary.src.plan.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.R
import com.hansung.traveldiary.config.EditBottomDialogFragment
import com.hansung.traveldiary.databinding.ItemScheduleBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.plan.ScheduleFragment
import com.hansung.traveldiary.src.plan.TravelPlanBaseActivity
import com.hansung.traveldiary.src.plan.model.SharedPlaceViewModel
import java.text.SimpleDateFormat
import java.util.*

class ScheduleAdapter(private val placeViewModel: SharedPlaceViewModel, private val index: Int, private val day: Int, private val finishText: TextView) : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {
    private var db: FirebaseFirestore? = null
    private var user: FirebaseUser? = null

    class ViewHolder(val binding: ItemScheduleBinding) : RecyclerView.ViewHolder(binding.root) {
        val location: TextView = binding.itemScheduleLocation
        val editIcon: ImageView = binding.itemScheduleEdit
        val topBar: View = binding.topBar
        val bottomBar: View = binding.bottomBar
        val dotImg: ImageView = binding.dotImg
        val upBtn: ImageView = binding.ivMoveup
        val downBtn: ImageView = binding.ivMovedown
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        user = Firebase.auth.currentUser
        db = Firebase.firestore

        val context = holder.itemView.context

        val barColor = when (MainActivity.userPlanArray[index].baseData.color) {
            "pink" -> R.color.pink
            "purple" -> R.color.purple
            "yellow" -> R.color.yellow
            "sky" -> R.color.sky
            "blue" -> R.color.blue
            "orange" -> R.color.orange
            else -> R.color.orange
        }
        holder.topBar.setBackgroundColor(context.resources.getColor(barColor))
        holder.bottomBar.setBackgroundColor(context.resources.getColor(barColor))

        val dotColor = when (MainActivity.userPlanArray[index].baseData.color) {
            "pink" -> R.drawable.pink_dot
            "purple" -> R.drawable.purple_dot
            "yellow" -> R.drawable.yellow_dot
            "sky" -> R.drawable.sky_dot
            "blue" -> R.drawable.blue_dot
            "orange" -> R.drawable.orange_dot
            else -> R.drawable.orange_dot
        }
        holder.dotImg.setBackgroundResource(dotColor)

        if (ScheduleFragment.checked) {
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
                placeViewModel.moveUp(position)
                notifyDataSetChanged()
            }
        }

        holder.downBtn.setOnClickListener {
            if(position != itemCount -1){
                placeViewModel.moveDown(position)
                notifyDataSetChanged()
            }
        }

        holder.location.text =
            placeViewModel.items.placeFolder[position].placeName
        holder.editIcon.setOnClickListener {
            val editBtmSheetDialogFragment = EditBottomDialogFragment {
                when (it) {
                    0 -> {
                        ScheduleFragment.checked = true
                        finishText.visibility = View.VISIBLE
                        notifyDataSetChanged()
                    }
                    1 -> {
                        placeViewModel.removePlace(position)
                        println("Size: ${placeViewModel.items.placeFolder.size}")
                        println("Document:${afterDate(MainActivity.userPlanArray[index].baseData.startDate, position)}")
                        db!!.collection("Plan").document(user!!.email.toString())
                            .collection("PlanData").document(MainActivity.userPlanArray[index].baseData.idx.toString())
                            .collection("PlaceInfo").document(afterDate(MainActivity.userPlanArray[index].baseData.startDate, day))
                            .set(placeViewModel.items).addOnSuccessListener {
                                notifyDataSetChanged()
                            }

                    }
                }
            }
            editBtmSheetDialogFragment.show((context as TravelPlanBaseActivity).supportFragmentManager, editBtmSheetDialogFragment.tag)
        }

        if (position == 0)
            holder.topBar.visibility = View.GONE
        if (position == placeViewModel.items.placeFolder.size - 1)
            holder.bottomBar.visibility = View.GONE
    }

    override fun getItemCount(): Int = placeViewModel.items.placeFolder.size

    fun afterDate(date: String, day: Int, pattern: String = "yyyy-MM-dd"): String {
        val format = SimpleDateFormat(pattern, Locale.getDefault())

        val calendar = Calendar.getInstance()
        format.parse(date)?.let { calendar.time = it }
        calendar.add(Calendar.DAY_OF_YEAR, day)

        return format.format(calendar.time)
    }
}