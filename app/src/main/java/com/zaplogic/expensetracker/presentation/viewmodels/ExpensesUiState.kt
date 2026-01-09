package com.zaplogic.expensetracker.presentation.viewmodels

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class ExpensesUiState(
    val isLoading: Boolean = true,
    val budgetLabel: String = "",
    val dateLabel: String = "",
    val totalLabel: String = "",
    val monthlyIncomeLabel: String = "",
    val remainingAmountLabel: String = "",
    val selectedTab: ExpenseTab = ExpenseTab.EXPENSE,
    val expenses: List<ExpenseCategoryUiModel> = emptyList(),
    val errorMessage: String? = null
)

@Immutable
data class ExpenseCategoryUiModel(
    val id: Int,
    val title: String,
    val amountLabel: String,
    val iconEmoji: String,
    val backgroundColor: Color,
    val contentColor: Color
)

enum class ExpenseTab {
    EXPENSE, INCOME
}

