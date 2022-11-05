package com.iitms.bfr.ui.view.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.iitms.bfr.R
import com.iitms.bfr.data.model.CategoryList
import com.iitms.bfr.data.model.UserInfo
import com.iitms.bfr.databinding.CategoryFragmentBinding
import com.iitms.bfr.ui.adapter.CategoryAdapter
import com.iitms.bfr.ui.base.BaseFragment
import com.iitms.bfr.ui.listener.CategoryListener
import com.iitms.bfr.ui.viewModel.HomeFragmentViewModel
import javax.inject.Inject

class CategoryFragment : BaseFragment<HomeFragmentViewModel,CategoryFragmentBinding>() {



    @Inject
    lateinit var categoryAdapter: CategoryAdapter

    private var userInfo: UserInfo? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
    }

    private fun observe() {
        viewModel.getCategory()
        viewModel.categoryList.observe(viewLifecycleOwner, Observer {
            if(it?.categoryList != null && it.categoryList.isNotEmpty()){
                binding.categoryAdapter = categoryAdapter
                categoryAdapter.addCategory(it.categoryList , object : CategoryListener {
                    override fun onCategoryClick(categoryList: CategoryList) {
                        navigate(R.id.navigation_sub_category, null)
                    }

                })
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner){
            isLoading(it)
        }
    }


    override fun createViewModel(): HomeFragmentViewModel {
       return ViewModelProvider(this,viewModelFactory).get(HomeFragmentViewModel::class.java)
    }

    override fun getLayout(): Int {
        return R.layout.fragment_category
    }

}