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
import com.iitms.bfr.data.model.SubCategoryDetail
import com.iitms.bfr.databinding.SubCategoryAdapterBinding
import com.iitms.bfr.ui.listener.SubCategoryListener
import javax.inject.Inject

class SubCategoryAdapter @Inject constructor() :
    RecyclerView.Adapter<SubCategoryAdapter.CourseViewHolder>() {

    var mContext : Context? = null
    var subCategoryList = ArrayList<SubCategoryDetail>()
    var listner : SubCategoryListener? =null
    var positionState: Int = 0


    class CourseViewHolder(var binding: SubCategoryAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding: SubCategoryAdapterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_sub_category, parent, false
        )
        mContext = parent.context
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val data = subCategoryList[position]

        holder.binding.tvSubCategoryName.text = data.name
        if(data.href != null && data.href!!.isNotEmpty()){
            Glide.with(mContext!!)
                .load(data.href)
                .centerCrop()
                .error(R.drawable.ic_chicken_logo)
                .into(holder.binding.ivSubCategory)
        }else{
            holder.binding.ivSubCategory.setImageDrawable(ContextCompat.getDrawable(mContext!!, R.drawable.ic_chicken_logo))
        }

        holder.itemView.setOnClickListener {
            listner!!.onSubCategoryClick(data)
        }

    }
    
    override fun getItemCount(): Int {
        return subCategoryList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addSubCategory(list: ArrayList<SubCategoryDetail>, listener: SubCategoryListener) {
        this.subCategoryList = list
        this.listner = listener
        notifyDataSetChanged()
    }





}