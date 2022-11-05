package com.iitms.bfr.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UserData(
    @SerializedName("user")
    var user: User? = null
) : Serializable