package com.iitms.bfr.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import javax.inject.Inject

open class Status @Inject constructor() : Serializable {

    @SerializedName("Id")
    var id: String? = null

    @SerializedName("IsErrorInService")
    var isErrorInService: Boolean = false

    @SerializedName("Message")
    var message: String? = null

    var responseCode : Int = 0
}