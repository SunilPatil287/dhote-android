package com.iitms.bfr.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iitms.bfr.data.model.Initial
import com.iitms.bfr.data.model.UserInfo
import com.iitms.bfr.data.repository.OtpVerificationRepository
import com.iitms.bfr.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OtpVerificationViewModel @Inject constructor(val repository : OtpVerificationRepository) : BaseViewModel() {

    val initial = MutableLiveData<Initial>()

/*
    fun login(loginModel: LoginModel) {
        viewModelScope.launch {
            isLoading.postValue(true)
            when (val responseData = repository.login(
                loginModel.userName.toString(),
                loginModel.userPassWord.toString()
            )) {
                is Resource.Loading -> {
                    val isLoading: Boolean = responseData.value
                }
                is Resource.Success -> {
                    isLoading.postValue(false)
                    saveUserInfo(responseData.value.userInfo)
                    initial.postValue(responseData.value!!)
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
*/

    private fun saveUserInfo(data: UserInfo?) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) { repository.deleteUserInfo() }
            if (data != null) {
                withContext(Dispatchers.Default)  { repository.saveUserInfo(data!!) }
            }
        }
    }

}