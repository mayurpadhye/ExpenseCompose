package com.zaplogic.expensetracker.domain.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody

object ErrorParser {
    fun parseError(errorBody: ResponseBody?): ApiError? {
        return try {
            val gson = Gson()
            val type = object : TypeToken<ApiError>() {}.type
            gson.fromJson(errorBody?.charStream(), type)
        } catch (e: Exception) {
            ApiError(message = "An unknown error occurred")
        }
    }
}