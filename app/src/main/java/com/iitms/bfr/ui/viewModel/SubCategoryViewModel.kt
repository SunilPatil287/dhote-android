package com.iitms.bfr.ui.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iitms.bfr.data.Resource
import com.iitms.bfr.data.model.*
import com.iitms.bfr.data.repository.HomeFragmentRepository
import com.iitms.bfr.data.repository.SubCategoryRepository
import com.iitms.bfr.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SubCategoryViewModel @Inject constructor(val repository : SubCategoryRepository) : BaseViewModel() {

    var categoryList = MutableLiveData<Category>()
    var subCategoryList = MutableLiveData<SubCategory>()

    var smallSizeMenu = ObservableField<Boolean>(true)


    fun getUserInfo() : LiveData<UserInfo> {
        return repository.getUserInfo()
    }

    fun getCategory() {
        viewModelScope.launch {
            isLoading.postValue(true)
            when (val responseData = repository.getCategory()) {
                is Resource.Loading -> {
                    val isLoading: Boolean = responseData.value
                }
                is Resource.Success -> {
                    isLoading.postValue(false)
                    categoryList.postValue(responseData.value!!)
                }
                is Resource.Failure -> {
                    isLoading.postValue(false)
                    failed.postValue(responseData.status)
                }
                is Resource.AuthenticationFailed -> {
                    isLoading.postValue(false)
                    failed.postValue(responseData.status)
                }
            }
        }

    }
    fun getSubCategory() {
        viewModelScope.launch {
            isLoading.postValue(true)
            when (val responseData = repository.getSubCategory()) {
                is Resource.Loading -> {
                    val isLoading: Boolean = responseData.value
                }
                is Resource.Success -> {
                    isLoading.postValue(false)
                    subCategoryList.postValue(responseData.value!!)
                }
                is Resource.Failure -> {
                    isLoading.postValue(false)
                    failed.postValue(responseData.status)
                }
                is Resource.AuthenticationFailed -> {
                    isLoading.postValue(false)
                    failed.postValue(responseData.status)
                }
            }
        }

    }



}