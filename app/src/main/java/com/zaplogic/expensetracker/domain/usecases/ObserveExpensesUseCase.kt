package com.zaplogic.expensetracker.domain.usecases

import com.zaplogic.expensetracker.domain.repository.ExpensesRepository
import javax.inject.Inject

/**
 * Use case responsible for exposing the budget snapshot stream to the
 * presentation layer. Keeps the ViewModel agnostic of the data source.
 */
class ObserveExpensesUseCase @Inject constructor(
    private val expensesRepository: ExpensesRepository
) {

}

