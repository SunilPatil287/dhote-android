package com.iitms.bfr.ui.view.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.iitms.bfr.R
import com.iitms.bfr.data.model.CategoryList
import com.iitms.bfr.data.model.SubCategoryDetail
import com.iitms.bfr.data.model.UserInfo
import com.iitms.bfr.databinding.SubCategoryFragmentBinding
import com.iitms.bfr.ui.adapter.CategoryAdapter
import com.iitms.bfr.ui.adapter.CategoryStaggeredAdapter
import com.iitms.bfr.ui.adapter.SubCategoryAdapter
import com.iitms.bfr.ui.base.BaseFragment
import com.iitms.bfr.ui.listener.CategoryListener
import com.iitms.bfr.ui.listener.SubCategoryListener
import com.iitms.bfr.ui.viewModel.SubCategoryViewModel
import javax.inject.Inject

class SubCategoryFragment : BaseFragment<SubCategoryViewModel, SubCategoryFragmentBinding>() {


    @Inject
    lateinit var categoryStaggeredAdapter: CategoryStaggeredAdapter

    @Inject
    lateinit var subCategoryAdapter: SubCategoryAdapter

    private var userInfo: UserInfo? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
    }

    private fun observe() {
        viewModel.getCategory()
        viewModel.getSubCategory()
        viewModel.categoryList.observe(viewLifecycleOwner, Observer {
            if (it?.categoryList != null && it.categoryList.isNotEmpty()) {
                binding.categoryStaggeredAdapter = categoryStaggeredAdapter
                categoryStaggeredAdapter.addCategory(it.categoryList, object : CategoryListener {
                    override fun onCategoryClick(categoryList: CategoryList) {
//                        viewModel.getSubCategory()
                        if ((categoryList.productList != null && categoryList.productList.isNotEmpty())) {
                            binding.subCategoryAdapter = subCategoryAdapter
                            subCategoryAdapter.addSubCategory(
                                categoryList.productList,
                                object : SubCategoryListener {
                                    override fun onSubCategoryClick(subCategoryDetail: SubCategoryDetail) {
                                        navigate(R.id.navigation_sub_category_detail, null)
                                    }
                                })

                        }else{
                            binding.subCategoryAdapter = null
                        }
                    }
                })

            }
        })


        viewModel.isLoading.observe(viewLifecycleOwner){
            isLoading(it)
        }
/*
        viewModel.subCategoryList.observe(viewLifecycleOwner, Observer {
            if(it?.subCategory != null && it.subCategory!!.isNotEmpty()){
                binding.subCategoryAdapter = subCategoryAdapter
                subCategoryAdapter.addSubCategory(it.subCategory as ArrayList<SubCategoryDetail>, object : SubCategoryListener {
                    override fun onSubCategoryClick(subCategoryDetail: SubCategoryDetail) {
                        navigate(R.id.navigation_sub_category_detail, null)
                    }

                })
            }
        })
*/

    }


    override fun createViewModel(): SubCategoryViewModel {
        return ViewModelProvider(this, viewModelFactory).get(SubCategoryViewModel::class.java)
    }

    override fun getLayout(): Int {
        return R.layout.fragment_sub_category
    }

}