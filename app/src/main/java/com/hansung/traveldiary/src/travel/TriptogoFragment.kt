package com.hansung.traveldiary.src.travel

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FragmentTriptogoBinding
import java.util.*

class TriptogoFragment : Fragment() {
    private lateinit var binding: FragmentTriptogoBinding

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

        return binding.root
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
        builder.setPositiveButton("추가", null)
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