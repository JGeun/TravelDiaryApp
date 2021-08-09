package com.hansung.traveldiary.src.plan

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FragmentPlanMapBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.PlaceData
import com.hansung.traveldiary.src.plan.model.*
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.overlay.PathOverlay
import com.naver.maps.map.util.FusedLocationSource
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class TravelPlanMapFragment(val index: Int, val day: Int) : Fragment(), OnMapReadyCallback, KakaoSearchView {
    private lateinit var binding : FragmentPlanMapBinding
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap
    private var searchWord = ""
    private lateinit var searchWordResultTask: ActivityResultLauncher<Intent>
    private lateinit var searchWordResult : SearchWordResultInfo
    private var searchWordIndex = 0
    private var markerList=ArrayList<Marker>()
    private val latLngList = ArrayList<LatLng>()
    private var path : PathOverlay? = null
    private var lastLatitude = 37.58842461354086
    private var lastLongitude = 127.00601781685579
    private lateinit var searchLatlng : LatLng

    private var categoryGCeMap : HashMap<String, String> = HashMap()

    private var user: FirebaseUser? = null
    private var db: FirebaseFirestore? = null
    private val TAG = "TravelPlanMapFragment"

    private val userPlaceDataModel : SharedPlaceViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("map created")

        binding = FragmentPlanMapBinding.inflate(inflater, container, false)

        user = Firebase.auth.currentUser
        db = Firebase.firestore
        initGCMap()
        searchWordResultTaskInit()

        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }
        mapFragment.getMapAsync(this)

        locationSource = FusedLocationSource(this,
            TravelPlanBaseActivity.LOCATION_PERMISSION_REQUEST_CODE
        )

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

                    KakaoSearchKeywordService(this@TravelPlanMapFragment).tryGetKeyWordSearchInfo(
                        searchWord, 1
                    )
                    return true
                }
                return false
            }
        })

        binding.planBtmBtnStore.setOnClickListener{
            val placeData = PlaceData(searchWordResult.latitude.toDouble(), searchWordResult.longitude.toDouble(),searchWordResult.placeName)
            userPlaceDataModel.putPlace(placeData)
////            TravelPlanBaseActivity.planTotalData.dayList[TravelPlanBaseActivity.index].placeInfoArray.add(placeInfo)
//            println("user: " + user!!.email.toString())
//            println("title: " + title)
            val planDocRef = db!!.collection("Plan").document(user!!.email.toString()).collection("PlanData")
                .document(MainActivity.userPlanArray[index].baseData.idx.toString()).collection("PlaceInfo")
                .document(afterDate(MainActivity.userPlanArray[index].baseData.startDate, day))
                .set(userPlaceDataModel.items)

            val marker=Marker()
            marker.icon= OverlayImage.fromResource(R.drawable.ic_travel_marker)
            marker.width=100
            marker.height=150
            marker.position = searchLatlng
            marker.map = naverMap
//            Log.d(TAG, "db업데이트 후: " + userPlaceDataModel.items.size.toString())
            latLngList.add(searchLatlng)
//            Log.d(TAG, "입력 후: " + userPlaceDataModel.items.size.toString())

            if(userPlaceDataModel.items.placeFolder.size >= 2){
                if(path == null){
                    setLine()
                    path!!.coords = latLngList
                    path!!.map = naverMap
                }else
                    path!!.coords = latLngList
            }
        }
        return binding.root
    }

    fun afterDate(date: String, day: Int, pattern: String = "yyyy-MM-dd"): String {
        val format = SimpleDateFormat(pattern, Locale.getDefault())

        val calendar = Calendar.getInstance()
        format.parse(date)?.let { calendar.time = it }
        calendar.add(Calendar.DAY_OF_YEAR, day)

        return format.format(calendar.time)
    }

    fun initGCMap(){
        categoryGCeMap.put("MT1", "대형마트")
        categoryGCeMap.put("CS2", "편의점")
        categoryGCeMap.put("PS3", "어린이집")
        categoryGCeMap.put("SC4", "학교")
        categoryGCeMap.put("AC5", "학원")
        categoryGCeMap.put("PK6", "주차장")
        categoryGCeMap.put("OL7", "주유소")
        categoryGCeMap.put("SW8", "지하철역")
        categoryGCeMap.put("BK9", "은행")
        categoryGCeMap.put("AG2", "문화시설")
        categoryGCeMap.put("PO3", "공공기관")
        categoryGCeMap.put("AT4", "관광명소")
        categoryGCeMap.put("AD5", "숙박")
        categoryGCeMap.put("FD6", "음식점")
        categoryGCeMap.put("CE7", "카페")
        categoryGCeMap.put("HP8", "병원")
        categoryGCeMap.put("PM9", "약국")
    }

    fun searchWordResultTaskInit(){
        searchWordResultTask = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                searchWordResult = result.data?.getSerializableExtra("result") as SearchWordResultInfo
                binding.planBtmClShowPlaceInfo.isVisible = true
                binding.planBtmPlaceTitle.text = searchWordResult.placeName
                val categoryArr = searchWordResult.categoryName.trim().split(">")
                binding.planBtmPlaceCategory.text = categoryArr[categoryArr.size-1]
                val roadAddressName = searchWordResult.roadAddress
                if(roadAddressName.isEmpty())
                    binding.planBtmPlaceAddress.text = searchWordResult.address
                else
                    binding.planBtmPlaceAddress.text = roadAddressName
                binding.planBtmPlaceNumber.text = searchWordResult.phone
                binding.planBtmPlaceUrl.text = searchWordResult.url

                val longitude = searchWordResult.longitude.toDouble()
                val latitude = searchWordResult.latitude.toDouble()
//                val tm128 = Tm128(mapx.toDouble(), mapy.toDouble())
                searchLatlng = LatLng(latitude, longitude)

                val marker = Marker()
                marker.icon= OverlayImage.fromResource(R.drawable.ic_search_marker)
                marker.width=140
                marker.height=150
                /*binding.planBtmBtnStore.setOnClickListener {
                    marker.icon= OverlayImage.fromResource(R.drawable.ic_travel_marker)
                }*/
                marker.position = searchLatlng
                marker.map = naverMap

                val cameraUpdate = CameraUpdate.scrollAndZoomTo(searchLatlng, 17.0)
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
    }

    override fun onStart() {
        super.onStart()
        println("map fragment start")
    }

    override fun onMapReady(naverMap: NaverMap) {
        println("onMapReady")
        this.naverMap = naverMap
        naverMap.locationSource = locationSource
        naverMap.uiSettings.isLocationButtonEnabled = true

        latLngList.clear()

        val size = userPlaceDataModel.items.placeFolder.size
        if(size > 0){
            lastLatitude = userPlaceDataModel.items.placeFolder[size-1].latitude
            lastLongitude  = userPlaceDataModel.items.placeFolder[size-1].longitude
            for(placeData in userPlaceDataModel.items.placeFolder){
                val marker = Marker()
                //마커 이미지 변경 및 크기 조절
                marker.icon= OverlayImage.fromResource(R.drawable.ic_travel_marker)
                marker.width=100
                marker.height=150
                latLngList.add(LatLng(placeData.latitude, placeData.longitude))
                marker.position = LatLng(placeData.latitude, placeData.longitude)
                marker.map = naverMap
            }
            if(path != null && size < 2){
                path!!.map = null
                path = null
            }else if(size >= 2){
                if(path == null){
                    setLine()
                }
                path!!.coords = latLngList
                path!!.map = naverMap
            }
        }

        val cameraUpdate = CameraUpdate.toCameraPosition(CameraPosition(LatLng(lastLatitude, lastLongitude), 13.0))
            .reason(3)
            .finishCallback {
                showCustomToast("완료")
            }
            .cancelCallback {
                showCustomToast("취소")
            }
        naverMap.moveCamera(cameraUpdate)

        naverMap.setOnMapClickListener{ _, _ ->
            binding.planBtmClShowPlaceInfo.isVisible = false
        }


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

    fun showCustomToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onGetKeywordSearchSuccess(response: KakaoSearchKeywordResponse) {
        Log.d("확인", response.documents.size.toString())

        val intent = Intent(context, SearchWordResultActivity::class.java)
        intent.putExtra("word", searchWord)

        var searchWordResultList = response.documents
        val resultList = ArrayList<SearchWordResultInfo>()
        for (result in searchWordResultList) {
            resultList.add(SearchWordResultInfo(result.place_name, result.address_name,result.category_group_code,
                result.category_name, result.road_address_name, result.phone, result.place_url, result.x, result.y))
        }

        intent.putExtra("is_end", response.meta.is_end)
        intent.putExtra("result", resultList)
        binding.planEtSearch.setText("")
        searchWord = ""
        searchWordResultTask.launch(intent)
    }

    override fun onGetKeywordSearchFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    fun setLine(){
        path = PathOverlay()
        path!!.outlineWidth = 0
        path!!.width = 15
        path!!.color = ResourcesCompat.getColor(resources, R.color.mapLineColor, null)
    }

    fun getColor() : Int{
        val color = (context as TravelPlanBaseActivity).getColor()
        Log.d("체크", "Map color: $color")
        return when(color){
            "pink" -> R.color.pink
            "purple" -> R.color.purple
            "yellow" -> R.color.yellow
            "sky" -> R.color.sky
            "blue" -> R.color.blue
            "orange" -> R.color.orange
            else -> R.color.orange
        }
    }
}
