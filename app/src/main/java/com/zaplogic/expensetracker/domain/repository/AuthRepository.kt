package com.zaplogic.expensetracker.domain.repository

import com.zaplogic.expensetracker.data.remote.dto.LoginResponse
import com.zaplogic.expensetracker.data.remote.dto.SignUpRequestDto
import com.zaplogic.expensetracker.data.remote.dto.SignUpResponse
import com.zaplogic.expensetracker.data.network.Resource

interface AuthRepository {
    suspend fun login(
        username : String,
        password : String
    ) : LoginResponse?

    suspend fun registerUser(
        signUpRequestDto: SignUpRequestDto
    ) : Resource<SignUpResponse?>
}