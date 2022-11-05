package com.iitms.bfr.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.iitms.bfr.data.Resource
import com.iitms.bfr.data.model.User
import com.iitms.bfr.data.model.UserInfo
import com.iitms.bfr.data.repository.AddressRepository
import com.iitms.bfr.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddressViewModel @Inject constructor(val repository: AddressRepository) : BaseViewModel(){



    val userDataInit = MutableLiveData<User>()

    fun getUserInfo(): LiveData<UserInfo> {
        return repository.getUserInfo()
    }


    fun updateUserData(user: User) {
        viewModelScope.launch {
            isLoading.postValue(true)
            when (val responseData = repository.updateUserData(
                user
            )) {
                is Resource.Loading -> {
                    val isLoading: Boolean = responseData.value
                }
                is Resource.Success -> {
                    isLoading.postValue(false)
                    if(responseData.value.familyName != null && responseData.value.familyName!!.isNotEmpty()){
                        val userInfo = UserInfo()
                        userInfo.listToJson = Gson().toJson(responseData.value).toString()
                        saveUserInfo(userInfo)
                        userDataInit.postValue(responseData.value!!)
                    }

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

    private fun saveUserInfo(data: UserInfo?) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) { repository.deleteUserInfo() }
            if (data != null) {
                withContext(Dispatchers.Default) { repository.saveUserInfo(data!!) }
            }
        }
    }

}