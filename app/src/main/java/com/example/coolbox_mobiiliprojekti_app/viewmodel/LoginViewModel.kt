package com.example.coolbox_mobiiliprojekti_app.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coolbox_mobiiliprojekti_app.model.LoginRes
import com.example.coolbox_mobiiliprojekti_app.model.LoginState
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    fun setUsername(newUsername: String) {
        _loginState.value = _loginState.value.copy(username = newUsername)
    }

    fun setPassword(newPassword: String) {
        _loginState.value = _loginState.value.copy(password = newPassword)
    }

    fun login() {
        viewModelScope.launch {
            _loginState.value = _loginState.value.copy(loading = true)
            val res = LoginRes()
            _loginState.value = _loginState.value.copy(loading = false)
        }
    }
}