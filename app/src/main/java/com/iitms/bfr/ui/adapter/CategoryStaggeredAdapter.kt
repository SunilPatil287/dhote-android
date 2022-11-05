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
import com.iitms.bfr.databinding.CategoryStaggeredAdapterBinding
import com.iitms.bfr.ui.listener.CategoryListener
import javax.inject.Inject

class CategoryStaggeredAdapter @Inject constructor() :
    RecyclerView.Adapter<CategoryStaggeredAdapter.CourseViewHolder>() {

    var mContext : Context? = null
    var categoryList = ArrayList<CategoryList>()
    var listner : CategoryListener? =null
    var positionState: Int = 0


    class CourseViewHolder(var binding: CategoryStaggeredAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding: CategoryStaggeredAdapterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_category_staggered_list, parent, false
        )
        mContext = parent.context
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val data = categoryList[position]
        holder.binding.tvCategoryName.text = data.name
        if(data.statusState){
            positionState = position
            holder.binding.cvMain.background = (ContextCompat.getDrawable(mContext!!, R.drawable.rounded_background_half_circle_color_primary))
            holder.binding.tvCategoryName.setTextColor(ContextCompat.getColor(mContext!!, R.color.colorWhite))
        }else{
            holder.binding.cvMain.background = (ContextCompat.getDrawable(mContext!!, R.drawable.rounded_background_half_circle_white))
            holder.binding.tvCategoryName.setTextColor(ContextCompat.getColor(mContext!!, R.color.divider))
        }
        holder.binding.cvMain.setOnClickListener {
            categoryList[positionState].statusState = false
            data.statusState = true
            notifyDataSetChanged()
            listner!!.onCategoryClick(data)
        }
    }
    
    override fun getItemCount(): Int {
        return categoryList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addCategory(list: ArrayList<CategoryList>, listener: CategoryListener) {
        this.categoryList = list
        this.listner = listener
        notifyDataSetChanged()
    }





}