package com.iitms.bfr.data

import android.content.SharedPreferences
import com.iitms.bfr.BuildConfig
import com.iitms.bfr.ui.util.Constant

import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Amol Gahukar on 10/9/2020.
 */

@Singleton
class AuthorizationInterceptor @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var response: Response? = null
        val request = chain.request()
        try {

           /* val userType = sharedPreferences.getInt(USER_TYPE, 0)
            val schoolId = sharedPreferences.getInt(KEY_SCHOOL_ID, 0)
            val userId = if (userType == 3) sharedPreferences.getInt(KEY_STUD_UA_ID, 0)
            else sharedPreferences.getInt(KEY_REG_ID, 0)*/

            val requestBuilder = request.newBuilder()
            if (request.header("No-Authorization") == null) {

                requestBuilder.addHeader("userid", sharedPreferences.getString(Constant.USERID, "").toString())
                requestBuilder.addHeader("collegeid",  sharedPreferences.getString(Constant.COLLEGEID, "").toString())
                requestBuilder.addHeader("appUserId",  BuildConfig.APPLICATION_ID)
                requestBuilder.addHeader("CollegeRefId",  BuildConfig.APPLICATION_ID)
                requestBuilder.addHeader("Device",  "Android")
            }
            response = chain.proceed(requestBuilder.build())
        } catch (excep: SocketTimeoutException) {
            when (excep) {
                is SocketTimeoutException -> {
                    return Response.Builder()
                        .request(request)
                        .protocol(Protocol.HTTP_1_1)
                        .code(408)
                        .message("Socket timeout error")
                        .body("{${excep}}".toResponseBody(null)).build()
                }
            }
        }

        return response
    }

}