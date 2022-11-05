package com.iitms.bfr.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class User(
    @SerializedName("contactMedium") var contactMedium: ContactMedium? = null,
    @SerializedName("id") var id: String? = null,
    @SerializedName("familyName") var familyName: String? = null,
    @SerializedName("gender") var gender: String? = null,
    @SerializedName("givenName") var givenName: String? = null,
    @SerializedName("maritalStatus") var maritalStatus: String? = null,
    @SerializedName("middleName") var middleName: String? = null,
    @SerializedName("passCode") var passCode: String? = null,
    @SerializedName("place") var place: List<Place>? = null,
    @SerializedName("platform") var platform: String? = null,
    @SerializedName("system") var system: String? = null
): Serializable