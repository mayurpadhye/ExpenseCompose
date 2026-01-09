package com.zaplogic.expensetracker.presentation.viewmodels

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaplogic.expensetracker.core.enum_types.ExpenseCategoryType
import com.zaplogic.expensetracker.data.remote.dto.AddExpenseRequestDto
import com.zaplogic.expensetracker.data.network.Resource
import com.zaplogic.expensetracker.domain.shared_prefernce.PreferenceManager
import com.zaplogic.expensetracker.domain.usecases.GetExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val getExpenseUseCase: GetExpenseUseCase,
    private val sharedPreference: PreferenceManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExpensesUiState())
    val uiState: StateFlow<ExpensesUiState> = _uiState.asStateFlow()

    private var currentMonthlyIncome: Double = 0.0
    private var expensesTotalAmount: Double = 0.0
    private var currencySymbol: String = "$"

     fun getExpensesExpenses() {
        viewModelScope.launch {
            getExpenseUseCase.invoke(
                AddExpenseRequestDto(
                    userId = sharedPreference.getUserId()
                )
            ).onEach {
                value ->
                when(value){
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {

                       val expenses = value.data?.map { category ->
                            ExpenseCategoryUiModel(
                                id = category.id,
                                title = category.title,
                                amountLabel = formatCurrency(category.amount, ""),
                                iconEmoji = resolveEmoji(category.expenseType),
                                backgroundColor = resolveBackgroundColor(category.expenseType),
                                contentColor = Color.Black
                            )
                        }

                        _uiState.value = _uiState.value.copy(
                            expenses = expenses ?: emptyList()
                        )

                    }
                }
            }.launchIn(viewModelScope)

            /*
            observeExpensesUseCase()
                .catch { throwable ->
                    _uiState.value = ExpensesUiState(
                        isLoading = false,
                        errorMessage = throwable.message ?: "Unexpected error"
                    )
                }
                .collect { snapshot ->
                    currentMonthlyIncome = snapshot.monthlyIncome
                    expensesTotalAmount = snapshot.expenseCategories.sumOf { it.amount }
                    currencySymbol = snapshot.currencySymbol
                    _uiState.value = ExpensesUiState(
                        isLoading = false,
                        budgetLabel = formatCurrency(snapshot.totalBudget, snapshot.currencySymbol),
                        dateLabel = snapshot.referenceDateLabel,
                        totalLabel = formatCurrency(snapshot.dailyTotal, ""),
                        monthlyIncomeLabel = formatCurrency(currentMonthlyIncome, currencySymbol),
                        remainingAmountLabel = formatCurrency(
                            currentMonthlyIncome - expensesTotalAmount,
                            currencySymbol
                        ),
                        expenses = snapshot.expenseCategories.map { category ->
                                ExpenseCategoryUiModel(
                                id = category.id,
                                title = category.title,
                                amountLabel = formatCurrency(category.amount, ""),
                                iconEmoji = resolveEmoji(category.type),
                                backgroundColor = resolveBackgroundColor(category.type),
                                contentColor = Color.Black
                            )
                        }
                    )
                }*/
        }
    }

    fun onTabSelected(tab: ExpenseTab) {
        if (_uiState.value.selectedTab == tab) return
        _uiState.value = _uiState.value.copy(selectedTab = tab)
    }

    fun onIncomeAdded(amount: Double) {
        currentMonthlyIncome = amount
        _uiState.value = _uiState.value.copy(
            monthlyIncomeLabel = formatCurrency(currentMonthlyIncome, currencySymbol),
            remainingAmountLabel = formatCurrency(
                currentMonthlyIncome - expensesTotalAmount,
                currencySymbol
            )
        )
    }

    private fun formatCurrency(amount: Double, symbol: String): String {
        val formatter = DecimalFormat("#0.00")
        val formatted = formatter.format(amount)
        return buildString {
            append(symbol)
            append(formatted.replace('.', '.'))
        }
    }

    private fun resolveEmoji(type: ExpenseCategoryType): String {
        return when (type) {
            ExpenseCategoryType.ENTERTAINMENT -> "\uD83D\uDCA1" // light bulb
            ExpenseCategoryType.HOUSE_RENT -> "\uD83C\uDFE0" // house
            ExpenseCategoryType.FOOD -> "\uD83E\uDD51" // avocado
            ExpenseCategoryType.OTHER -> "\uD83D\uDC31" // cat face
            else -> "\uD83D\uDC31"
        }
    }

    private fun resolveBackgroundColor(type: ExpenseCategoryType): Color {
        return when (type) {
            ExpenseCategoryType.BILLS -> Color(0xFFFFF3CD)
            ExpenseCategoryType.HOUSE_RENT -> Color(0xFFE6F3FF)
            ExpenseCategoryType.FOOD -> Color(0xFFEFFFF1)
            ExpenseCategoryType.OTHER -> Color(0xFFFFF1F5)
            else -> Color(0xFFFFF1F5)
        }
    }
}

