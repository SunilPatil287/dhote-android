package com.iitms.bfr.ui.listener

import com.iitms.bfr.data.model.CategoryList
import com.iitms.bfr.data.model.DeliveryTime

interface CategoryListener {
    fun onCategoryClick(categoryList: CategoryList)
}