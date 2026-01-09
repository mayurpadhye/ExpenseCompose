package com.zaplogic.expensetracker.presentation.ui.screens.expense

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zaplogic.expensetracker.R
import com.zaplogic.expensetracker.presentation.ui.theme.BackgroundColor
import com.zaplogic.expensetracker.presentation.ui.theme.ExpenseTrackerTheme
import com.zaplogic.expensetracker.presentation.ui.theme.Dimens
import com.zaplogic.expensetracker.presentation.viewmodels.ExpenseCategoryUiModel
import com.zaplogic.expensetracker.presentation.viewmodels.ExpenseTab
import com.zaplogic.expensetracker.presentation.viewmodels.ExpensesUiState
import com.zaplogic.expensetracker.presentation.viewmodels.ExpensesViewModel
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ExpensesScreen(
    onAddExpenseClick :() -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ExpensesViewModel = hiltViewModel()
) {
    val activity = (LocalContext.current as? Activity)
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    // BackHandler will be active only when this composable is on screen
    BackHandler(enabled = true) {
        // When back is pressed, finish the activity
        activity?.finish()
    }



    LaunchedEffect(Unit) {
        viewModel.getExpensesExpenses()
    }

    ExpensesContent(
        modifier = modifier,
        state = state,
        onTabSelected = viewModel::onTabSelected,
        onIncomeAdded = viewModel::onIncomeAdded,
        onAddExpenseClick = onAddExpenseClick
    )
}

@Composable
private fun ExpensesContent(
    state: ExpensesUiState,
    modifier: Modifier = Modifier,
    onTabSelected: (ExpenseTab) -> Unit = {},
    onIncomeAdded: (Double) -> Unit = {},
    onAddExpenseClick: () -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = BackgroundColor
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            BudgetHeader(
                state = state,
                onTabSelected = onTabSelected
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                if (state.selectedTab == ExpenseTab.EXPENSE) {
                    ExpenseListSection(
                        state = state,
                        modifier = Modifier.fillMaxSize(),
                    )
                } else {
                    TotalIncome(
                        modifier = Modifier.fillMaxSize(),
                        monthlyIncomeLabel = state.monthlyIncomeLabel,
                        remainingAmountLabel = state.remainingAmountLabel,
                        onIncomeAdded = onIncomeAdded
                    )
                }
            }
            BottomNavigationBar(
                modifier = Modifier
                    .fillMaxWidth(),
                onAddExpenseClick = onAddExpenseClick
            )
        }
    }
}

@Composable
private fun BudgetHeader(
    state: ExpensesUiState,
    onTabSelected: (ExpenseTab) -> Unit
) {
    val gradientColors = listOf(Color(0xFF1DD3B0), Color(0xFF0F8B8D))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .clip(
                RoundedCornerShape(
                    bottomStart = 32.dp,
                    bottomEnd = 32.dp
                )
            )
            .background(brush = Brush.verticalGradient(gradientColors))
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = "Menu",
                    tint = Color.White
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "My budget",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
            Text(
                text = state.budgetLabel.ifBlank { "₹0,00" },
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
            Spacer(modifier = Modifier.height(24.dp))
            SegmentedTabs(
                selectedTab = state.selectedTab,
                onTabSelected = onTabSelected
            )
        }
    }
}

@Composable
private fun SegmentedTabs(
    selectedTab: ExpenseTab,
    onTabSelected: (ExpenseTab) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.2f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ExpenseTab.entries.forEach { tab ->
                val isSelected = tab == selectedTab
                val background by animateColorAsState(
                    targetValue = if (isSelected) Color.White else Color.Transparent,
                    label = "tab_background"
                )
                val contentColor = if (isSelected) Color.Black else Color.White
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight()
                        .clip(RoundedCornerShape(20.dp))
                        .background(background)
                        .clickable { onTabSelected(tab) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tab.name.lowercase().replaceFirstChar { it.titlecase() },
                        color = contentColor,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                        modifier = Modifier.padding(Dimens.dp_10)
                    )
                }
            }
        }
    }
}

@Composable
private fun ExpenseListSection(
    state: ExpensesUiState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = state.dateLabel,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = if (state.selectedTab == ExpenseTab.EXPENSE) "Expense" else "Income",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
                Text(
                    text = state.totalLabel,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color(0xFFFF6F3C),
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
        items(state.expenses, key = { it.id }) { expense ->
            ExpenseRow(expense)
        }
    }
}

@Composable
private fun TotalIncome(
    modifier: Modifier = Modifier,
    monthlyIncomeLabel: String,
    remainingAmountLabel: String,
    onIncomeAdded: (Double) -> Unit
) {
    var incomeInput by rememberSaveable { mutableStateOf("") }
    val incomeValue = incomeInput.toDoubleOrNull()
    val isAddEnabled = incomeValue != null && incomeValue > 0.0

    Column(
        modifier = modifier
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Current Month Income",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    text = monthlyIncomeLabel.ifBlank { "₹0.00" },
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Remaining after expenses",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    text = remainingAmountLabel.ifBlank { "₹0.00" },
                    style = MaterialTheme.typography.titleLarge.copy(color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
                )
            }
        }

        OutlinedTextField(
            value = incomeInput,
            onValueChange = { incomeInput = it },
            label = { Text("Add income (one time)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                incomeValue?.let {
                    onIncomeAdded(it)
                    incomeInput = ""
                }
            },
            enabled = isAddEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = "Update Income")
        }
    }
}

@Composable
private fun ExpenseRow(expense: ExpenseCategoryUiModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(expense.backgroundColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = expense.iconEmoji,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                        color = expense.contentColor
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = expense.title,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
                    )
                    Text(
                        text = "expense",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
            Text(
                text = "₹${expense.amountLabel}",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFFEE7A35)
            )
        }
    }
}

@Composable
private fun BottomNavigationBar(modifier: Modifier = Modifier,onAddExpenseClick: () -> Unit) {
    Card(
        modifier = modifier
            .padding(horizontal = 24.dp, vertical = 16.dp),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0B0D27)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Menu,
                contentDescription = "Camera",
                tint = Color.White
            )
            Icon(
                imageVector = Icons.Outlined.Call,
                contentDescription = "Schedule",
                tint = Color.White
            )
            Icon(
                imageVector = Icons.Outlined.MailOutline,
                contentDescription = "Notifications",
                tint = Color.White
            )
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add",
                tint = Color.White,
                modifier = Modifier.clickable{
                    onAddExpenseClick.invoke()
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ExpensesPreview() {
    val previewExpenses = listOf(
        ExpenseCategoryUiModel(
            id = 1,
            title = "Electricity",
            amountLabel = "60,00",
            iconEmoji = "\uD83D\uDCA1",
            backgroundColor = Color(0xFFFFF3CD),
            contentColor = Color.Black
        )
    )
    val previewState = ExpensesUiState(
        isLoading = false,
        budgetLabel = "₹5430.60",
        dateLabel = "18 May 2020",
        totalLabel = "270,00",
        expenses = previewExpenses
    )
    ExpenseTrackerTheme {
        ExpensesContent(state = previewState, onAddExpenseClick = {

        })
    }
}

