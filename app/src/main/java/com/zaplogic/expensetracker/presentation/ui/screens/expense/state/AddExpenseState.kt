package com.zaplogic.expensetracker.presentation.ui.screens.expense.state

import com.zaplogic.expensetracker.core.common.Utils.getAllExpenseCategories
import com.zaplogic.expensetracker.core.enum_types.ExpenseCategoryType
import com.zaplogic.expensetracker.domain.model.Expenses
import java.time.LocalDate

data class AddExpenseState(
    val title: String = "",
    val amount: String = "",
    val category: String = "",
    val description : String = "",
    val date : String = "",
    val isCategoryExpanded : Boolean = false,
    val selectedCategory : ExpenseCategoryType = ExpenseCategoryType.SELECT_CATEGORY,
    val expenseDate: LocalDate? = null,
    val categories: List<ExpenseCategoryType> = getAllExpenseCategories(),
    val showDatePickerDialog : Boolean = false,
    val titleError : String? = null,
    val dateError : String? = null,
    val amountError : String? = null,
    val categoryError : String? = null,
    val isLoading : Boolean = false,
    val data : Expenses? = null,
    val errorMsg : String? = null
)

sealed class AddExpenseEvent {
    object NavigateToDashboard : AddExpenseEvent()
    data class AddExpenseEventMsg(val message : String) : AddExpenseEvent()
}
