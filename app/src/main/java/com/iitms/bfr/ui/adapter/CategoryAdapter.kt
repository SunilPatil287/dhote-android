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
import com.iitms.bfr.databinding.CategoryAdapterBinding
import com.iitms.bfr.ui.listener.CategoryListener
import javax.inject.Inject

class CategoryAdapter @Inject constructor() :
    RecyclerView.Adapter<CategoryAdapter.CourseViewHolder>() {

    var mContext : Context? = null
    var categoryList = ArrayList<CategoryList>()

    lateinit var listner : CategoryListener


    class CourseViewHolder(var binding: CategoryAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding: CategoryAdapterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_category, parent, false
        )
        mContext = parent.context
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val data = categoryList[position]

        holder.binding.tvCategoryName.text = data.name
        holder.binding.tvDescription.text = data.description
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

    private fun setVisibilityGone(holder: CourseViewHolder) {
        holder.itemView.visibility = View.GONE
        val params = holder.itemView.layoutParams
        params.height = 0
        params.width = 0
        holder.itemView.layoutParams = params
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