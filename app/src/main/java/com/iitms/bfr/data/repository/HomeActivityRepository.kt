package com.iitms.bfr.data.repository

import androidx.lifecycle.LiveData
import com.iitms.bfr.data.ApiClient
import com.iitms.bfr.data.db.dao.UserInfoDao
import com.iitms.bfr.data.model.UserInfo
import javax.inject.Inject

class HomeActivityRepository @Inject constructor(
    private val userInfoDao: UserInfoDao,
    private val apiClient: ApiClient
) : BaseRepository() {

    fun getUserInfo() : LiveData<UserInfo> = userInfoDao.getUserInfo()


}