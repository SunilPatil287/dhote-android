package com.iitms.bfr.ui.view.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.iitms.bfr.R
import com.iitms.bfr.data.model.DeliveryTime
import com.iitms.bfr.data.model.Place
import com.iitms.bfr.data.model.User
import com.iitms.bfr.data.model.UserInfo
import com.iitms.bfr.databinding.CartFragmentBinding
import com.iitms.bfr.databinding.DialogDeliveryTimeBinding
import com.iitms.bfr.databinding.DialogSelectLocationBinding
import com.iitms.bfr.ui.adapter.DeliveryTimeAdapter
import com.iitms.bfr.ui.adapter.LocationAdapter
import com.iitms.bfr.ui.base.BaseFragment
import com.iitms.bfr.ui.listener.DeliveryTimeDialogListener
import com.iitms.bfr.ui.listener.LocationListener
import com.iitms.bfr.ui.view.activity.PaymentActivity
import com.iitms.bfr.ui.viewModel.CartFragmentViewModel
import java.util.ArrayList
import javax.inject.Inject


class CartFragment @Inject constructor() :
    BaseFragment<CartFragmentViewModel, CartFragmentBinding>(), View.OnClickListener {

    @Inject
    lateinit var deliveryTimeAdapter: DeliveryTimeAdapter

    @Inject
    lateinit var locationAdapter: LocationAdapter

    var user : User? = null
    private var userInfo: UserInfo? = null
    private var location: List<Place>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvDeliveryTime.setOnClickListener(this)
        binding.cvCoupon.setOnClickListener(this)
        binding.tvPickLocation.setOnClickListener(this)
        binding.ivAdd.setOnClickListener(this)
        binding.ivMinus.setOnClickListener(this)
        binding.btnPayment.setOnClickListener(this)

        observe()
    }


    private fun observe() {
        viewModel.getUserInfo().observe(viewLifecycleOwner) {
            if (it != null) {
                user = Gson().fromJson(it.listToJson, User::class.java)
                if(user != null) {
                    location = user!!.place!!
                }
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner){
            isLoading(it)
        }

    }



    override fun createViewModel(): CartFragmentViewModel {
        return ViewModelProvider(this, viewModelFactory).get(CartFragmentViewModel::class.java)
    }

    override fun getLayout(): Int {
        return R.layout.fragment_cart
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_delivery_time -> {
                val deliveryTime = DeliveryTime()
                deliveryTime.time = "10 AM - 12 PM"
                deliveryTime.timeId = "1"
                deliveryTime.status = false

                val deliveryTime1 = DeliveryTime()
                deliveryTime1.time = "11 AM - 01 PM"
                deliveryTime1.timeId = "1"
                deliveryTime1.status = false

                val deliveryTime2 = DeliveryTime()
                deliveryTime2.time = "04 AM - 05 PM"
                deliveryTime2.timeId = "1"
                deliveryTime2.status = false

                val deliveryTime3 = DeliveryTime()
                deliveryTime3.time = "06 AM - 07 PM"
                deliveryTime3.timeId = "1"
                deliveryTime3.status = false

                val deliveryTime4 = DeliveryTime()
                deliveryTime4.time = "22 AM - 22 PM"
                deliveryTime4.timeId = "1"
                deliveryTime4.status = false

                val list : ArrayList<DeliveryTime> = ArrayList()
                list.add(deliveryTime)
                list.add(deliveryTime1)
                list.add(deliveryTime2)
                list.add(deliveryTime3)
                list.add(deliveryTime4)
                openDeliveryTimeDialog(list, "Abc")
            }

            R.id.iv_add -> {
                if(binding.tvPrice.text.toString().isNotEmpty() && binding.tvCount.text.toString().toInt() > 0){
                    val oldPrice = 150
                    val increasePrice = binding.tvPrice.text.toString().toInt() + oldPrice
                    binding.tvPrice.text = increasePrice.toString()
                    binding.tvPriceBottom.text = increasePrice.toString()
                    binding.tvCount.text =  (binding.tvCount.text.toString().toInt() + 1).toString()
                }
            }

            R.id.iv_minus -> {
                if(binding.tvPrice.text.toString().isNotEmpty() && binding.tvCount.text.toString().toInt() > 0){
                    val oldPrice = 150
                    val increasePrice = binding.tvPrice.text.toString().toInt() - oldPrice

                    if(binding.tvCount.text.toString().toInt() != 1){
                        binding.tvPrice.text = increasePrice.toString()
                        binding.tvPriceBottom.text = increasePrice.toString()
                        binding.tvCount.text =  (binding.tvCount.text.toString().toInt() - 1).toString()
                    }

                }
            }

            R.id.btn_payment -> {
                startActivity(Intent(requireActivity(), PaymentActivity::class.java).putExtra("Amount", binding.tvPrice.text.toString()))
            }

            R.id.cv_coupon -> {
                navigate(R.id.navigation_apply_coupon, null)
            }

            R.id.tv_pick_location -> {
                if(location != null) {
                    openLocationPickerDialog(location as ArrayList<Place>, "")
                }
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun openDeliveryTimeDialog(
        data: ArrayList<DeliveryTime>,
        dialogTitle: String
    ) {
        val dialog = BottomSheetDialog(requireContext(), R.style.AppFixedBottomSheetDialogTheme)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding: DialogDeliveryTimeBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.dialog_delivery_time_list,
            null,
            false
        )

        dialogBinding.adapter = deliveryTimeAdapter

        dialogBinding.tabLayout.addTab(dialogBinding.tabLayout.newTab().setText(R.string.lbl_today))
        dialogBinding.tabLayout.addTab(dialogBinding.tabLayout.newTab().setText(R.string.lbl_tomorrows))


        deliveryTimeAdapter.addDeliveryTime(data, object : DeliveryTimeDialogListener {
            override fun onDeliveryTimeClick(deliveryTime: DeliveryTime) {

            }
        })


        dialog.setContentView(dialogBinding.root)
        dialog.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun openLocationPickerDialog(
        data: ArrayList<Place>,
        dialogTitle: String
    ) {
        val dialog = BottomSheetDialog(requireContext(), R.style.AppFixedBottomSheetDialogTheme)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding: DialogSelectLocationBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.dialog_select_location,
            null,
            false
        )
        dialogBinding.adapter = locationAdapter
        dialogBinding.ivClose.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.rlAddLocation.setOnClickListener {
            dialog.dismiss()
            navigate(R.id.navigation_add_address, null)
        }
        locationAdapter.addLocation(data, object : LocationListener {
            override fun onLocationClick(location: Place) {

            }

            override fun onDeleteLocation(location: Place) {
            }

            override fun onEditLocation(location: Place) {
            }
        })
        dialog.setContentView(dialogBinding.root)
        dialog.show()
    }





}