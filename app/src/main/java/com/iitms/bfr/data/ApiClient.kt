package com.iitms.bfr.data

import com.iitms.bfr.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiClient {


    @POST("user/customerLogin/{phone}")
    suspend fun login(
        @Path(value = "phone")  phone_number : String,
        @Header("TenantId") tenantId: String,
        @Header("Authorization") authorization: String
    ): Response<LoginData>


    @POST("user/validateUser/{phone}/{otp}")
    @FormUrlEncoded
    suspend fun verifyOtp(
        @Path(value = "phone")  phone_number : String,
        @Path(value = "otp")  otp : String,
    ): Response<LoginData>



    @GET("query/category")
    suspend fun getCategory(
        @Header("TenantId") tenantId: String,
        @Header("Authorization") authorization: String
    ): Response<Category>



    @POST("user/")
    suspend fun saveUserDate(
        @Header("TenantId") tenantId: String,
        @Header("Authorization") authorization: String,
        @Body user: User
    ): Response<User>


    @PATCH("user/{userId}")
    suspend fun updateUserData(
        @Header("TenantId") tenantId: String,
        @Header("Authorization") authorization: String,
        @Path(value = "userId")  userId : String,
        @Body user: User
    ): Response<User>


    @GET("query/user/{userId}")
    suspend fun getUserData(
        @Header("TenantId") tenantId: String,
        @Header("Authorization") authorization: String,
        @Path(value = "userId")  userId : String,
    ): Response<UserData>




    @GET("query/product")
    suspend fun getSubCategory(
        @Header("TenantId") tenantId: String,
        @Header("Authorization") authorization: String,
        @Query("order") order : String,
        @Query("orderBy") orderBy : String
    ): Response<SubCategory>



}