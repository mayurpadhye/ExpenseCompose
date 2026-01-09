package com.zaplogic.expensetracker.domain.repository

import com.zaplogic.expensetracker.data.remote.dto.AddExpenseRequestDto
import com.zaplogic.expensetracker.data.remote.dto.AddExpenseResponse
import com.zaplogic.expensetracker.data.network.Resource
import com.zaplogic.expensetracker.domain.model.Expenses

/**
 * Abstraction layer for retrieving expense/budget related data.
 */
interface ExpensesRepository {
   suspend fun getExpenseListById(
        addExpenseRequestDto: AddExpenseRequestDto
    ) : Resource<List<AddExpenseResponse>?>
   suspend fun addExpense(addExpenseRequestDto: AddExpenseRequestDto) : Resource<Expenses?>
}

