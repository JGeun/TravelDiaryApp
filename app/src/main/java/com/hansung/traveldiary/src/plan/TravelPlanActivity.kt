package com.hansung.traveldiary.src.plan

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityTravelDiaryBinding
import com.hansung.traveldiary.src.plan.adapter.PlaceData
import com.hansung.traveldiary.src.plan.adapter.PlanAdapter
import com.hansung.traveldiary.src.plan.model.MapSearchInfo
import com.hansung.traveldiary.src.plan.model.SearchInfo
import com.hansung.traveldiary.src.plan.model.SearchWordResultInfo
import com.hansung.traveldiary.util.StatusBarUtil
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.Tm128
import com.naver.maps.map.*
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.*

class TravelPlanActivity : AppCompatActivity(), OnMapReadyCallback, TravelMapView {
    private lateinit var TF: travelMap
    private lateinit var DF: diary
    private val planList = ArrayList<PlaceData>()
    private val planAdapter = PlanAdapter(planList)
    private lateinit var binding: ActivityTravelDiaryBinding
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap
    private lateinit var infoWindow: InfoWindow
    private val TAG = "TravelDiaryActivity"
    private var searchWord = ""
    private var searchWordResultList = ArrayList<SearchInfo>()
    private lateinit var searchWordResultTask: ActivityResultLauncher<Intent>
    private var searchWordIndex = 0

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://openapi.naver.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTravelDiaryBinding.inflate(layoutInflater)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.WHITE_STATUS_BAR)

        searchWordResultTask = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                searchWordIndex = result.data?.getIntExtra("index", 0)!!
                val mapx = searchWordResultList[searchWordIndex].mapx
                val mapy = searchWordResultList[searchWordIndex].mapy
                val tm128 = Tm128(mapx.toDouble(), mapy.toDouble())
                val latLng = tm128.toLatLng()

                val marker = Marker()
                marker.position = latLng
                marker.map = naverMap

                val cameraUpdate = CameraUpdate.scrollAndZoomTo(latLng, 17.0)
                    .reason(3)
                    .animate(CameraAnimation.Easing, 2000)
                    .finishCallback {
                        showCustomToast("완료")
                    }
                    .cancelCallback {
                        showCustomToast("취소")
                    }
                naverMap.moveCamera(cameraUpdate)
            }
        }

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }
        mapFragment.getMapAsync(this)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)


        binding.planEtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d(TAG, "입력: ${binding.planEtSearch.text}")
                searchWord = binding.planEtSearch.text.toString()
            }

            override fun afterTextChanged(s: Editable?) {}

        })
        binding.planEtSearch.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if ((event!!.action == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER) {

//                    planList.add(PlaceData(searchWord))
//                    planAdapter.notifyDataSetChanged()
                    TravelMapService(this@TravelPlanActivity).tryGetSearchInfo(searchWord, "random")

                    return true
                }
                return false
            }
        })

//        binding.planBtmDialogBtn.setOnTouchListener(object : View.OnTouchListener {
//            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//                val layoutParam = binding.planBtmDialogsheet.layoutParams
//                layoutParam.width = ViewGroup.LayoutParams.MATCH_PARENT
//                Log.d("y값", event!!.y.toString())
//                if (event!!.action == MotionEvent.ACTION_UP) {
//                    layoutParam.height -= event.y.toInt()
//                    if (layoutParam.height < 80)
//                        layoutParam.height = 80
//                    else if (layoutParam.height >= 800)
//                        layoutParam.height = 800;
//                    binding.planBtmDialogsheet.requestLayout()
//                }
//                return false
//            }
//        })
//
//        initPlanList()
//
//        binding.planRvLocation.apply {
//            setHasFixedSize(true)
//            adapter = planAdapter
//            layoutManager = LinearLayoutManager(this@TravelPlanActivity)
//        }
    }

    fun initPlanList() {

    }

    //지도객체 생성
    fun onMapReady(nMap: MapView) {
        //복수개의 마커
        val markers: ArrayList<Marker> = ArrayList<Marker>()
        val latLngs: ArrayList<LatLng> = ArrayList<LatLng>()
        val lat_arr: ArrayList<DoubleArray> = ArrayList<DoubleArray>()
        var count: Int = 0
        infoWindow = InfoWindow()
        val marker = Marker()
        val marker1 = Marker()

//        val lovationOverlay = naverMap.locationOverlay
//        infoWindow.open(marker)
//        //마커 이미지 변경
//        val image = OverlayImage.fromResource(R.drawable.mapmaker)
//        //롱클릭리스너로 마커위치 변경경
//       naverMap.setOnMapLongClickListener(NaverMap.OnMapLongClickListener(){ pointF: PointF, latLng: LatLng ->
//
//                markers.add(Marker())
//
//                markers.get(count).position=latLng
//                latLngs.add(latLng)
//                println("위도는 ${latLngs.get(0).latitude} 경도는 ${latLngs.get(0).longitude}")
//                markers.get(count++).map=naverMap
//
//        })
//       //마커 삭제
//        naverMap.setOnMapClickListener(NaverMap.OnMapClickListener(){ pointF: PointF, latLng: LatLng ->
//            val size=markers.size-1
//
//            if(markers.size>=1){
//                for(i in 0..size){
//                    if(round(latLngs.get(i).latitude*100000)/100000==round(latLng.latitude*100000)/100000&&
//                        round(latLngs.get(i).longitude*10000)/10000==round(latLng.longitude*10000)/10000){
//                        println("Dfaccccccfd")
//                        markers.get(i).map=null
//                        markers.remove(markers.get(i))
//                        latLngs.remove(latLngs.get(i))
//                        count--
//                        break
//                    }
//                }
//            }
//        })
    }

    override fun onGetMapSearchSuccess(response: MapSearchInfo) {
        val intent = Intent(this@TravelPlanActivity, SearchWordResultActivity::class.java)
        intent.putExtra("word", searchWord)
        searchWordResultList = response.item
        val resultList = ArrayList<SearchWordResultInfo>()
        for (result in searchWordResultList) {
            resultList.add(
                SearchWordResultInfo(
                    result.title.replace(" ", "").replace("<b>", " ").replace("</b>", ""), result.address
                )
            )
        }

        intent.putExtra("result", resultList)
        binding.planEtSearch.setText("")
        searchWord = ""
        searchWordResultTask.launch(intent)

//        Log.d("확인", response.item[0].title)
//        val mapx = response.item[0].mapx
//        val mapy = response.item[0].mapy
//        Log.d(TAG, mapx.toString() + " / " + mapy.toString())
//
//        Log.d(TAG, "address: " + response.item[0].address)
//        Log.d(TAG, "road: " + response.item[0].roadAddress)
//        var mLat  = 0.0
//        var mlng  = 0.0

//        -----------------------------------------
//        val address = response.item[0].address
//        val geoCoder = Geocoder(this)
//        try{
//            val resultLocation = geoCoder.getFromLocationName(address, 1)
//            mLat = resultLocation.get(0).latitude
//            mlng = resultLocation.get(0).longitude
//        }catch (e: IOException){
//            e.printStackTrace()
//            Log.d(TAG, "주소변환 실패")
//        }
//
//        Log.d(TAG, "mLat: $mLat mlng: $mlng")
//        -------------------------------------------
//        val tm128 = Tm128(mapx.toDouble(), mapy.toDouble())
//        val latLng = tm128.toLatLng()
//
//        val marker = Marker()
//        marker.position = latLng
//        marker.map = naverMap
//
//        val cameraUpdate = CameraUpdate.scrollAndZoomTo(latLng, 17.0)
//            .reason(3)
//            .animate(CameraAnimation.Easing, 2000)
//            .finishCallback {
//                showCustomToast("완료")
//            }
//            .cancelCallback {
//                showCustomToast("취소")
//            }
//        naverMap.moveCamera(cameraUpdate)


    }

    override fun onGetMapSearchFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    private fun showCustomToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource
        naverMap.uiSettings.isLocationButtonEnabled = true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        Log.d(TAG, "MainActivity - onRequestPermissionsResult")
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions,
                grantResults
            )
        ) {
            if (!locationSource.isActivated) { // 권한 거부됨
                Log.d(TAG, "MainActivity - onRequestPermissionsResult 권한 거부됨")
                naverMap.locationTrackingMode = LocationTrackingMode.None
            } else {
                Log.d(TAG, "MainActivity - onRequestPermissionsResult 권한 승인됨")
                naverMap.locationTrackingMode = LocationTrackingMode.Follow // 현위치 버튼 컨트롤 활성
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}



