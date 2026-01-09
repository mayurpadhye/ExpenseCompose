package com.zaplogic.expensetracker.domain.usecases

import com.zaplogic.expensetracker.data.remote.dto.LoginResponse
import com.zaplogic.expensetracker.data.network.Resource
import com.zaplogic.expensetracker.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: AuthRepository

){
    operator fun invoke(username : String, password : String) : Flow<Resource<LoginResponse?>> =
        flow{
           try {
               emit(Resource.Loading())
               val loginResponse =  loginRepository.login(username = username, password = password)
               emit(Resource.Success(loginResponse))
           } catch (e : HttpException){
               emit(Resource.Error(e.localizedMessage ?: "An unexpected HTTP error occurred"))
           }
           catch (e: IOException) {
               emit(Resource.Error("Couldn't reach server. Check your internet connection."))
           } catch (e: Exception) {
               emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
           }
        }
}