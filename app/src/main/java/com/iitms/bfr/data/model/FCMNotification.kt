package com.iitms.bfr.data.model

import com.google.gson.annotations.SerializedName

class FCMNotification {

    @SerializedName("NotId")
    var notId: Int? = 0

    @SerializedName("ImageUrl")
    var imageUrl: String? = null

    @SerializedName("NotType")
    var notType: String? = null

    @SerializedName("UserId")
    var userId: Int = 0

    @SerializedName("Action")
    var action: String? = null

    @SerializedName("MessageTitle")
    var messageTitle: String? = null

    @SerializedName("Message")
    var message: String? = null

    @SerializedName("DateTime")
    var dateTime: String? = null

    @SerializedName("ReadFlag")
    var readFlag: Int? = 0
}