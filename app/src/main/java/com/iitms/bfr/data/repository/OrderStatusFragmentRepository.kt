package com.iitms.bfr.data.repository

import androidx.lifecycle.LiveData
import com.iitms.bfr.data.ApiClient
import com.iitms.bfr.data.db.dao.UserInfoDao
import com.iitms.bfr.data.model.UserInfo
import javax.inject.Inject

class OrderStatusFragmentRepository @Inject constructor(
    private val apiClient: ApiClient,
    private val userInfoDao: UserInfoDao,
) : BaseRepository() {


    fun getUserInfo(): LiveData<UserInfo> {
        return userInfoDao.getUserInfo()
    }


    suspend  fun getCategory() = safeApiCall {
        apiClient.getCategory("Android","test")
    }
}