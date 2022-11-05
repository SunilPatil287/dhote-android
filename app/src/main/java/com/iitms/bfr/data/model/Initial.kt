package com.iitms.bfr.data.model

import com.google.gson.annotations.SerializedName

class Initial(

    @SerializedName("userInfo")
    var userInfo: UserInfo? = null,

) : Status() {

}