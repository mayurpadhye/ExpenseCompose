package com.zaplogic.expensetracker.domain.model

import com.zaplogic.expensetracker.core.enum_types.ExpenseCategoryType

data class ExpenseCategory(
    val id: Int,
    val title: String,
    val amount: Double,
    val type: ExpenseCategoryType
)
