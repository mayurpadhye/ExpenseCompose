package com.zaplogic.expensetracker.core.common

import com.zaplogic.expensetracker.core.enum_types.ExpenseCategoryType
import com.zaplogic.expensetracker.presentation.navigation.NavigationItem

object Utils {

    fun getAllExpenseCategories() : List<ExpenseCategoryType>{
        return listOf(
            ExpenseCategoryType.SELECT_CATEGORY,
            ExpenseCategoryType.TRAVELLING,
            ExpenseCategoryType.HOUSE_RENT,
            ExpenseCategoryType.FOOD,
            ExpenseCategoryType.BILLS,
            ExpenseCategoryType.ENTERTAINMENT,
            ExpenseCategoryType.GROCERIES,
            ExpenseCategoryType.LOAN,
            ExpenseCategoryType.SHOPPING,
            ExpenseCategoryType.OTHER
        )
    }

     fun getTitleForRoute(route: String?): String {
        return when (route) {
            NavigationItem.AddExpenseScreenItem.route -> "Add Expenses"
            NavigationItem.DashboardScreenItem.route -> "Home"
            else -> "App Name"
        }

    }
}