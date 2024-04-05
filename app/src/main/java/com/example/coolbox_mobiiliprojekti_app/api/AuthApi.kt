package com.example.coolbox_mobiiliprojekti_app.api

import com.example.coolbox_mobiiliprojekti_app.model.LoginDetails
import com.example.coolbox_mobiiliprojekti_app.model.LoginRes
import com.example.coolbox_mobiiliprojekti_app.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


val authService = retrofit.create(AuthApi::class.java)

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body loginReq: LoginDetails) : LoginRes

    @POST("auth/logout")
    suspend fun logout()

    @POST("auth/register")
    suspend fun register(@Body loginReq: LoginDetails) : User

    @GET("auth/user")
    suspend fun getUser() : User

}