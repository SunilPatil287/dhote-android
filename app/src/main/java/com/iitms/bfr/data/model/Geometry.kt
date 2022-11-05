package com.iitms.bfr.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Geometry(

    @SerializedName("geographicDatum") var geographicDatum: String? = null,
    @SerializedName("latitude") var latitude: Double? = null,
    @SerializedName("longitude") var longitude: Double? = null

): Serializable