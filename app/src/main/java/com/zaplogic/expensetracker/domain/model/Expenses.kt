package com.zaplogic.expensetracker.domain.model

import com.google.gson.annotations.SerializedName
import com.zaplogic.expensetracker.core.enum_types.ExpenseCategoryType

data class Expenses(
    @SerializedName("amount")
    val amount: Double = 0.0,
    @SerializedName("expenseDate")
    val expenseDate: String = "",
    @SerializedName("expenseDescription")
    val expenseDescription: String = "",
    @SerializedName("expenseType")
    val expenseType: ExpenseCategoryType = ExpenseCategoryType.BILLS,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("title")
    val title: String = ""
)
