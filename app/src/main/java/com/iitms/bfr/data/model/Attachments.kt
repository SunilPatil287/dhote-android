package com.iitms.bfr.data.model
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Attachments(

    @SerializedName("id")
    var id: String? = null,
    @SerializedName("href")
    var href: String? = null,
    @SerializedName("attachmentType")
    var attachmentType: String? = null,
    @SerializedName("isRef")
    var isRef: Boolean? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("description")
    var description: String? = null

): Serializable