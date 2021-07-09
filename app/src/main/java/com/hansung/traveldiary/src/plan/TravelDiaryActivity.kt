package com.hansung.traveldiary.src.plan

import android.graphics.PointF
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityTravelDiaryBinding
import com.hansung.traveldiary.util.StatusBarUtil
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.Utmk
import com.naver.maps.map.*
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource

class TravelDiaryActivity : AppCompatActivity(),OnMapReadyCallback {
    private lateinit var TF: travelMap
    private lateinit var DF: diary
    private lateinit var binding: ActivityTravelDiaryBinding
    private lateinit var locationSource:FusedLocationSource
    private lateinit var naverMap:NaverMap
    private lateinit var infoWindow:InfoWindow
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityTravelDiaryBinding.inflate(layoutInflater)
        locationSource= FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.WHITE_STATUS_BAR)
        val transaction = supportFragmentManager.beginTransaction()

        var TF= travelMap()
        var DF= diary()
        replaceView(TF)
        binding.travleTablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        replaceView(TF)
                    }
                    1 -> {
                        replaceView(DF)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_view) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_view, it).commit()
            }
        mapFragment.getMapAsync(this)
//
//        val planBottomDialog  = PlanBottomDialog()
//        planBottomDialog.show(supportFragmentManager, planBottomDialog.tag)
    }
    fun replaceView(tab: Fragment){
        var selectedFragment:Fragment?=null
        selectedFragment=tab
        selectedFragment?.let{
            supportFragmentManager.beginTransaction()
                .replace(R.id.tab_fragment, it).commit()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if(locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)){
            if(!locationSource.isActivated){
                naverMap.locationTrackingMode=LocationTrackingMode.None
            }
            return
        }
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(100.0, 100.0))
        naverMap.moveCamera(cameraUpdate)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    //지도객체 생성
    override fun onMapReady(nMap: NaverMap) {
        //복수개의 마커
        val markers:ArrayList<Marker> =ArrayList<Marker>()
        val latLngs:ArrayList<LatLng> =ArrayList<LatLng>()
        var count:Int =0
        naverMap = nMap
        infoWindow = InfoWindow()
        val marker = Marker()
        val marker1=Marker()
        val lovationOverlay = naverMap.locationOverlay
        infoWindow.open(marker)
        //마커 이미지 변경
        val image = OverlayImage.fromResource(R.drawable.mapmaker)
        //롱클릭리스너로 마커위치 변경경
       naverMap.setOnMapLongClickListener(NaverMap.OnMapLongClickListener(){ pointF: PointF, latLng: LatLng ->
            markers.add(Marker())
            markers.get(count).position=latLng
            markers.get(count++).map=naverMap
            latLngs.add(latLng)
            println(latLngs.get(0))
        })
    }

    companion object{
        private const val LOCATION_PERMISSION_REQUEST_CODE=1000
    }

}


