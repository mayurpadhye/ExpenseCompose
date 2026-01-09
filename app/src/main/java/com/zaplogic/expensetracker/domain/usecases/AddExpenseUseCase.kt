package com.zaplogic.expensetracker.domain.usecases

import com.zaplogic.expensetracker.data.remote.dto.AddExpenseRequestDto
import com.zaplogic.expensetracker.data.remote.dto.AddExpenseResponse
import com.zaplogic.expensetracker.data.network.Resource
import com.zaplogic.expensetracker.domain.model.Expenses
import com.zaplogic.expensetracker.domain.repository.ExpensesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddExpenseUseCase @Inject constructor(
    private val expensesRepository: ExpensesRepository
) {
    operator fun invoke(addExpenseRequestDto: AddExpenseRequestDto) : Flow<Resource<Expenses?>>  =
        flow {
            emit(Resource.Loading())
          val response = expensesRepository.addExpense(addExpenseRequestDto)
            emit(response)

    }

}