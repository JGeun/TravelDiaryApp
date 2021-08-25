package com.hansung.traveldiary.src.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FragmentPopularPlaceBinding
import com.hansung.traveldiary.src.home.adapter.PopularPlaceAdapter

class NaturePlaceFragment () : Fragment() {
    val popularPlace=ArrayList<RecommandActivity.place>()
    private lateinit var binding: FragmentPopularPlaceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        popularPlace.add(RecommandActivity.place(R.drawable.cheonggyecheon,
            "청계천","서울 종로구 무교로 37 (서린동)  ","https://hanok.seoul.go.kr/front/index.do","02-2133-1372"))

        popularPlace.add(RecommandActivity.place(R.drawable.seoul_tower_of_namsan,
            "남산서울타워","서울 용산구 남산공원길 105 (용산동2가, YTN서울타워)","https://www.sisul.or.kr/open_content/cheonggye/","02-2290-6114"))

        popularPlace.add(RecommandActivity.place(R.drawable.namsan_park,
            "남산공원","서울특별시 중구 삼일대로 231","http://parks.seoul.go.kr/template/sub/namsan.do","02-3783-5900"))

        popularPlace.add(RecommandActivity.place(R.drawable.hangang_river,
            "한강","서울 영등포구 여의공원로 68","https://hangang.seoul.go.kr/","(주) 02-120 (야) 3780-0777"))

        popularPlace.add(RecommandActivity.place(R.drawable.seoul_forest,
            "서울숲","서울특별시 성동구 뚝섬로 273 (성수동1가)","https://seoulforest.or.kr/","02-460-2905"))

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPopularPlaceBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        popularPlace.add(RecommandActivity.place(R.drawable.naksan_park,
            "낙산공원","서울 종로구 낙산길 41 (동숭동)","http://parks.seoul.go.kr/template/sub/naksan.do","02-743-7985"))

        popularPlace.add(RecommandActivity.place(R.drawable.bukhansan_national_park,
            "북한산국립공원","서울 성북구 보국문로34길 6-21 (정릉동) ","http://www.knps.or.kr/bukhan","02-909-0497"))

        popularPlace.add(RecommandActivity.place(R.drawable.yeouido_park,
            "여의도 공원","서울 영등포구 여의공원로 68","http://parks.seoul.go.kr/template/sub/yeouido.do","02-761-4079"))


        binding.rv.apply{
            setHasFixedSize(true)
            adapter = PopularPlaceAdapter(popularPlace)
            layoutManager = LinearLayoutManager(context)
        }


        return binding.root
    }
}