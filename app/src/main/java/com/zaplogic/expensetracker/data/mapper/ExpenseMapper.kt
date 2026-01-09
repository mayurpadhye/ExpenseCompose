package com.zaplogic.expensetracker.data.mapper

import com.zaplogic.expensetracker.data.remote.dto.AddExpenseResponse
import com.zaplogic.expensetracker.domain.model.Expenses

fun AddExpenseResponse.toDomain() : Expenses{

return Expenses(
    amount = this.amount,
    expenseDate = this.expenseDate,
    expenseDescription = this.expenseDescription,
    expenseType = this.expenseType,
    title = this.title,
    id = this.id
)
}