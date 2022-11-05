package com.iitms.bfr.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ProductList(
    @SerializedName("createdOn")
    var createdOn: String? = null,

    @SerializedName("lastModified")
    var lastModified: String? = null,

    @SerializedName("createdBy")
    var createdBy: String? = null,

    @SerializedName("lastUpdatedBy")
    var lastUpdatedBy: String? = null,

    @SerializedName("status")
    var status: String? = null,

    @SerializedName("tag")
    var tag: String? = null,

    @SerializedName("noOfOrders")
    var noOfOrders: Int? = null,

    @SerializedName("id")
    var id: String? = null,

    @SerializedName("href")
    var href: String? = null,

    @SerializedName("code")
    var code: String? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("description")
    var description: String? = null,

    @SerializedName("discount")
    var discount: Int? = null,

    @SerializedName("discountPercentage")
    var discountPercentage: Int? = null,

    @SerializedName("categoryId")
    var categoryId: String? = null,

    @SerializedName("originalPrice")
    var originalPrice: Int? = null,

    @SerializedName("piecesSize")
    var piecesSize: String? = null,

    @SerializedName("originalWeight")
    var originalWeight: Int? = null,

    @SerializedName("approxWeight")
    var approxWeight: Int? = null,

    @SerializedName("stock")
    var stock: Int? = null
): Serializable