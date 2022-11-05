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
import com.iitms.bfr.databinding.MoreCouponAdapterBinding
import com.iitms.bfr.ui.listener.CategoryListener
import javax.inject.Inject

class MoreCouponAdapter @Inject constructor() :
    RecyclerView.Adapter<MoreCouponAdapter.CourseViewHolder>() {

    var mContext : Context? = null
    var moreCouponList = ArrayList<CategoryList>()

    lateinit var listner : CategoryListener


    class CourseViewHolder(var binding: MoreCouponAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding: MoreCouponAdapterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_more_coupon, parent, false
        )
        mContext = parent.context
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val data = moreCouponList[position]

        holder.itemView.setOnClickListener {
            listner.onCategoryClick(data)
        }

    }


    override fun getItemCount(): Int {
        return moreCouponList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addCategory(list: ArrayList<CategoryList>, listner : CategoryListener) {
        this.moreCouponList = list
        this.listner = listner
        notifyDataSetChanged()
    }





}