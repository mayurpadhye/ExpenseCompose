package com.zaplogic.expensetracker.data.repositoryImpl

import com.zaplogic.expensetracker.data.remote.dto.LoginRequestDto
import com.zaplogic.expensetracker.data.remote.dto.LoginResponse
import com.zaplogic.expensetracker.data.remote.dto.SignUpRequestDto
import com.zaplogic.expensetracker.data.remote.dto.SignUpResponse
import com.zaplogic.expensetracker.data.remote.api.ApiInterface
import com.zaplogic.expensetracker.data.network.Resource
import com.zaplogic.expensetracker.domain.repository.AuthRepository
import com.zaplogic.expensetracker.domain.repository.BaseRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface
): AuthRepository, BaseRepository() {
    override suspend fun login(username: String, password: String) : LoginResponse? {
        val res = apiInterface.login(LoginRequestDto(email = username, password = password))

       if (res.isSuccessful && res.body()!=null){
           return res.body()
       } else {
           throw Exception("Login Failed")
       }
    }

    override suspend fun registerUser(
        signUpRequestDto: SignUpRequestDto
    ) : Resource<SignUpResponse?>{

       return safeAPICall { apiInterface.signUp(signUpRequestDto) }
       /* val res = apiInterface.signUp(signUpRequestDto)
        if (res.isSuccessful && res.body()!=null){
            return res.body()
        }else{
            val errorObj = ErrorParser.parseError(res.errorBody())
            val displayMessage = errorObj?.message ?: "Unknown Error"
            throw Exception(displayMessage)

        }*/
    }
}