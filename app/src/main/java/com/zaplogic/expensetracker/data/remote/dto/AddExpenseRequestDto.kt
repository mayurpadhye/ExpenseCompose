package com.zaplogic.expensetracker.data.remote.dto

import com.zaplogic.expensetracker.core.enum_types.ExpenseCategoryType


data class AddExpenseRequestDto(
    val title : String? = null,
    val expenseDescription : String? = null,
    val amount : String? = null,
    val expenseType : ExpenseCategoryType? = null,
    val expenseDate : String? = null,
    val userId : Int?
)
