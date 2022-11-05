package com.iitms.bfr.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.iitms.bfr.R
import com.iitms.bfr.databinding.CommonSpinnerAdapterBinding

import javax.inject.Inject

class CommonSpinnerAdapter @Inject constructor() :
    RecyclerView.Adapter<CommonSpinnerAdapter.CommonSpinnerViewHolder>() {

    private var spinnerData = ArrayList<String>()
    lateinit var listener: SpinnerItemClickListener

    inner class CommonSpinnerViewHolder(var binding: CommonSpinnerAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonSpinnerViewHolder {
        val binding: CommonSpinnerAdapterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_common_spinner, parent, false
        )
        return CommonSpinnerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommonSpinnerViewHolder, position: Int) {
        holder.binding.tvName.text = spinnerData[position]
        holder.binding.tvName.setOnClickListener { listener.onItemClick(spinnerData[position]) }
    }

    override fun getItemCount(): Int {
        return spinnerData.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addData(spinnerData: ArrayList<String>, listener: SpinnerItemClickListener) {
        this.spinnerData = spinnerData
        this.listener = listener
        notifyDataSetChanged()

    }

    interface SpinnerItemClickListener {
        fun onItemClick(value: String)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(spinnerData: ArrayList<String>) {
        this.spinnerData = spinnerData
        notifyDataSetChanged()

    }
}