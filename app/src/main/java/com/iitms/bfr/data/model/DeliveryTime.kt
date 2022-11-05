package com.iitms.bfr.data.model

import com.google.gson.annotations.SerializedName

class DeliveryTime(

    @SerializedName("Time")
    var time: String? = null,

    @SerializedName("TimeId")
    var timeId: String? = null,

    @SerializedName("Status")
    var status: Boolean = false

)