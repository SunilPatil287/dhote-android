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
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.iitms.bfr.R
import com.iitms.bfr.data.model.CategoryList
import com.iitms.bfr.databinding.HomeCategoryAdapterBinding
import com.iitms.bfr.ui.listener.CategoryListener
import javax.inject.Inject

class HomeCategoryAdapter @Inject constructor() :
    RecyclerView.Adapter<HomeCategoryAdapter.CourseViewHolder>() {

    var mContext : Context? = null
    var categoryList = ArrayList<CategoryList>()
    lateinit var listner : CategoryListener


    class CourseViewHolder(var binding: HomeCategoryAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding: HomeCategoryAdapterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_home_category, parent, false
        )
        mContext = parent.context
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val data = categoryList[position]

        holder.binding.tvCategoryName.text = data.name
        if(data.href != null && data.href!!.isNotEmpty()){
            Glide.with(mContext!!)
                .load(data.href)
                .centerCrop()
                .error(R.drawable.ic_chicken_logo)
                .into(holder.binding.ivCategory)
        }else{
            holder.binding.ivCategory.setImageDrawable(ContextCompat.getDrawable(mContext!!, R.drawable.ic_chicken_logo))
        }

        holder.itemView.setOnClickListener {
            listner.onCategoryClick(data)
        }
    }


    override fun getItemCount(): Int {
        return categoryList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addCategory(list: ArrayList<CategoryList>, listner : CategoryListener) {
        this.categoryList = list
        this.listner = listner
        notifyDataSetChanged()
    }




}