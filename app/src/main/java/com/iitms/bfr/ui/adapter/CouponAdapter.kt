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
import com.iitms.bfr.data.model.CategoryList
import com.iitms.bfr.databinding.CouponAdapterBinding
import com.iitms.bfr.ui.listener.CategoryListener
import javax.inject.Inject

class CouponAdapter @Inject constructor() :
    RecyclerView.Adapter<CouponAdapter.CourseViewHolder>() {

    var mContext : Context? = null
    var couponList = ArrayList<CategoryList>()

    lateinit var listner : CategoryListener


    class CourseViewHolder(var binding: CouponAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding: CouponAdapterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_coupon, parent, false
        )
        mContext = parent.context
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val data = couponList[position]

        holder.itemView.setOnClickListener {
            listner.onCategoryClick(data)
        }

    }


    override fun getItemCount(): Int {
        return couponList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addCategory(list: ArrayList<CategoryList>, listner : CategoryListener) {
        this.couponList = list
        this.listner = listner
        notifyDataSetChanged()
    }





}