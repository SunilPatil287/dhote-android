package com.iitms.bfr.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Place(
    @SerializedName("addressType")
    var addressType: String? = null,
    @SerializedName("city")
    var city: String? = null,
    @SerializedName("country")
    var country: String? = null,
    @SerializedName("defaultDeliveryAddress")
    var defaultDeliveryAddress: Boolean? = null,
    @SerializedName("fullAddress")
    var fullAddress: String? = null,
    @SerializedName("geoLocationRefOrValue")
    var geoLocationRefOrValue: GeoLocationRefOrValue? = null,
    @SerializedName("locality")
    var locality: String? = null,
    @SerializedName("pinCode")
    var pinCode: String? = null,
    @SerializedName("state")
    var state: String? = null,
    @SerializedName("streetName")
    var streetName: String? = null
): Serializable