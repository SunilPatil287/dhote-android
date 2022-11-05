package com.iitms.bfr.ui.listener

import com.iitms.bfr.data.model.CategoryList
import com.iitms.bfr.data.model.DeliveryTime
import com.iitms.bfr.data.model.OrderList

interface OrdersListener {
    fun onOrdersClick(orderList: OrderList)
}