package com.iitms.bfr.data.repository

import androidx.lifecycle.LiveData
import com.iitms.bfr.data.ApiClient
import com.iitms.bfr.data.db.dao.UserInfoDao
import com.iitms.bfr.data.model.User
import com.iitms.bfr.data.model.UserInfo
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val apiClient: ApiClient,
    private val userInfoDao: UserInfoDao,
) : BaseRepository() {


    fun getUserInfo(): LiveData<UserInfo> {
        return userInfoDao.getUserInfo()
    }

    suspend fun saveUserInfo(userInfo: UserInfo) = userInfoDao.insertUserData(userInfo)

    fun deleteUserInfo() = userInfoDao.deleteUserInfo()


    suspend fun getUserData(userId : String) = safeApiCall {
        apiClient.getUserData("Android","test", userId)
    }

    suspend fun saveUserDate(user : User) = safeApiCall {
        apiClient.saveUserDate("Android","test", user)
    }
}