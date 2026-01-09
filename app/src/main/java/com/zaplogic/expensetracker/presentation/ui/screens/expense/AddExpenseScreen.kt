package com.zaplogic.expensetracker.presentation.ui.screens.expense


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.zaplogic.expensetracker.presentation.common.SnackbarManager
import com.zaplogic.expensetracker.presentation.ui.components.CustomDatePicker
import com.zaplogic.expensetracker.presentation.ui.screens.expense.state.AddExpenseEvent
import com.zaplogic.expensetracker.presentation.viewmodels.AddExpensesViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    addExpensesViewModel: AddExpensesViewModel = hiltViewModel()
) {


    val addExpenseState by addExpensesViewModel.addExpenseUIState
    val addExpenseEvent = addExpensesViewModel.addExpenseEvent

    LaunchedEffect(true) {
        addExpenseEvent.collect {
            when (it) {
                is AddExpenseEvent.AddExpenseEventMsg -> {
                    SnackbarManager.showMessage(it.message)

                }

                else -> {

                }
                }
            }
        }

        if (addExpenseState.showDatePickerDialog) {
            CustomDatePicker(
                onDateSelected = {
                    it?.let {
                        addExpensesViewModel.onExpenseDateChange(it)
                    }
                },
                onDismiss = {
                    addExpensesViewModel.showDatePickerDialog(false)
                }
            )

        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OutlinedTextField(
                value = addExpenseState.title,
                isError = !addExpenseState.titleError.isNullOrEmpty(),
                supportingText = {
                    if (!addExpenseState.titleError.isNullOrEmpty()) {
                        Text(
                            text = addExpenseState.titleError ?: "",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                },
                onValueChange = {
                    addExpensesViewModel.onExpenseTitleChange(it.trim())
                },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = addExpenseState.amount,
                isError = !addExpenseState.amountError.isNullOrEmpty(),
                supportingText = {
                    if (!addExpenseState.amountError.isNullOrEmpty()) {
                        Text(
                            text = addExpenseState.amountError ?: "",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                },
                onValueChange = {
                    addExpensesViewModel.onAmountChange(it.trim())
                },
                label = { Text("Amount") },
                modifier = Modifier.fillMaxWidth(),
                prefix = { Text("₹") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            // --- Category Dropdown ---
            ExposedDropdownMenuBox(
                expanded = addExpenseState.isCategoryExpanded,
                onExpandedChange = {
                    addExpensesViewModel.onCategoryExpandedChange(addExpenseState.isCategoryExpanded)
                }
            ) {
                OutlinedTextField(
                    value = addExpenseState.selectedCategory.name,
                    isError = !addExpenseState.categoryError.isNullOrEmpty(),
                    supportingText = {
                        if (!addExpenseState.categoryError.isNullOrEmpty()) {
                            Text(
                                text = addExpenseState.categoryError ?: "",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    },
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = addExpenseState.isCategoryExpanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = addExpenseState.isCategoryExpanded,
                    onDismissRequest = {
                        addExpensesViewModel.onCategoryExpandedChange(
                            addExpenseState.isCategoryExpanded
                        )
                    }
                ) {
                    addExpenseState.categories.forEach { it ->
                        DropdownMenuItem(
                            text = { Text(it.name) },
                            onClick = {
                                addExpensesViewModel.onCategorySelection(it)
                                addExpensesViewModel.onCategoryExpandedChange(addExpenseState.isCategoryExpanded)
                            }
                        )
                    }
                }
            }

            // --- Date Picker ---
            OutlinedTextField(
                value = addExpensesViewModel.getExpenseDate(addExpenseState.expenseDate),
                onValueChange = {},
                isError = !addExpenseState.dateError.isNullOrEmpty(),
                supportingText = {
                    if (!addExpenseState.dateError.isNullOrEmpty()) {
                        Text(
                            text = addExpenseState.dateError ?: "",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                },
                readOnly = true,
                label = { Text("Date") },
                trailingIcon = {
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = "Select Date",
                        modifier = Modifier.clickable {
                            addExpensesViewModel.showDatePickerDialog(true)
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        addExpensesViewModel.showDatePickerDialog(true)
                    }
            )


            OutlinedTextField(
                value = addExpenseState.description,

                onValueChange = {
                    addExpensesViewModel.onDescriptionChange(it)
                },
                label = { Text("Description (Optional)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                maxLines = 4
            )

            Spacer(modifier = Modifier.weight(1f))

            // --- Save Button ---
            Button(
                onClick = {
                    addExpensesViewModel.validateAddExpenseForm()

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("SAVE EXPENSE")
            }
        }
    }