package com.zaplogic.expensetracker.data.repositoryImpl

import com.zaplogic.expensetracker.data.mapper.toDomain
import com.zaplogic.expensetracker.data.remote.dto.AddExpenseRequestDto
import com.zaplogic.expensetracker.data.remote.dto.AddExpenseResponse
import com.zaplogic.expensetracker.data.remote.api.ApiInterface
import com.zaplogic.expensetracker.data.network.Resource
import com.zaplogic.expensetracker.domain.model.Expenses
import com.zaplogic.expensetracker.domain.repository.ExpensesRepository
import com.zaplogic.expensetracker.domain.repository.BaseRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Simple in-memory repository that mimics a persistent source while the
 * networking contract is being built. Keeps the data layer adhering to the
 * repository abstraction which can later be replaced without touching the UI.
 */
@Singleton
class ExpensesRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface
) : ExpensesRepository, BaseRepository() {
    override suspend fun getExpenseListById(addExpenseRequestDto: AddExpenseRequestDto): Resource<List<AddExpenseResponse>?> {
        return safeAPICall {
            apiInterface.getExpenseListById(addExpenseRequestDto)
        }
    }

    /*override fun observeBudgetSnapshot(): Flow<BudgetSnapshot> {
        val snapshot = BudgetSnapshot(
            currencySymbol = "\u20B9",
            totalBudget = 5430.60,
            referenceDateLabel = "18 May 2020",
            dailyTotal = 270.0,
            monthlyIncome = 6200.00,
            expenseCategories = listOf(
                ExpenseCategory(
                    id = 1,
                    title = "Electricity",
                    amount = 60.0,
                    type = ExpenseCategoryType.BILLS
                ),
                ExpenseCategory(
                    id = 2,
                    title = "Home",
                    amount = 23.50,
                    type = ExpenseCategoryType.HOUSE_RENT
                ),
                ExpenseCategory(
                    id = 3,
                    title = "Food & Drink",
                    amount = 150.0,
                    type = ExpenseCategoryType.FOOD
                ),
                ExpenseCategory(
                    id = 4,
                    title = "Pet Food",
                    amount = 36.50,
                    type = ExpenseCategoryType.OTHER
                )
            )
        )
        return flowOf(snapshot)
    }*/

    override suspend fun addExpense(addExpenseRequestDto: AddExpenseRequestDto): Resource<Expenses?> {
        val dtoResource = safeAPICall {
            apiInterface.addExpense(addExpenseRequestDto)
        }

        // 2. Map the result to Resource<Expenses> manually
        return if (dtoResource is Resource.Success) {
            Resource.Success(dtoResource.data?.toDomain())
        } else {
            // Pass the error message along
            Resource.Error(dtoResource.message ?: "An error occurred")
        }
    }
}

