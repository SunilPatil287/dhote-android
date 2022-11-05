package com.iitms.bfr.ui.view.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.iitms.bfr.R
import com.iitms.bfr.data.model.*
import com.iitms.bfr.databinding.DialogDeliveryTimeBinding
import com.iitms.bfr.databinding.OrdersFragmentBinding
import com.iitms.bfr.ui.adapter.DeliveryTimeAdapter
import com.iitms.bfr.ui.adapter.MoreCouponAdapter
import com.iitms.bfr.ui.adapter.OrdersAdapter
import com.iitms.bfr.ui.base.BaseFragment
import com.iitms.bfr.ui.listener.CategoryListener
import com.iitms.bfr.ui.listener.DeliveryTimeDialogListener
import com.iitms.bfr.ui.listener.OrdersListener
import com.iitms.bfr.ui.viewModel.OrderStatusFragmentViewModel
import java.util.ArrayList
import javax.inject.Inject


class OrdersFragment @Inject constructor() :
    BaseFragment<OrderStatusFragmentViewModel, OrdersFragmentBinding>(), View.OnClickListener {

    @Inject
    lateinit var ordersAdapter: OrdersAdapter

    private var userInfo: UserInfo? = null



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
    }


    private fun observe() {
        viewModel.getUserInfo().observe(viewLifecycleOwner) {
            if (it != null) {
                this.userInfo = it
            }
        }

        binding.adapter = ordersAdapter

        val ordersList = ArrayList<OrderList>()
        ordersList.add(OrderList())
        ordersList.add(OrderList())
        ordersList.add(OrderList())
        ordersList.add(OrderList())
        ordersList.add(OrderList())
        ordersList.add(OrderList())
        ordersList.add(OrderList())
        ordersList.add(OrderList())
        ordersAdapter.addOrders(ordersList, object : OrdersListener {
            override fun onOrdersClick(orderList: OrderList) {
                val bundle = Bundle()
                bundle.putString("Amount", "100")
                navigate(R.id.navigation_order_status, bundle)
            }
        })


        viewModel.isLoading.observe(viewLifecycleOwner){
            isLoading(it)
        }
    }



    override fun createViewModel(): OrderStatusFragmentViewModel {
        return ViewModelProvider(this, viewModelFactory).get(OrderStatusFragmentViewModel::class.java)
    }

    override fun getLayout(): Int {
        return R.layout.fragment_orders
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
        }
    }







}