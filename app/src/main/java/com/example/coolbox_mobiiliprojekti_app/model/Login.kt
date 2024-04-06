package com.example.coolbox_mobiiliprojekti_app.model

import com.google.gson.annotations.SerializedName

data class LoginState(
    val username: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val error: String? = null,
    val showPassword: Boolean = false,
    val registerDone: Boolean = false,
    val loggedIn: Boolean = false,
    val initialized: Boolean = false,
    val loginDone: Boolean = false,
    val user: User = User()
)

data class User(
    @SerializedName("user_id")
    val userId: Int = 0,
    val username: String = "",
    @SerializedName("role_id")
    val roleId: Int = 0
)

data class LoginDetails(
    val username: String = "",
    val password: String = ""
)

data class LoginRes(
    @SerializedName("access_token")
    val accessToken: String = "",
    val user: User = User()
)
