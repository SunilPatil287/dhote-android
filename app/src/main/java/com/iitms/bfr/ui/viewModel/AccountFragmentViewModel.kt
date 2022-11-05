package com.iitms.bfr.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iitms.bfr.data.Resource
import com.iitms.bfr.data.model.User
import com.iitms.bfr.data.model.UserData
import com.iitms.bfr.data.model.UserInfo
import com.iitms.bfr.data.repository.AccountRepository
import com.iitms.bfr.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountFragmentViewModel @Inject constructor(var repository: AccountRepository) :
    BaseViewModel() {


    val user = MutableLiveData<User>()

    fun getUserInfo(): LiveData<UserInfo> {
        return repository.getUserInfo()
    }


    fun getUserData(userId: String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            when (val responseData = repository.getUserData(
                userId
            )) {
                is Resource.Loading -> {
                    val isLoading: Boolean = responseData.value
                }
                is Resource.Success -> {
                    isLoading.postValue(false)
                    user.postValue(responseData.value.user!!)
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