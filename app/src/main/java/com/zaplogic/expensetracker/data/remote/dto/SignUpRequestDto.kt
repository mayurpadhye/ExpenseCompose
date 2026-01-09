package com.zaplogic.expensetracker.data.remote.dto

data class SignUpRequestDto(
    val name: String?,
    val email: String?,
    val password: String?,
    val mobileNumber: Long?,
    val address: String?,
    val dob: String?,
    val gender: String?

)
