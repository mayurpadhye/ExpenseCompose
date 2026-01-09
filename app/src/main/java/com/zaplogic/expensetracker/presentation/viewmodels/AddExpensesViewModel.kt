package com.zaplogic.expensetracker.presentation.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaplogic.expensetracker.core.enum_types.ExpenseCategoryType
import com.zaplogic.expensetracker.data.remote.dto.AddExpenseRequestDto
import com.zaplogic.expensetracker.data.network.Resource
import com.zaplogic.expensetracker.domain.shared_prefernce.PreferenceManager
import com.zaplogic.expensetracker.domain.usecases.AddExpenseUseCase
import com.zaplogic.expensetracker.presentation.ui.screens.expense.state.AddExpenseEvent
import com.zaplogic.expensetracker.presentation.ui.screens.expense.state.AddExpenseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddExpensesViewModel @Inject constructor(
    private val addExpenseUseCase: AddExpenseUseCase,
    private val sharedPreference: PreferenceManager
) : ViewModel() {


    @RequiresApi(Build.VERSION_CODES.O)
    private val dateFormatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")

    private val _addExpenseUiState = mutableStateOf(AddExpenseState())
    val addExpenseUIState: MutableState<AddExpenseState> = _addExpenseUiState

    private val _addExpenseEvent = Channel<AddExpenseEvent>()
    val addExpenseEvent = _addExpenseEvent.receiveAsFlow()

    private fun updateState(transform: (AddExpenseState) -> AddExpenseState) {
        _addExpenseUiState.value = transform(_addExpenseUiState.value)
    }

    fun onExpenseTitleChange(title: String) = updateState {
        it.copy(title = title, titleError = null)

    }

    fun onAmountChange(amount: String) = updateState {
        it.copy(amount = amount, amountError = null)
    }

    fun onDescriptionChange(description: String) = updateState {
        it.copy(description = description)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun onExpenseDateChange(timeStamp: Long) = updateState {
        val date = java.time.Instant.ofEpochMilli(timeStamp)
            .atZone(java.time.ZoneId.systemDefault())
            .toLocalDate()
        it.copy(expenseDate = date, dateError = null)
    }

    fun onCategoryExpandedChange(isCategoryExpanded: Boolean) = updateState {
        it.copy(isCategoryExpanded = !isCategoryExpanded)
    }

    fun onCategorySelection(selectedCategory: ExpenseCategoryType) = updateState {
        it.copy(selectedCategory = selectedCategory, categoryError = null)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getExpenseDate(expenseDate: LocalDate?): String {
        return expenseDate?.format(dateFormatter) ?: "Select Date"
    }

    fun showDatePickerDialog(isShow: Boolean) = updateState {
        it.copy(showDatePickerDialog = isShow)
    }

    fun validateAddExpenseForm() = updateState {
        val titleError: String? = if (checkTitleIsValid()) null else "Please Enter Title"
        val amountError: String? = if (checkAmountIsValid()) null else "Please Enter Amount"
        val categoryError: String? = if (checkCategoryIsValid()) null else "Please Select Category"
        val dateError: String? = if (checkIsDateValid()) null else "Please Select Date"
        if (titleError == null && amountError == null && categoryError == null && dateError == null) {
            // addExpense()
        }
        it.copy(
            titleError = titleError,
            amountError = amountError,
            categoryError = categoryError,
            dateError = dateError
        )


    }

    fun addExpense() {
        viewModelScope.launch {
            addExpenseUseCase(
                AddExpenseRequestDto(
                    title = _addExpenseUiState.value.title,
                    amount = _addExpenseUiState.value.amount,
                    expenseDescription = _addExpenseUiState.value.description,
                    expenseDate = _addExpenseUiState.value.expenseDate.toString(),
                    expenseType = _addExpenseUiState.value.selectedCategory,
                    userId = sharedPreference.getUserId()
                )
            ).onEach {  value ->
                when(value) {
                    is Resource.Error -> {
                        _addExpenseUiState.value = _addExpenseUiState.value.copy(
                            errorMsg = value.message
                        )
                        _addExpenseEvent.send(AddExpenseEvent.AddExpenseEventMsg(value.message?:"Unexpected Error!"))
                    }
                    is Resource.Loading -> {
                        updateState { _addExpenseUiState.value.copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        _addExpenseUiState.value.copy(
                            isLoading = false,
                            data = value.data
                        )
                        _addExpenseEvent.send(AddExpenseEvent.AddExpenseEventMsg("Expense Added Successfully"))
                    }
                }
            }.launchIn(viewModelScope)

        }
    }

    fun checkTitleIsValid(): Boolean {
        return _addExpenseUiState.value.title.isNotEmpty()

    }

    fun checkAmountIsValid(): Boolean {
        return _addExpenseUiState.value.amount.isNotEmpty()

    }

    fun checkCategoryIsValid(): Boolean {
        return _addExpenseUiState.value.selectedCategory != ExpenseCategoryType.SELECT_CATEGORY
    }

    fun checkIsDateValid(): Boolean {
        return _addExpenseUiState.value.expenseDate != null
    }


}