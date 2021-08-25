package com.hansung.traveldiary.src.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FragmentPopularPlaceBinding
import com.hansung.traveldiary.databinding.FragmentUserNameBinding
import com.hansung.traveldiary.src.bulletin.UserNameAdapter
import com.hansung.traveldiary.src.home.adapter.PopularPlaceAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PopularPlaceFragment() : Fragment() {
    val popularPlace=ArrayList<RecommandActivity.place>()
    private lateinit var binding:FragmentPopularPlaceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        popularPlace.add(RecommandActivity.place(R.drawable.bukchon_hanok_village,
            "북촌한옥마을","서울 종로구 계동길 37  북촌문화센터 일대 (계동)","https://hanok.seoul.go.kr/front/index.do","02-2133-1372"))

        popularPlace.add(RecommandActivity.place(R.drawable.hanok_street_ikseon_dong,
            "익선동 한옥거리","서울 종로구 돈화문로11가길 17 (익선동)  3,5호선 종로3가역","",""))

        popularPlace.add(RecommandActivity.place(R.drawable.gyeongbokgung,
            "경복궁","서울 종로구 사직로 161 (세종로, 경복궁)","http://www.royalpalace.go.kr/","02-3700-3900"))

        popularPlace.add(RecommandActivity.place(R.drawable.national_museum_of_korea,
            "국립중앙박물관","서울 용산구 서빙고로 137 (국립중앙박물관)","https://www.museum.go.kr/site/main/home","02-2077-9000"))

        popularPlace.add(RecommandActivity.place(R.drawable.tower_of_lotte_world,
            "롯데월드타워","서울 송파구 올림픽로 300 (신천동)","https://www.lwt.co.kr/en/main/main.do","02-3213-5000 "))
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPopularPlaceBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment


        popularPlace.add(RecommandActivity.place(R.drawable.myeong_dong,
            "명동","서울 중구 명동8길 27 (명동2가, 엠 플라자) 4호선 명동역 5~10번 출구 일대 02-120","",""))

        popularPlace.add(RecommandActivity.place(R.drawable.insa_dong_street,
            "인사동길","서울 종로구 인사동길 39-1 (관훈동) 일대 1호선 종로3가역 4번,5번 출구 도보 10분 3호선 안국 5번,6번 출구 도보 5분 02-120","",""))
        popularPlace.add(RecommandActivity.place(R.drawable.cheonggyecheon,
            "청계천","서울 종로구 무교로 37 (서린동)","https://www.sisul.or.kr/open_content/cheonggye/","02-2290-6114"))
        binding.rv.apply{
            setHasFixedSize(true)
            adapter = PopularPlaceAdapter(popularPlace)
            layoutManager = LinearLayoutManager(context)
        }


        return binding.root
    }
}