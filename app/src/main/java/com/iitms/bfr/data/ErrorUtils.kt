package com.iitms.bfr.data

import com.iitms.bfr.data.model.Status
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import javax.inject.Inject

class ErrorUtils @Inject constructor(var retrofit: Retrofit) {

    internal fun parseError(response: Response<*>): Status {
        val converter = retrofit.responseBodyConverter<Status>(
            Status::class.java,
            arrayOfNulls<Annotation>(0)
        )
        val error: Status
        try {
            error = converter.convert(response.errorBody()!!)!!
        } catch (e: IOException) {
            return Status()
        }
        return error
    }


}