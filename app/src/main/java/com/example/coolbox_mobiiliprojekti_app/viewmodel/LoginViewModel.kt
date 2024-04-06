package com.example.coolbox_mobiiliprojekti_app.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coolbox_mobiiliprojekti_app.api.authInterceptor
import com.example.coolbox_mobiiliprojekti_app.api.authService
import com.example.coolbox_mobiiliprojekti_app.database.AccessToken
import com.example.coolbox_mobiiliprojekti_app.database.AccountDatabase
import com.example.coolbox_mobiiliprojekti_app.database.DbProvider
import com.example.coolbox_mobiiliprojekti_app.model.LoginDetails
import com.example.coolbox_mobiiliprojekti_app.model.LoginRes
import com.example.coolbox_mobiiliprojekti_app.model.LoginState
import kotlinx.coroutines.launch
import retrofit2.HttpException


class LoginViewModel(private val db: AccountDatabase = DbProvider.db) : ViewModel() {
    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    private val _user = mutableStateOf(LoginRes())
    val user: State<LoginRes> = _user

    fun setUsername(newUsername: String) {
        _loginState.value = _loginState.value.copy(username = newUsername)
    }

    fun setPassword(newPassword: String) {
        _loginState.value = _loginState.value.copy(password = newPassword)
    }

    fun setRegisterDone(done: Boolean) {
        _loginState.value = _loginState.value.copy(registerDone = done)
    }

    fun setLoginDone(done: Boolean) {
        _loginState.value = _loginState.value.copy(loginDone = done)
    }

    fun setInitialized() {
        _loginState.value = _loginState.value.copy(initialized = true)
    }

    fun clearError() {
        _loginState.value = _loginState.value.copy(error = null)
    }

    fun toggleShowPassword() {
        if (_loginState.value.showPassword) {
            _loginState.value = _loginState.value.copy(showPassword = false)
        }
        else _loginState.value = _loginState.value.copy(showPassword = true)
    }

    // Checks for existing token from RoomDB.
    // If found token is not valid for some reason it clears it.
    fun tryAutoLogin() {
        viewModelScope.launch {
            val query = db.accessTokenDao().getAccessToken()

            if (query != null) {
                try {
                    authInterceptor.setToken(query.accessToken)

                    // Check if token is valid
                    val res = authService.getUser()

                    Log.d("CheckLoginState()", "Token found and valid, skip login")

                    _user.value = _user.value.copy(accessToken = query.accessToken, user = res)
                    _loginState.value = _loginState.value.copy(loggedIn = true)
                }
                catch (e: Exception) {
                    if (e is HttpException) {
                        if (e.code() == 401) {
                            _loginState.value = _loginState.value.copy(error = "${e.code()}")
                        }
                        else _loginState.value = _loginState.value.copy(error = "$e")
                    } else _loginState.value = _loginState.value.copy(error = "$e")

                    // Autologin fail, remove token from RoomDB
                    authInterceptor.clearToken()
                    db.accessTokenDao().clearAccessToken()
                    _loginState.value = _loginState.value.copy(loggedIn = false)
                }
            }
            else Log.d("CheckLoginState()", "Login required")

            _loginState.value = _loginState.value.copy(loginDone = true)
        }
    }

    fun login() {
        viewModelScope.launch {
            try {
                _loginState.value = _loginState.value.copy(loading = true)

                _user.value = authService.login(
                    LoginDetails(
                        username = _loginState.value.username,
                        password = _loginState.value.password
                    )
                )

                // This is used to deliver token with headers
                authInterceptor.setToken(_user.value.accessToken)

                // This is used for auto login feature
                db.accessTokenDao().insertToken(
                    AccessToken(accessToken = _user.value.accessToken)
                )

                _loginState.value = _loginState.value.copy(loggedIn = true, username = "", password = "")
            }
            catch (e: Exception) {
                Log.d("error login()", "$e")
                if (e is HttpException) {
                    _loginState.value = _loginState.value.copy(error = e.code().toString())
                }
                else _loginState.value = _loginState.value.copy(error = e.toString())

            }
            finally {
                _loginState.value = _loginState.value.copy(loading = false, loginDone = true)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                _loginState.value = _loginState.value.copy(loading = true)

                authService.logout()

                // Clear tokens from authInterceptor and from RoomDB
                db.accessTokenDao().clearAccessToken()
                authInterceptor.clearToken()

                _loginState.value = _loginState.value.copy(loggedIn = false)
                Log.d("Logout()", "Successfully logout")
            } catch (e: Exception) {
                _loginState.value = _loginState.value.copy(error = e.toString())
                Log.d("Logout()", "$e")
            } finally {
                _loginState.value = _loginState.value.copy(loading = false, loginDone = true)
            }
        }
    }

    fun createNewUser() {
        viewModelScope.launch {
            try {
                _loginState.value = _loginState.value.copy(loading = true)

                authService.register(
                    LoginDetails(
                        _loginState.value.username,
                        _loginState.value.password
                    )
                )
                _loginState.value = _loginState.value.copy(registerDone = true)
            }
            catch (e: Exception) {
                Log.d("error createNewUser()", "$e")
                if (e is HttpException) {
                    _loginState.value = _loginState.value.copy(error = e.code().toString())
                }
                else _loginState.value = _loginState.value.copy(error = e.toString())
            }
            finally {
                _loginState.value = _loginState.value.copy(loading = false)
            }
        }
    }

}