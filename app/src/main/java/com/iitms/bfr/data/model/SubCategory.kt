package com.iitms.bfr.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class SubCategory (

    @SerializedName("productList")
    var subCategory : List<SubCategoryDetail>? = null
        ): Serializable