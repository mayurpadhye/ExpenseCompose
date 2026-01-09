package com.zaplogic.expensetracker.presentation.ui.screens.authentication.state

import com.zaplogic.expensetracker.data.remote.dto.LoginResponse

data class LoginUiState(
    val isLoading: Boolean = false,
    val data: LoginResponse? = null,
    val error: String = "",
    val usernameError: String? = null,
    val passwordError: String? = null,
    val emailInput: String = "",        // Add this
    val passwordInput: String = "",     // Add this
    val isLoginSuccessful: Boolean = false,
    val isShowPassword : Boolean = false,
    val rememberMe : Boolean = false
)