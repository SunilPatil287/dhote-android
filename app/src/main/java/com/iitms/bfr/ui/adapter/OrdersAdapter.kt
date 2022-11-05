package com.iitms.bfr.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iitms.bfr.R
import com.iitms.bfr.data.model.OrderList
import com.iitms.bfr.databinding.OrdersAdapterBinding
import com.iitms.bfr.ui.listener.CategoryListener
import com.iitms.bfr.ui.listener.OrdersListener
import javax.inject.Inject

class OrdersAdapter @Inject constructor() :
    RecyclerView.Adapter<OrdersAdapter.CourseViewHolder>() {

    var mContext : Context? = null
    var orderList = ArrayList<OrderList>()

    lateinit var listner : OrdersListener


    class CourseViewHolder(var binding: OrdersAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding: OrdersAdapterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_orders, parent, false
        )
        mContext = parent.context
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val data = orderList[position]


        holder.itemView.setOnClickListener {
            listner.onOrdersClick(data)
        }

    }


    override fun getItemCount(): Int {
        return orderList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addOrders(list: ArrayList<OrderList>, listner : OrdersListener) {
        this.orderList = list
        this.listner = listner
        notifyDataSetChanged()
    }





}