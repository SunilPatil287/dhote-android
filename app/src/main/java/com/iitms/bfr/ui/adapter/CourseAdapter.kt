package com.iitms.bfr.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.iitms.bfr.R
import com.iitms.bfr.data.model.Course
import com.iitms.bfr.databinding.CourseAdapterBinding
import javax.inject.Inject

class CourseAdapter @Inject constructor() :
    RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    var courseList = ArrayList<Course>()


    class CourseViewHolder(var binding: CourseAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding: CourseAdapterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_course, parent, false
        )
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val data = courseList[position]
        holder.binding.data = data

    }

    override fun getItemCount(): Int {
        return if(courseList.size > 4) 4 else courseList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addCourse(list: ArrayList<Course>) {
        this.courseList = list
        notifyDataSetChanged()
    }





}