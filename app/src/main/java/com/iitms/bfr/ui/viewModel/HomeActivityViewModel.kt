package com.iitms.bfr.ui.viewModel

import androidx.lifecycle.LiveData
import com.iitms.bfr.data.model.UserInfo
import com.iitms.bfr.data.repository.HomeActivityRepository
import com.iitms.bfr.ui.base.BaseViewModel
import javax.inject.Inject

class HomeActivityViewModel  @Inject constructor(private val repository: HomeActivityRepository
) : BaseViewModel() {

    fun getUserInfo(): LiveData<UserInfo> = repository.getUserInfo()



}