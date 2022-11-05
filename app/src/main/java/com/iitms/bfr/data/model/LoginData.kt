package com.iitms.bfr.data.model

import com.google.gson.annotations.SerializedName

class LoginData(
    @SerializedName("mobileNumber")
    var mobileNumber: String? = null,

    @SerializedName("message")
    var message: String? = null,

    @SerializedName("otp")
    var otp: String? = null
)