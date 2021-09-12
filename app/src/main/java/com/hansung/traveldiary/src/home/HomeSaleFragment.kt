    package com.hansung.traveldiary.src.home

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FragmentHomeSaleBinding
import com.hansung.traveldiary.src.travel.AddBook.SelectAreaBtmDialog
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
    class HomeSaleFragment(): Fragment() {
        var date = ""
        var ToCityId:String?=""
        var FromCityId:String?=""
        var ddate:String?=""
        var rdate:String?=""
        private val airportViewModel : AirportViewModel by activityViewModels()
        private lateinit var binding : FragmentHomeSaleBinding
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding=FragmentHomeSaleBinding.inflate(inflater,container, false)
            binding.airUp.setOnClickListener {
                val bottomDialog = AirPortBtmDialog(false)
                bottomDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomDialogTheme)
                bottomDialog.show(  (activity as AppCompatActivity).supportFragmentManager, "bottomPlanlistSheet")
            }

            val current = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
            binding.tvDeperatureDay.text = current.format(formatter)
            binding.tvReturnDay.text = current.format(formatter)

            airportViewModel.airport.observe(viewLifecycleOwner, androidx.lifecycle.Observer<String> {
                binding.deperatureArea.text = airportViewModel.airport.value
                when(binding.deperatureArea.text){
                    "서울"->{ binding.tvDeperature.text="ICN"
                        ToCityId="SEL"}
                    "제주"->{binding.tvDeperature.text="CJU"
                        ToCityId="CJU"}
                    "부산"->{binding.tvDeperature.text="PUS"
                    ToCityId="PUS"}
                    "광주"->{binding.tvDeperature.text="KWJ"
                    ToCityId="KOW"}
                    "청주"->{binding.tvDeperature.text="CJJ"
                    ToCityId="CJJ"}
                    "여수"->{binding.tvDeperature.text="RSU"
                    ToCityId="RSU"}
                    else->{binding.tvDeperature.text=""}
                }
            })
            binding.airDown.setOnClickListener {
                val bottomDialog = AirPortBtmDialog(true)
                bottomDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomDialogTheme)
                bottomDialog.show(  (activity as AppCompatActivity).supportFragmentManager, "bottomPlanlistSheet")
            }
            airportViewModel.destinationAirPort.observe(viewLifecycleOwner, androidx.lifecycle.Observer<String> {
                binding.destinationAirPort.text = airportViewModel.destinationAirPort.value
                when(binding.destinationAirPort.text){
                    "서울"->{binding.tvReturn.text="ICN"
                        FromCityId="SEL"}
                    "제주"->{binding.tvReturn.text="CJU"
                        FromCityId="CJU"}
                    "부산"->{binding.tvReturn.text="PUS"
                        FromCityId="PUS"}
                    "광주"->{binding.tvReturn.text="KWJ"
                        FromCityId="KOW"}
                    "청주"->{binding.tvReturn.text="CJJ"
                        FromCityId="CJJ"}
                    "여수"->{binding.tvReturn.text="RSU"
                        FromCityId="RSU"}
                    else->{binding.tvReturn.text=""}

                }
            })

            binding.deperatureLayout.setOnClickListener {
                val cal = Calendar.getInstance()
                val year = cal.get(Calendar.YEAR)
                val month = cal.get(Calendar.MONTH)
                val day = cal.get(Calendar.DAY_OF_MONTH)

                var listener = DatePickerDialog.OnDateSetListener { _, y, m, d ->
                    date = String.format("$y-%02d-%02d", m + 1, d)
                    Log.d("시작날짜", date)
                    binding.tvDeperatureDay.setText(date)
                    ddate=date
                    Log.d("달력", "OK")
                }
                println(ddate)
                val datePickerDialog =
                    DatePickerDialog((activity as AppCompatActivity), listener, year, month, day)
                datePickerDialog.show()
            }

            binding.returnLayout.setOnClickListener {
                val cal = Calendar.getInstance()
                val year = cal.get(Calendar.YEAR)
                val month = cal.get(Calendar.MONTH)
                val day = cal.get(Calendar.DAY_OF_MONTH)

                var listener = DatePickerDialog.OnDateSetListener { _, y, m, d ->
                    date = String.format("$y-%02d-%02d", m + 1, d)
                    Log.d("시작날짜", date)
                    binding.tvReturnDay.setText(date)
                    rdate=date
                    Log.d("달력", "OK")
                }

                val datePickerDialog =
                    DatePickerDialog((activity as AppCompatActivity), listener, year, month, day)
                println(binding.tvReturnDay.text)
                datePickerDialog.show()
            }
            binding.btnSearch.setOnClickListener {
                if(binding.destinationAirPort.text.equals( binding.deperatureArea.text)){
                    Toast.makeText(context, "출발지, 도착지를 다르게 선택해주세요!",Toast.LENGTH_SHORT).show()
                }
                if(ddate!!.get(0)>rdate!!.get(0) || ddate!!.get(1)>rdate!!.get(1) || (ddate!!.get(0)==rdate!!.get(0)&&ddate!!.get(1)==rdate!!.get(1)&& ddate!!.get(2)>rdate!!.get(2) )){
                        Toast.makeText(context, "출발 날짜와 도착 날짜를 올바르게 선택해주세요!",Toast.LENGTH_SHORT).show()
                }

                    println("이쪽 들어왔음")
                    val intent= Intent(context,WebViewActivity::class.java)
                    intent.putExtra("deperature",ddate)
                    intent.putExtra("return",rdate)
                    intent.putExtra("ToCityId",ToCityId)
                    intent.putExtra("FromCityId",FromCityId)
                    startActivity(intent)

            }
            return binding.root
        }
    }