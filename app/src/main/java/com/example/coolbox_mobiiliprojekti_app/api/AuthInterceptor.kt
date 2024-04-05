package com.example.coolbox_mobiiliprojekti_app.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response


val authInterceptor = AuthInterceptor()

// Create OkHttpClient with AuthInterceptor instance. This is used in Retrofit builder
val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(authInterceptor)
    .build()


class AuthInterceptor(private var token: String = "") : Interceptor {

    // Set token on login.
    fun setToken(newToken: String) {
        this.token = newToken
    }

    // Clear token on logout. Could be done with setToken too
    fun clearToken() {
        this.token = ""
    }

    // Headers for requests are added here
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(request)
    }
}
