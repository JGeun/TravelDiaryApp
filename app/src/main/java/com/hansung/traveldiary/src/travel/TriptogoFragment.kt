package com.hansung.traveldiary.src.travel

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FragmentTriptogoBinding
import com.hansung.traveldiary.src.travel.adapter.PlanTripAdapter
import okhttp3.internal.notify
import java.util.*
import kotlin.collections.ArrayList

data class PlanTripinData(val image : Drawable, val title : String)

class TriptogoFragment : Fragment() {
    private lateinit var binding: FragmentTriptogoBinding
    private val tripPlanList = ArrayList<PlanTripinData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTriptogoBinding.inflate(inflater, container, false)

        binding.floatingActionButton.setOnClickListener {
//            startActivity(Intent(it.context, DiaryActivity::class.java))
            showDialog()
        }

        initTripPlanList()

        val adapter = PlanTripAdapter(tripPlanList)
        binding.plantripRv.adapter = adapter
        binding.plantripRv.layoutManager = GridLayoutManager(context, 3)
        binding.plantripRv.setHasFixedSize(false)

        return binding.root
    }

    private fun initTripPlanList(){
        tripPlanList.add(PlanTripinData(ResourcesCompat.getDrawable(resources, R.drawable.ic_graybook, null)!!, "부산여행와랄라"))
        tripPlanList.add(PlanTripinData(ResourcesCompat.getDrawable(resources, R.drawable.ic_bluebook, null)!!, "부산에가요오"))
        tripPlanList.add(PlanTripinData(ResourcesCompat.getDrawable(resources, R.drawable.ic_redbook, null)!!, "여수"))
        tripPlanList.add(PlanTripinData(ResourcesCompat.getDrawable(resources, R.drawable.ic_darkgraybook, null)!!, "제주도"))
        tripPlanList.add(PlanTripinData(ResourcesCompat.getDrawable(resources, R.drawable.ic_yellowbook, null)!!, "순천"))
        tripPlanList.add(PlanTripinData(ResourcesCompat.getDrawable(resources, R.drawable.ic_bluebook, null)!!, "렛츠고강릉요요"))
        tripPlanList.add(PlanTripinData(ResourcesCompat.getDrawable(resources, R.drawable.ic_greenbook, null)!!, "속초"))
    }

    fun newInstant() : TriptogoFragment
    {
        val args = Bundle()
        val frag = TriptogoFragment()
        frag.arguments = args
        return frag
    }

    fun showDialog(){
        var view = layoutInflater.inflate(R.layout.dialog_add_plan, null)

        var builder = AlertDialog.Builder(context)
        builder.setTitle("여행 추가하기")
        builder.setPositiveButton("추가") { dialog, which ->
            var title = view.findViewById<EditText>(R.id.edit_title).text.toString()
            tripPlanList.add(
                PlanTripinData(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_greenbook,
                        null
                    )!!, title
                )
            )
            binding.plantripRv.adapter?.notifyDataSetChanged()
        }
        builder.setNegativeButton("취소", null)

        view.findViewById<EditText>(R.id.edit_start_date).setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                val cal = Calendar.getInstance()
                val year = cal.get(Calendar.YEAR)
                val month = cal.get(Calendar.MONTH)
                val day = cal.get(Calendar.DAY_OF_MONTH)

                var listener = DatePickerDialog.OnDateSetListener{
                        _, y, m, d -> view.findViewById<EditText>(R.id.edit_start_date).setText("$y - ${m+1} - $d")
                }

                val datePickerDialog = DatePickerDialog(requireContext(), listener, year, month, day )
                datePickerDialog.show()
            }
        }

        view.findViewById<EditText>(R.id.edit_end_date).setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                val cal = Calendar.getInstance()
                val year = cal.get(Calendar.YEAR)
                val month = cal.get(Calendar.MONTH)
                val day = cal.get(Calendar.DAY_OF_MONTH)

                var listener = DatePickerDialog.OnDateSetListener{
                        _, y, m, d -> view.findViewById<EditText>(R.id.edit_end_date).setText("$y - ${m+1} - $d")
                }

                val datePickerDialog = DatePickerDialog(requireContext(), listener, year, month, day )
                datePickerDialog.show()
            }
        }


        builder.setView(view)

        builder.show()
    }

}