package com.zaplogic.expensetracker.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("id")
    val id: Int,

    @SerializedName("email")
    val email: String,

    @SerializedName("token")
    val token: String,

    @SerializedName("refreshedToken")
    val refreshedToken: String
)