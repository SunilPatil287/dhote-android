package com.iitms.bfr.data.repository

import com.iitms.bfr.data.ErrorUtils
import com.iitms.bfr.data.Resource
import com.iitms.bfr.data.model.Status
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection
import javax.inject.Inject

open class BaseRepository {

    @Inject
    lateinit var status: Status

    @Inject
    lateinit var errorUtils: ErrorUtils

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>) : Resource<T> {
        return try {
            Resource.Loading(true)
            val myResp = call.invoke()
            if (myResp.isSuccessful) {
                Resource.Success(myResp.body()!!)
            } else {
                status = errorUtils.parseError(myResp)
                status.responseCode = myResp.code()
                Resource.Failure(status)
            }

        } catch (e: Exception) {
            if (e is HttpException) {
                if (e.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    val responseBody = e.response()
                    status = errorUtils.parseError(responseBody!!)
                    status.responseCode = e.code()
                    Resource.AuthenticationFailed(status)
                } else if (e.code() == HttpURLConnection.HTTP_NOT_FOUND) {
                    status.message = e.message
                    status.responseCode = e.code()
                    Resource.Failure(internalServerError(status))
                } else {
                    val responseBody = e.response()
                    status = errorUtils.parseError(responseBody!!)
                    status.responseCode = e.code()
                    Resource.Failure(status)
                }
            } else {
                status.message = e.message
                Resource.Failure(status)
            }
        }
    }

    private fun internalServerError(status: Status): Status {
        status.message = "Internal Server Error"
        return status
    }


}