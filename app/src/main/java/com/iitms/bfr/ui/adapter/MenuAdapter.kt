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
import com.iitms.bfr.databinding.MenuAdapterBinding
import com.iitms.bfr.ui.listener.CategoryListener
import javax.inject.Inject

class MenuAdapter @Inject constructor() :
    RecyclerView.Adapter<MenuAdapter.CourseViewHolder>() {

    var mContext : Context? = null
    var menuList = ArrayList<CategoryList>()
    var listner : CategoryListener? =null
    var positionState: Int = 0


    class CourseViewHolder(var binding: MenuAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding: MenuAdapterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_menu, parent, false
        )
        mContext = parent.context
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val data = menuList[position]

        holder.binding.tvMenuName.text = data.name
        if(data.href != null && data.href!!.isNotEmpty()){
            Glide.with(mContext!!)
                .load(/*data.attachments!![0].href*/R.drawable.ic_banner)
                .centerCrop()
                .error(R.drawable.ic_banner)
                .into(holder.binding.ivBanner)
        }else{
            holder.binding.ivBanner.setImageDrawable(ContextCompat.getDrawable(mContext!!, R.drawable.ic_banner))
        }

        holder.itemView.setOnClickListener {
            listner!!.onCategoryClick(data)
        }

    }
    
    override fun getItemCount(): Int {
        return menuList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addMenu(list: ArrayList<CategoryList>, listener: CategoryListener) {
        this.menuList = list
        this.listner = listener
        notifyDataSetChanged()
    }





}