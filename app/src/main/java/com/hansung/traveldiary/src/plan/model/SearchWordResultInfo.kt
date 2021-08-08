package com.hansung.traveldiary.src.plan.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class SearchWordResultInfo(title: String, address: String,groupCode:String,place_code:HashMap<String,String>) : Serializable {
    var title : String
    var address : String
    var groupCode:String
    var place_code:HashMap<String,String>
    init{
        this.title = title
        this.address = address
        this.groupCode=groupCode
        this.place_code=place_code
    }



}

