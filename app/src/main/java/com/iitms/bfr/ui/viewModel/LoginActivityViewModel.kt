package com.iitms.bfr.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iitms.bfr.data.Resource
import com.iitms.bfr.data.model.Initial
import com.iitms.bfr.data.model.LoginData
import com.iitms.bfr.data.model.LoginModel
import com.iitms.bfr.data.model.UserInfo
import com.iitms.bfr.data.repository.LoginRepository
import com.iitms.bfr.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginActivityViewModel @Inject constructor(val repository : LoginRepository) : BaseViewModel() {

    val initial = MutableLiveData<LoginData>()

    fun login(phoneNumber: String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            when (val responseData = repository.login(
                phoneNumber
            )) {
                is Resource.Loading -> {
                    val isLoading: Boolean = responseData.value
                }
                is Resource.Success -> {
                    isLoading.postValue(false)
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

    private fun saveUserInfo(data: UserInfo?) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) { repository.deleteUserInfo() }
            if (data != null) {
                withContext(Dispatchers.Default)  { repository.saveUserInfo(data!!) }
            }
        }
    }

}