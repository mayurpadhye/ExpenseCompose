package com.zaplogic.expensetracker.presentation.navigation

import androidx.navigation.NavHostController

object NavigationUtils {

    fun navigateToDashboard(navController: NavHostController){
        navController.navigate(NavigationItem.DashboardScreenItem.route){
            popUpTo(NavigationItem.SplashScreenItem.route) {
                inclusive = true
            }
        }

    }

    fun navigateToOnBoardingScreen(navHostController: NavHostController){

        navHostController.navigate(NavigationItem.OnBoardingScreenItem.route){
            popUpTo(NavigationItem.SplashScreenItem.route) {
                inclusive = true
            }
        }
    }

    fun navigateToSignInScreen(navHostController: NavHostController){
        navHostController.navigate(NavigationItem.LoginScreenItem.route)
    }

    fun navigateToSignUpScreen(navHostController: NavHostController){
        navHostController.navigate(NavigationItem.SignUpScreenItem.route)
    }

    fun navigateToForgotPasswordScreen(navHostController: NavHostController){


    }

    fun navigateToAddExpenseScreen(navHostController: NavHostController){
       navHostController.navigate(NavigationItem.AddExpenseScreenItem.route)
    }
}