package com.iitms.bfr.ui.listener

import com.iitms.bfr.data.model.CategoryList
import com.iitms.bfr.data.model.DeliveryTime
import com.iitms.bfr.data.model.SubCategoryDetail

interface SubCategoryListener {
    fun onSubCategoryClick(subCategoryDetail: SubCategoryDetail)
}