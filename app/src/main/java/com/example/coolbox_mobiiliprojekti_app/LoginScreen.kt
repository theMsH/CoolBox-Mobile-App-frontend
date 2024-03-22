package com.example.coolbox_mobiiliprojekti_app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coolbox_mobiiliprojekti_app.viewmodel.LoginViewModel

@Composable
fun LoginScreen(onLoginClick: () -> Unit) {
    val loginVm: LoginViewModel = viewModel()
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            loginVm.loginState.value.loading -> CircularProgressIndicator(
                modifier = Modifier.align(
                    Alignment.Center)
            )
            else -> Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                OutlinedTextField(
                    value = loginVm.loginState.value.username,
                    onValueChange = { newUsername ->
                        loginVm.setUsername(newUsername)},
                    placeholder = { Text("Username") })

                Spacer(modifier = Modifier.height(5.dp))

                OutlinedTextField(
                    visualTransformation = PasswordVisualTransformation(),
                    value = loginVm.loginState.value.password,
                    onValueChange = { newPassword ->
                        loginVm.setPassword(newPassword)},
                    placeholder = { Text("Password") })
                Spacer(modifier = Modifier.height((5.dp)))
                Button(
                    enabled = loginVm.loginState.value.username != "" &&
                            loginVm.loginState.value.password != "",
                    onClick = {
                        loginVm.login()
                        onLoginClick()
                    })
                {
                    Text("Login")
                }
            }
        }
    }
}