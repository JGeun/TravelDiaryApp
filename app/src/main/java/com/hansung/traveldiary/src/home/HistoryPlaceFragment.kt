package com.hansung.traveldiary.src.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FragmentPopularPlaceBinding
import com.hansung.traveldiary.src.home.adapter.PopularPlaceAdapter

class HistoryPlaceFragment () : Fragment() {
    val popularPlace=ArrayList<RecommandActivity.place>()
    private lateinit var binding: FragmentPopularPlaceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        popularPlace.add(RecommandActivity.place(
            R.drawable.changdeokhgung,
            "창덕궁","서울 종로구 율곡로 99 (와룡동, 창덕궁)","http://www.cdg.go.kr/default.jsp","02-3668-2300"))

        popularPlace.add(RecommandActivity.place(
            R.drawable.hanok_street_ikseon_dong,
            "익선동 한옥거리","서울 종로구 돈화문로11가길 17${"\n"} (익선동)  3,5호선 종로3가역","",""))

        popularPlace.add(RecommandActivity.place(
            R.drawable.gyeongbokgung,
            "경복궁","서울 종로구 사직로 161 (세종로, 경복궁)","http://www.royalpalace.go.kr/","02-3700-3900"))

        popularPlace.add(RecommandActivity.place(
            R.drawable.bongeunsa_temple,
            "봉은사","서울 강남구 봉은사로 531 (봉은사)","http://www.bongeunsa.org/","02-3218-4800"))

        popularPlace.add(RecommandActivity.place(
            R.drawable.myeong_dong_cathdral,
            "명동성당","서울 중구 명동길 74 (명동성당)","http://www.mdsd.or.kr/","02-774-1784"))

        popularPlace.add(RecommandActivity.place(R.drawable.jogyesa_temple,
            "조계사","서울특별시 종로구 우정국로 55 (조계사)","http://www.jogyesa.kr/user/jogye/","02-768-8600"))
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPopularPlaceBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        binding.rv.apply{
            setHasFixedSize(true)
            adapter = PopularPlaceAdapter(popularPlace)
            layoutManager = LinearLayoutManager(context)
        }

        return binding.root
    }
}