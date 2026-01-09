package com.zaplogic.expensetracker.domain.usecases

import com.zaplogic.expensetracker.data.remote.dto.SignUpRequestDto
import com.zaplogic.expensetracker.data.remote.dto.SignUpResponse
import com.zaplogic.expensetracker.data.network.Resource
import com.zaplogic.expensetracker.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
     operator fun invoke(signUpRequestDto: SignUpRequestDto): Flow<Resource<SignUpResponse?>> =
        flow {
            emit(Resource.Loading())
            val signUpResponse = authRepository.registerUser(signUpRequestDto)
            emit(signUpResponse)
        }


}