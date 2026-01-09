package com.zaplogic.expensetracker.domain.model

data class RegisterUser(
val name : String,
    val email : String,
    val mobileNumber : Long,
    val address : String,
    val password : String,
    val dob : String,
    val gender : String
)
