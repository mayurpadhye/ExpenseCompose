@file:OptIn(ExperimentalMaterial3Api::class)

package com.zaplogic.expensetracker.presentation.ui.components

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import com.zaplogic.expensetracker.presentation.ui.theme.Dimens
import com.zaplogic.expensetracker.core.enum_types.GenderTypes

@Composable
fun RoundedCornerEditText(
    placeHolder: String,
    isError: Boolean,
    errorMsg: String?,
    keyboardType: KeyboardType = KeyboardType.Text,
    isMobileNumber: Boolean = false,
    onValueChanged: (String) -> Unit
) {

    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = {
            if (isMobileNumber) {
                if (it.length <= 10) {
                    text = it
                    onValueChanged.invoke(it)
                }
            } else {
                text = it
                onValueChanged.invoke(it)
            }
        },
        label = { Text(placeHolder) },
        placeholder = { Text(placeHolder) },
       keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier
            .fillMaxWidth()
            ,
        shape = RoundedCornerShape(Dimens.dp_15),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedContainerColor = MaterialTheme.colorScheme.tertiary,
            unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,

            // Use 'BorderColor' instead of 'IndicatorColor'
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            errorBorderColor = Color.Transparent,

            // Optional: Label colors
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = Color.Gray
        ),
        isError = isError,
        // Show error text below field
        supportingText = {
            if (isError) {
                Text(
                    text = errorMsg ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    )
}

@Composable
fun ReadOnlyTextFieldWithCalendar(
    dob: String,
    isError: Boolean,
    errorMsg: String?,
    onValueChanged: (String) -> Unit
) {
    var text by remember { mutableStateOf(dob) }
    val context = LocalContext.current

    val datePickerDialog = remember {
        val dialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                text = "$dayOfMonth/${month + 1}/$year"
                onValueChanged(text)
            },
            2000, 0, 1 // Default date
        )
        dialog.datePicker.maxDate = System.currentTimeMillis()
        dialog
    }



    Box {
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                onValueChanged(it)
            },
            label = { Text("Date of Birth") },
            placeholder = { Text("Date of Birth") },
            modifier = Modifier
                .fillMaxWidth(),
            readOnly = true,
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(
                        text = errorMsg ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            },
            shape = RoundedCornerShape(Dimens.dp_10),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedContainerColor = MaterialTheme.colorScheme.tertiary,
                unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,

                // Use 'BorderColor' instead of 'IndicatorColor'
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent,

                // Optional: Label colors
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = Color.Gray
            ),
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    datePickerDialog.show()
                },
        )
    }


}

@Composable
fun CustomDropDown(
    value: GenderTypes?,
    isError: Boolean,
    errorMsg: String?,
    onValueSelected: (GenderTypes?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var dataText by remember { mutableStateOf(value) }
    val genders = listOf(GenderTypes.MALE, GenderTypes.FEMALE)
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent)
        ) {
            OutlinedTextField(
                value = dataText?.name ?: "",
                onValueChange = {},
                readOnly = true,
                isError = isError,
                supportingText = {
                    if (isError) {
                        Text(
                            text = errorMsg ?: "",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                },
                label = {Text("Gender")},
                placeholder = { Text("Gender") },
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(Dimens.dp_10),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = MaterialTheme.colorScheme.tertiary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,

                    // Use 'BorderColor' instead of 'IndicatorColor'
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent,

                    // Optional: Label colors
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    unfocusedLabelColor = Color.Gray
                ),
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.dp_10)
                    .background(
                        MaterialTheme.colorScheme.surface,
                        RoundedCornerShape(Dimens.dp_15)
                    )
            ) {
                genders.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option.name) },
                        onClick = {
                            dataText = option
                            onValueSelected.invoke(dataText)
                            expanded = false
                        }
                    )

                }
            }

        }
    }

}