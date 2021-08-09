package com.hansung.traveldiary.src.plan.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class SearchWordResultInfo(title: String, address: String,groupCode:String) : Serializable {
    var title : String
    var address : String
    var groupCode:String

    init{
        this.title = title
        this.address = address
        this.groupCode=groupCode
    }
}

