package com.zaplogic.expensetracker.data.remote.dto

import com.zaplogic.expensetracker.core.enum_types.GenderTypes

data class SignUpResponse(
    val name : String,
    val email : String,
    val mobileNumber : Long,
    val gender : GenderTypes,
    val address : String,
    val id : Int,
    val createdAt : String
)
