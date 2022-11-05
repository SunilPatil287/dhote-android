package com.iitms.bfr.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iitms.bfr.data.Resource
import com.iitms.bfr.data.model.*
import com.iitms.bfr.data.repository.CartFragmentRepository
import com.iitms.bfr.data.repository.HomeFragmentRepository
import com.iitms.bfr.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CartFragmentViewModel @Inject constructor(val repository : CartFragmentRepository) : BaseViewModel() {

    val categoryList = MutableLiveData<Category>()


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





}