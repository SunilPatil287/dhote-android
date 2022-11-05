package com.iitms.bfr.data

import com.iitms.bfr.data.model.Status


sealed class Resource<out T> {
    data class Loading(val value: Boolean) : Resource<Nothing>()
    data class Success<out T>(val value: T) : Resource<T>()
    data class Failure(val status: Status) : Resource<Nothing>()
    data class AuthenticationFailed(val status: Status) : Resource<Nothing>()
}