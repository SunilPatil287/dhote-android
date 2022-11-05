package com.iitms.bfr.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Data(
    @SerializedName("Type")
    var type: String? = null,

    @SerializedName("Key")
    var key: String? = null,

    @SerializedName("Value")
    var value: String? = null
) : Serializable {
}