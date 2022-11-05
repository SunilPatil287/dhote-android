package com.iitms.bfr.data.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import javax.inject.Inject

/**
 * Created by Sunil Patil on 7/8/2021.
 */
class ForgetPasswordModel @Inject constructor() : BaseObservable() {

    var userName: String? = null
        @Bindable get
        set(userName) {
            field = userName
            notifyPropertyChanged(BR.userName)
        }

    fun isValidInput(): String {
        return when {
            userName.isNullOrEmpty() -> "User Name Required!"
            else -> "SUCCESS"
        }
    }


}