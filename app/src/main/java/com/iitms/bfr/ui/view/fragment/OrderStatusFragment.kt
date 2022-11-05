package com.iitms.bfr.ui.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.iitms.bfr.R
import com.iitms.bfr.data.model.User
import com.iitms.bfr.data.model.UserInfo
import com.iitms.bfr.databinding.OrderStatusFragmentBinding
import com.iitms.bfr.ui.adapter.MoreCouponAdapter
import com.iitms.bfr.ui.base.BaseFragment
import com.iitms.bfr.ui.viewModel.OrderStatusFragmentViewModel
import javax.inject.Inject


class OrderStatusFragment @Inject constructor() :
    BaseFragment<OrderStatusFragmentViewModel, OrderStatusFragmentBinding>(), View.OnClickListener {

    @Inject
    lateinit var moreCouponAdapter: MoreCouponAdapter
    var user : User? = null


    var amount : String? =null

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(requireArguments().getString("Amount") != null){
            amount = requireArguments().getString("Amount")
            binding.tvTotalPay.text = resources.getString(R.string.rupee) + amount
        }
        observe()
    }


    private fun observe() {
        viewModel.getCategory()

        viewModel.getUserInfo().observe(viewLifecycleOwner) {
            if (it != null) {
                user = Gson().fromJson(it.listToJson, User::class.java)
                if(user != null) {

                }
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner){
            isLoading(it)
        }

        viewModel.categoryList.observe(viewLifecycleOwner) {
            if (it != null) {
  /*              binding.adapter = moreCouponAdapter
                moreCouponAdapter.addCategory(it.categoryList, object : CategoryListener {
                    override fun onCategoryClick(categoryList: CategoryList) {

                    }
                })*/
            }
        }

/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.chkStudent.buttonTintList = ContextCompat.getColorStateList(this@RegistrationActivity, R.color.green)
            binding.chkFaculty.buttonTintList = ContextCompat.getColorStateList(this@RegistrationActivity, R.color.gray)
        }
*/


    }



    override fun createViewModel(): OrderStatusFragmentViewModel {
        return ViewModelProvider(this, viewModelFactory).get(OrderStatusFragmentViewModel::class.java)
    }

    override fun getLayout(): Int {
        return R.layout.fragment_order_status
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
        }
    }







}