package com.iitms.bfr.ui.view.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.iitms.bfr.R
import com.iitms.bfr.data.model.CategoryList
import com.iitms.bfr.data.model.UserInfo
import com.iitms.bfr.databinding.MoreCouponFragmentBinding
import com.iitms.bfr.ui.adapter.MoreCouponAdapter
import com.iitms.bfr.ui.base.BaseFragment
import com.iitms.bfr.ui.listener.CategoryListener
import com.iitms.bfr.ui.viewModel.CartFragmentViewModel
import javax.inject.Inject


class ApplyCouponFragment @Inject constructor() :
    BaseFragment<CartFragmentViewModel, MoreCouponFragmentBinding>(), View.OnClickListener {

    @Inject
    lateinit var moreCouponAdapter: MoreCouponAdapter

    private var userInfo: UserInfo? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
    }


    private fun observe() {
        viewModel.getCategory()

        viewModel.getUserInfo().observe(viewLifecycleOwner) {
            if (it != null) {
                this.userInfo = it
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner){
            isLoading(it)
        }

        viewModel.categoryList.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.adapter = moreCouponAdapter
                moreCouponAdapter.addCategory(it.categoryList, object : CategoryListener {
                    override fun onCategoryClick(categoryList: CategoryList) {

                    }
                })
            }
        }

    }



    override fun createViewModel(): CartFragmentViewModel {
        return ViewModelProvider(this, viewModelFactory).get(CartFragmentViewModel::class.java)
    }

    override fun getLayout(): Int {
        return R.layout.fragment_apply_coupon
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
        }
    }







}