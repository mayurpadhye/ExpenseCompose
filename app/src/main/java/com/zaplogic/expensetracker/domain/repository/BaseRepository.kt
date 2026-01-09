package com.zaplogic.expensetracker.domain.repository

import com.zaplogic.expensetracker.data.network.Resource
import com.zaplogic.expensetracker.domain.model.ErrorParser
import retrofit2.Response

abstract class BaseRepository {
    suspend fun <T> safeAPICall(apiCall: suspend () -> Response<T>) : Resource<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful){
                response.body()?.let {
                    Resource.Success(it)
                }?: Resource.Error("Response Body is Empty")
            } else {
                val errorObj = ErrorParser.parseError(response.errorBody())
                Resource.Error(errorObj?.message ?: "Unknown Error")
            }


        } catch (e : Exception){
            Resource.Error(e.message ?: "Network Error")
        }

    }
}