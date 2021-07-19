package com.hansung.traveldiary.src.plan

import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.media.audiofx.BassBoost
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.location.LocationServices
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityTravelDiaryBinding
import com.hansung.traveldiary.src.plan.adapter.PlaceData
import com.hansung.traveldiary.src.plan.adapter.PlanAdapter
import com.hansung.traveldiary.src.plan.model.*
import com.hansung.traveldiary.src.travel.AddPlanDialog
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

class TravelPlanActivity : AppCompatActivity(), OnMapReadyCallback, TravelMapView, KakaoSearchView {
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
    private var searchWordResultList = ArrayList<KakaoSearchKeywordInfo>()
    private lateinit var searchWordResultTask: ActivityResultLauncher<Intent>
    private var searchWordIndex = 0

    private var lastPosX = 127.00601781685579
    private var lastPosY = 37.58842461354086

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
        private const val LOCATION_PERMISSION_FUN_REQUEST_CODE = 1001
        val naverRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://openapi.naver.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val kakaoRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTravelDiaryBinding.inflate(layoutInflater)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.WHITE_STATUS_BAR)

        getLocationPermission()

        searchWordResultTask = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                searchWordIndex = result.data?.getIntExtra("index", 0)!!
                val mapx = searchWordResultList[searchWordIndex].x.toDouble()
                val mapy = searchWordResultList[searchWordIndex].y.toDouble()
                Log.d("위치체크", mapx.toString() + " / " + mapy.toString())
//                val tm128 = Tm128(mapx.toDouble(), mapy.toDouble())
                val latLng = LatLng(mapy, mapx)

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
//                    TravelMapService(this@TravelPlanActivity).tryGetSearchInfo(searchWord, "random")
                    KakaoSearchKeywordService(this@TravelPlanActivity).tryGetKeyWordSearchInfo(
                        searchWord
                    )
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

    fun getLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("MainActivity", "permission체크")
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_PERMISSION_FUN_REQUEST_CODE
            )
        } else {
            getLastLocation()
        }
    }

    fun initPlanList() {

    }

    override fun onGetMapSearchSuccess(response: MapSearchInfo) {
//        val intent = Intent(this@TravelPlanActivity, SearchWordResultActivity::class.java)
//        intent.putExtra("word", searchWord)
//        searchWordResultList = response.item
//        val resultList = ArrayList<SearchWordResultInfo>()
//        for (result in searchWordResultList) {
//            resultList.add(
//                SearchWordResultInfo(
//                    result.title.replace(" ", "").replace("<b>", " ").replace("</b>", ""), result.address
//                )
//            )
//        }
//
//        intent.putExtra("result", resultList)
//        binding.planEtSearch.setText("")
//        searchWord = ""
//        searchWordResultTask.launch(intent)

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

        setCameraInMap(lastPosY, lastPosX, 13.0)
    }

    fun setCameraInMap(posY: Double, posX: Double, zoom: Double = 13.0){
        val cameraUpdate = CameraUpdate.scrollAndZoomTo(LatLng(posY, posX), zoom)
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        Log.d(TAG, "MainActivity - onRequestPermissionsResult")
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated) { // 권한 거부됨
                Log.d(TAG, "MainActivity - onRequestPermissionsResult 권한 거부됨")
                naverMap.locationTrackingMode = LocationTrackingMode.None
            } else {
                Log.d(TAG, "MainActivity - onRequestPermissionsResult 권한 승인됨")
                naverMap.locationTrackingMode = LocationTrackingMode.Follow // 현위치 버튼 컨트롤 활성
            }
            return
        } else if (requestCode == LOCATION_PERMISSION_FUN_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            } else {
                showCustomToast("위치를 허용해주시지 않으면 지도를 사용할 수 없습니다.")
                requestPermissionReload()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun requestPermissionReload() {
        val dlg = RequestLocationPermissionReloadDialog(this)
        dlg.start()
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                lastPosX = it.longitude
                lastPosY = it.latitude
                Log.d("체크", lastPosX.toString() + " / " + lastPosY.toString())
//                setCameraInMap(lastPosY, lastPosX)
            }
        }.addOnFailureListener {
            showCustomToast("위치를 가져오는 데 실패했습니다.")
        }
    }

    override fun onGetKeywordSearchSuccess(response: KakaoSearchKeywordResponse) {
        Log.d("확인", response.documents.size.toString())

        val intent = Intent(this@TravelPlanActivity, SearchWordResultActivity::class.java)
        intent.putExtra("word", searchWord)
        searchWordResultList = response.documents
        val resultList = ArrayList<SearchWordResultInfo>()
        for (result in searchWordResultList) {
            resultList.add(
                SearchWordResultInfo(
                    result.place_name, result.address_name
                )
            )
        }

        intent.putExtra("result", resultList)
        binding.planEtSearch.setText("")
        searchWord = ""
        searchWordResultTask.launch(intent)
    }

    override fun onGetKeywordSearchFailure(message: String) {
        showCustomToast("오류 : $message")
    }
}



