package com.zaplogic.expensetracker.presentation.navigation

sealed class Screen(val route: String) {
    object SplashScreen : Screen(NavigationItem.SplashScreenItem.name)
    object LoginScreen : Screen(NavigationItem.LoginScreenItem.name)
    object SignUpScreen : Screen(NavigationItem.SignUpScreenItem.name)
    object DashboardScreen : Screen(NavigationItem.DashboardScreenItem.name)
    object OnBoardingScreen : Screen(NavigationItem.OnBoardingScreenItem.name)
}