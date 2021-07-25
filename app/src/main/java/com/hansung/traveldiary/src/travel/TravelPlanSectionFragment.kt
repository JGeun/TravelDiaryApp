package com.hansung.traveldiary.src.travel

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FragmentTriptogoBinding
import com.hansung.traveldiary.src.travel.adapter.PlanSectionAdapter

import com.naver.maps.map.util.FusedLocationSource
import kotlin.collections.ArrayList

data class PlanSectionData(val image: Drawable, val title: String, val color: String, val start_date : String, val end_date : String)


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
            dlg.start(this, tripPlanList)
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
                    R.drawable.ic_diary_purple,
                    null
                )!!, "부산여행와랄라", "purple", "12", "13")

        )
        tripPlanList.add(
            PlanSectionData(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_diary_olive,
                    null
                )!!, "부산에가요오", "olive", "24", "30"
            )
        )
        tripPlanList.add(
            PlanSectionData(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_diary_green,
                    null
                )!!, "여수", "green", "01", "03"
            )
        )
        tripPlanList.add(
            PlanSectionData(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_diary_pink,
                    null
                )!!, "제주도", "pink", "10", "12"
            )
        )
        tripPlanList.add(
            PlanSectionData(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_diary_blue,
                    null
                )!!, "순천", "blue", "13", "16"
            )
        )
        tripPlanList.add(
            PlanSectionData(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_diary_purple,
                    null
                )!!, "렛츠고강릉요요", "purple", "09", "12"
            )
        )
        tripPlanList.add(
            PlanSectionData(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_diary_pink,
                    null
                )!!, "속초", "pink", "19","21"
            )
        )

//        --------------------------
        tripPlanList.add(
            PlanSectionData(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_diary_purple,
                    null
                )!!, "부산여행와랄라", "purple", "12", "13")

        )
        tripPlanList.add(
            PlanSectionData(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_diary_olive,
                    null
                )!!, "부산에가요오", "olive", "24", "30"
            )
        )
        tripPlanList.add(
            PlanSectionData(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_diary_green,
                    null
                )!!, "여수", "green", "01", "03"
            )
        )
        tripPlanList.add(
            PlanSectionData(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_diary_pink,
                    null
                )!!, "제주도", "pink", "10", "12"
            )
        )
        tripPlanList.add(
            PlanSectionData(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_diary_blue,
                    null
                )!!, "순천", "blue", "13", "16"
            )
        )
        tripPlanList.add(
            PlanSectionData(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_diary_purple,
                    null
                )!!, "렛츠고강릉요요", "purple", "09", "12"
            )
        )
        tripPlanList.add(
            PlanSectionData(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_diary_pink,
                    null
                )!!, "속초", "pink", "19","21"
            )
        )

    }

    public fun addPlanAndNotify(data : PlanSectionData){
        tripPlanList.add(data)
        binding.plantripRv.adapter!!.notifyDataSetChanged()
    }

    fun newInstant(): TriptogoFragment {
        val args = Bundle()
        val frag = TriptogoFragment()
        frag.arguments = args
        return frag
    }

    /*
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
                R.id.addPlen_rb_pink -> color = "pink"
                R.id.addPlen_rb_blue -> color = "blue"
                R.id.addPlen_rb_green -> color = "green"
                R.id.addPlen_rb_olive -> color = "olive"
                R.id.addPlen_rb_purple -> color = "purple"
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

            binding.plantripRv.adapter?.notifyDat
            aSetChanged()
        }
        builder.setNegativeButton("취소", null)

        builder.setView(view)

        builder.show()
    }

     */
}

