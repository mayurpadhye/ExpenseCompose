package com.zaplogic.expensetracker.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiError(
    val message: String? = null,
    val statusCode: Int? = null,
    val timestamp: String? = null
)