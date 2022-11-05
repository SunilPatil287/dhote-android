package com.iitms.bfr.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.iitms.bfr.R
import com.iitms.bfr.data.model.DeliveryTime
import com.iitms.bfr.databinding.DeliveryTimeAdapterBinding
import com.iitms.bfr.ui.listener.DeliveryTimeDialogListener
import javax.inject.Inject

class DeliveryTimeAdapter @Inject constructor() :
    RecyclerView.Adapter<DeliveryTimeAdapter.CourseViewHolder>() {

    var mContext : Context? = null
    var deliveryTimeList = ArrayList<DeliveryTime>()
    var listner : DeliveryTimeDialogListener ? =null
    var positionState: Int = 0


    class CourseViewHolder(var binding: DeliveryTimeAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding: DeliveryTimeAdapterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_delivery_time, parent, false
        )
        mContext = parent.context
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val data = deliveryTimeList[position]
        holder.binding.tvTimeName.text = data.time

        if(data.status){
            positionState = position
            holder.binding.cvMain.background = (ContextCompat.getDrawable(mContext!!, R.drawable.rounded_background_half_circle_color_primary))
            holder.binding.tvTimeName.setTextColor(ContextCompat.getColor(mContext!!, R.color.colorWhite))
        }else{
            holder.binding.cvMain.background = (ContextCompat.getDrawable(mContext!!, R.drawable.rounded_background_half_circle_white))
            holder.binding.tvTimeName.setTextColor(ContextCompat.getColor(mContext!!, R.color.divider))
        }
        holder.binding.cvMain.setOnClickListener {
            deliveryTimeList[positionState].status = false
            data.status = true
            notifyDataSetChanged()
            listner!!.onDeliveryTimeClick(data)
        }
    }


    override fun getItemCount(): Int {
        return deliveryTimeList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addDeliveryTime(list: ArrayList<DeliveryTime>, listner :DeliveryTimeDialogListener) {
        this.deliveryTimeList = list
        this.listner = listner
        notifyDataSetChanged()
    }





}