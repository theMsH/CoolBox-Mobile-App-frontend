package com.example.coolbox_mobiiliprojekti_app.model

data class LoginState(
    val username: String = "",
    val password: String = "",
    val loading: Boolean = false)

data class LoginRes(val id: Int = 1, val role: String = "a")