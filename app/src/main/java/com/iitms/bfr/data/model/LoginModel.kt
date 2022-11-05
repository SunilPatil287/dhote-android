package com.iitms.bfr.data.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import javax.inject.Inject

class LoginModel @Inject constructor() : BaseObservable() {

    var userName: String? = null
        @Bindable get
        set(userName) {
            field = userName
            notifyPropertyChanged(BR.userName)
        }
    var userPassWord: String? = null
        @Bindable get
        set(userPassWord) {
            field = userPassWord
            notifyPropertyChanged(BR.userPassWord)
        }


    fun isValidInput(): String {
        return when {
            userName.isNullOrEmpty() -> "User Name Required!"
            userPassWord.isNullOrEmpty() -> "Password Required!"
            else -> "SUCCESS"
        }
    }
}