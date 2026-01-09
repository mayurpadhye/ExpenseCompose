package com.zaplogic.expensetracker.data.remote.api

import com.zaplogic.expensetracker.data.remote.dto.AddExpenseRequestDto
import com.zaplogic.expensetracker.data.remote.dto.AddExpenseResponse
import com.zaplogic.expensetracker.data.remote.dto.LoginRequestDto
import com.zaplogic.expensetracker.data.remote.dto.LoginResponse
import com.zaplogic.expensetracker.data.remote.dto.SignUpRequestDto
import com.zaplogic.expensetracker.data.remote.dto.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST("auth/loginApp")
    suspend fun login(@Body loginRequestDto: LoginRequestDto) : Response<LoginResponse>
    @POST("auth/signUp")
    suspend fun signUp(@Body signUpRequestDto: SignUpRequestDto) : Response<SignUpResponse?>

    @POST("expenses/addExpense")
    suspend fun addExpense(@Body addExpenseRequestDto: AddExpenseRequestDto) : Response<AddExpenseResponse?>

    @POST("expenses/getAllExpenseByUserId")
    suspend fun getExpenseListById(@Body addExpenseRequestDto: AddExpenseRequestDto) : Response<List<AddExpenseResponse>?>

}