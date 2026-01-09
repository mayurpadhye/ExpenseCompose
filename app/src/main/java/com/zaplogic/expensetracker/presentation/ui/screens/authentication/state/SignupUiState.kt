package com.zaplogic.expensetracker.presentation.ui.screens.authentication.state

import com.zaplogic.expensetracker.data.remote.dto.SignUpResponse
import com.zaplogic.expensetracker.core.enum_types.GenderTypes

data class SignupUiState(
    val name: String? = null,
    val email: String? = null,
    val mobileNumber: String? = null,
    val gender: GenderTypes? = null,
    val address: String? = null,
    val password: String? = null,
    val dob : String? = null,
    val isLoading: Boolean = false,
    val nameValidationError: String? = null,
    val emailValidationError: String? = null,
    val mobileNumberValidationError: String? = null,
    val genderValidationError: String? = null,
    val addressValidationError: String? = null,
    val passwordValidationError: String? = null,
    val dobValidationError : String? = null,
    val data: SignUpResponse? = null,
    val isSignUpSuccess: Boolean? = null
)
