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
import com.hansung.traveldiary.src.PlaceInfo
import com.hansung.traveldiary.src.plan.model.*
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.overlay.PathOverlay
import com.naver.maps.map.util.FusedLocationSource

class TravelPlanMapFragment() : Fragment(), OnMapReadyCallback, KakaoSearchView {
    private lateinit var binding : FragmentPlanMapBinding
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap
    private var searchWord = ""
    private var searchWordResultList = ArrayList<KakaoSearchKeywordInfo>()
    private lateinit var searchWordResultTask: ActivityResultLauncher<Intent>
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
    private var title : String? = null
    private var placeInfoArray = ArrayList<PlaceInfo>()
    constructor(title: String?) : this() {
        this.title = title
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("map created")
        println("체크 TravelPlanMapFragment ${title}")
        binding = FragmentPlanMapBinding.inflate(inflater, container, false)

        placeInfoArray = userPlaceDataModel.items.dayPlaceList[TravelPlanBaseActivity.index].placeInfoArray

        user = Firebase.auth.currentUser
        db = Firebase.firestore
        initGCMap()
        searchWordResultTaskInit()

        Log.d(TAG, placeInfoArray.size.toString())

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
            Log.d(TAG, "입력 전: " + placeInfoArray.toString())
            val placeInfo = PlaceInfo(searchWordResultList[searchWordIndex].place_name, searchWordResultList[searchWordIndex].y.toDouble(), searchWordResultList[searchWordIndex].x.toDouble())
            userPlaceDataModel.putPlace(placeInfo, TravelPlanBaseActivity.index)
////            TravelPlanBaseActivity.planTotalData.dayList[TravelPlanBaseActivity.index].placeInfoArray.add(placeInfo)
//            println("user: " + user!!.email.toString())
//            println("title: " + title)
            val userDocRef = db!!.collection("User").document("UserData")
            userDocRef.collection(user!!.email.toString()).document("Plan").collection(title!!).document("PlaceInfo")
                .set(TravelPlanBaseActivity.placeInfoFolder)
            val marker=Marker()
            marker.icon= OverlayImage.fromResource(R.drawable.ic_travel_marker)
            marker.width=100
            marker.height=150
            marker.position = searchLatlng
            marker.map = naverMap
//            Log.d(TAG, "db업데이트 후: " + userPlaceDataModel.items.size.toString())
            latLngList.add(searchLatlng)
//            Log.d(TAG, "입력 후: " + userPlaceDataModel.items.size.toString())

            if(placeInfoArray.size >= 2){
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
                searchWordIndex = result.data?.getIntExtra("index", 0)!!
                binding.planBtmClShowPlaceInfo.isVisible = true
                binding.planBtmPlaceTitle.text = searchWordResultList[searchWordIndex].place_name
                val categoryArr = searchWordResultList[searchWordIndex].category_name.trim().split(">")
                binding.planBtmPlaceCategory.text = categoryArr[categoryArr.size-1]
                val roadAddressName = searchWordResultList[searchWordIndex].road_address_name
                if(roadAddressName.isEmpty())
                    binding.planBtmPlaceAddress.text = searchWordResultList[searchWordIndex].address_name
                else
                    binding.planBtmPlaceAddress.text = roadAddressName
                binding.planBtmPlaceNumber.text = searchWordResultList[searchWordIndex].phone
                binding.planBtmPlaceUrl.text = searchWordResultList[searchWordIndex].place_url

                val longitude = searchWordResultList[searchWordIndex].x.toDouble()
                val latitude = searchWordResultList[searchWordIndex].y.toDouble()
                Log.d("위치체크", longitude.toString() + " / " + latitude.toString())
//                val tm128 = Tm128(mapx.toDouble(), mapy.toDouble())
                searchLatlng = LatLng(latitude, longitude)

                val marker = Marker()
                marker.icon= OverlayImage.fromResource(R.drawable.ic_search_marker)
                marker.width=100
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

        if(placeInfoArray.size != 0){
            lastLatitude = placeInfoArray[placeInfoArray.size-1].latitude
            lastLongitude  = placeInfoArray[placeInfoArray.size-1].longitude
            for(placeData in placeInfoArray){
                val marker = Marker()
                //마커 이미지 변경 및 크기 조절
                marker.icon= OverlayImage.fromResource(R.drawable.ic_travel_marker)
                marker.width=100
                marker.height=150
                latLngList.add(LatLng(placeData.latitude, placeData.longitude))
                marker.position = LatLng(placeData.latitude, placeData.longitude)
                marker.map = naverMap
            }
            if(path != null && placeInfoArray.size < 2){
                path!!.map = null
                path = null
            }else if(placeInfoArray.size >= 2){
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

        searchWordResultList = response.documents
        val resultList = ArrayList<SearchWordResultInfo>()
        for (result in searchWordResultList) {
            resultList.add(
                SearchWordResultInfo(
                    result.place_name, result.address_name, result.category_group_name
                )
            )
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
