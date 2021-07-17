package com.hansung.traveldiary.src.travel

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FragmentTriptogoBinding
import com.hansung.traveldiary.src.travel.adapter.PlanSectionAdapter

import com.naver.maps.map.util.FusedLocationSource
import java.util.*
import kotlin.collections.ArrayList

data class PlanSectionData(val image: Drawable, val title: String, val color: String)


class TriptogoFragment : Fragment() {
    private lateinit var binding: FragmentTriptogoBinding
    private lateinit var mLocationSource: FusedLocationSource
    private val tripPlanList = ArrayList<PlanSectionData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTriptogoBinding.inflate(inflater, container, false)

        binding.floatingActionButton.setOnClickListener {
            val dlg =AddPlanDialog(requireContext())
            dlg.start()
        }

        initTripPlanList()

        binding.plantripRv.apply{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter =PlanSectionAdapter(tripPlanList)

        }

        return binding.root
    }

    private fun initTripPlanList() {
        tripPlanList.add(
            PlanSectionData(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_graybook,
                    null
                )!!, "부산여행와랄라", "gray")

        )
        tripPlanList.add(
            PlanSectionData(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_bluebook,
                    null
                )!!, "부산에가요오", "blue"
            )
        )
        tripPlanList.add(
            PlanSectionData(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_redbook,
                    null
                )!!, "여수", "red"
            )
        )
        tripPlanList.add(
            PlanSectionData(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_darkgraybook,
                    null
                )!!, "제주도", "gray"
            )
        )
        tripPlanList.add(
            PlanSectionData(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_yellowbook,
                    null
                )!!, "순천", "yellow"
            )
        )
        tripPlanList.add(
            PlanSectionData(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_bluebook,
                    null
                )!!, "렛츠고강릉요요", "blue"
            )
        )
        tripPlanList.add(
            PlanSectionData(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_greenbook,
                    null
                )!!, "속초", "green"
            )
        )

//        --------------------------
        tripPlanList.add(
            PlanSectionData(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_graybook,
                    null
                )!!, "부산여행와랄라", "gray")

        )
        tripPlanList.add(
            PlanSectionData(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_bluebook,
                    null
                )!!, "부산에가요오", "blue"
            )
        )
        tripPlanList.add(
            PlanSectionData(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_redbook,
                    null
                )!!, "여수", "red"
            )
        )
        tripPlanList.add(
            PlanSectionData(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_darkgraybook,
                    null
                )!!, "제주도", "gray"
            )
        )
        tripPlanList.add(
            PlanSectionData(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_yellowbook,
                    null
                )!!, "순천", "yellow"
            )
        )
        tripPlanList.add(
            PlanSectionData(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_bluebook,
                    null
                )!!, "렛츠고강릉요요", "blue"
            )
        )
        tripPlanList.add(
            PlanSectionData(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_greenbook,
                    null
                )!!, "속초", "green"
            )
        )
    }

    fun newInstant(): TriptogoFragment {
        val args = Bundle()
        val frag = TriptogoFragment()
        frag.arguments = args
        return frag
    }

    fun showDialog() {
        var view = layoutInflater.inflate(R.layout.dialog_add_plan, null)

        var builder = AlertDialog.Builder(context)
        builder.setTitle("여행 추가하기")

        view.findViewById<EditText>(R.id.edit_start_date).setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                val cal = Calendar.getInstance()
                val year = cal.get(Calendar.YEAR)
                val month = cal.get(Calendar.MONTH)
                val day = cal.get(Calendar.DAY_OF_MONTH)

                var listener = DatePickerDialog.OnDateSetListener { _, y, m, d ->
                    view.findViewById<EditText>(R.id.edit_start_date).setText("$y - ${m + 1} - $d")
                }

                val datePickerDialog =
                    DatePickerDialog(requireContext(), listener, year, month, day)
                datePickerDialog.show()
            }
        }

        view.findViewById<EditText>(R.id.edit_end_date).setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                val cal = Calendar.getInstance()
                val year = cal.get(Calendar.YEAR)
                val month = cal.get(Calendar.MONTH)
                val day = cal.get(Calendar.DAY_OF_MONTH)

                var listener = DatePickerDialog.OnDateSetListener { _, y, m, d ->
                    view.findViewById<EditText>(R.id.edit_end_date).setText("$y - ${m + 1} - $d")
                }

                val datePickerDialog =
                    DatePickerDialog(requireContext(), listener, year, month, day)
                datePickerDialog.show()
            }
        }
        var color = "null"
        val bookColor = view.findViewById<RadioGroup>(R.id.addPlan_rg_bookcolor)
        bookColor.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.addPlen_rb_red -> color = "red"
                R.id.addPlen_rb_blue -> color = "blue"
                R.id.addPlen_rb_green -> color = "green"
                R.id.addPlen_rb_yellow -> color = "yellow"
                R.id.addPlen_rb_gray -> color = "gray"
            }
            Log.d("확인", bookColor.checkedRadioButtonId.toString())
        }
        builder.setPositiveButton("추가") { dialog, which ->
            var title = view.findViewById<EditText>(R.id.edit_title).text.toString()
            tripPlanList.add(
                PlanSectionData(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_redbook,
                        null
                    )!!, title, color
                )
            )

            binding.plantripRv.adapter?.notifyDataSetChanged()
        }
        builder.setNegativeButton("취소", null)

        builder.setView(view)

        builder.show()
    }
}

