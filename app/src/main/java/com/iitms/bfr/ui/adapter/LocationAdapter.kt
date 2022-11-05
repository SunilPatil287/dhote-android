package com.iitms.bfr.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.iitms.bfr.R
import com.iitms.bfr.data.model.Place
import com.iitms.bfr.databinding.LocationAdapterBinding
import com.iitms.bfr.ui.listener.LocationListener
import javax.inject.Inject


class LocationAdapter @Inject constructor() :
    RecyclerView.Adapter<LocationAdapter.CourseViewHolder>() {

    var mContext : Context? = null
    var locationList = ArrayList<Place>()

    lateinit var listner : LocationListener


    class CourseViewHolder(var binding: LocationAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding: LocationAdapterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_location, parent, false
        )
        mContext = parent.context
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val data = locationList[position]
        holder.binding.data = data
        holder.itemView.setOnClickListener {
            listner.onLocationClick(data)
        }

        holder.binding.ivMenu.setOnClickListener {
            val popupMenu = PopupMenu(mContext , holder.binding.ivMenu)
            popupMenu.inflate(com.iitms.bfr.R.menu.options_menu)
            popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    when(item?.itemId){
                        R.id.menu_delete -> {
                           Toast.makeText(mContext,"Delete Address", Toast.LENGTH_LONG).show()
                            return true
                        }
                        R.id.menu_edit -> {
                            Toast.makeText(mContext,"Edit Address", Toast.LENGTH_LONG).show()
                            return true
                        }
                    }
                    return false
                }
            })
            popupMenu.show()
        }

    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addLocation(list: ArrayList<Place>, listner : LocationListener) {
        this.locationList = list
        this.listner = listner
        notifyDataSetChanged()
    }





}