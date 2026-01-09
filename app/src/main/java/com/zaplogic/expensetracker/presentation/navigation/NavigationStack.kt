package com.zaplogic.expensetracker.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zaplogic.expensetracker.presentation.ui.components.CommonToolbar
import com.zaplogic.expensetracker.presentation.ui.screens.expense.AddExpenseScreen
import com.zaplogic.expensetracker.presentation.ui.screens.expense.ExpensesScreen
import com.zaplogic.expensetracker.presentation.ui.screens.onBoardingScreen.OnBoardingScreen
import com.zaplogic.expensetracker.presentation.ui.screens.authentication.SignInScreen
import com.zaplogic.expensetracker.presentation.ui.screens.authentication.SignUpScreen
import com.zaplogic.expensetracker.presentation.ui.screens.splash_screen.SplashScreen
import com.zaplogic.expensetracker.presentation.viewmodels.AuthenticationViewModel
import com.zaplogic.expensetracker.core.common.Utils
import com.zaplogic.expensetracker.presentation.common.SnackbarManager
import kotlinx.coroutines.launch

@Composable
fun NavigationStack() {
    val navController = rememberNavController()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val screensWithoutToolbar = listOf(NavigationItem.LoginScreenItem.route,
        NavigationItem.SplashScreenItem.route,
        NavigationItem.OnBoardingScreenItem.route,
        NavigationItem.SignUpScreenItem.route,
        NavigationItem.DashboardScreenItem.route)
    // Logic to set title based on route


    LaunchedEffect(key1 = true) {
        SnackbarManager.messages.collect { message ->
            scope.launch {
                snackbarHostState.showSnackbar(message)
            }
        }
    }

    Scaffold(
        topBar = {
            if (currentRoute !in screensWithoutToolbar) {
                CommonToolbar(
                    title = Utils.getTitleForRoute(currentRoute),
                    canNavigateBack = true,
                    onSideMenuClick = {
                    },
                    onBackClick = {
                     navController.popBackStack()
                    }
                )
            }

        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)) {
            NavHost(

                navController = navController,
                startDestination = NavigationItem.SplashScreenItem.route
            ) {
                composable(route = NavigationItem.SplashScreenItem.route) {
                    SplashScreen(navController)
                }

                composable(route = NavigationItem.LoginScreenItem.route) {
                    SignInScreen(
                        onSignUpClick = {
                            NavigationUtils.navigateToSignUpScreen(navController)
                        },
                        onForgotPasswordClick = {

                        },
                        onDashBoardCallback = {
                            NavigationUtils.navigateToDashboard(navController)
                        }
                    )
                }

                composable(route = NavigationItem.SignUpScreenItem.route) {
                    val loginViewModel: AuthenticationViewModel = hiltViewModel()
                    SignUpScreen( loginViewModel,
                        onDashBoardCallback = {
                            NavigationUtils.navigateToDashboard(navController)
                        })
                }

                composable(route = NavigationItem.DashboardScreenItem.route) {
                    //  DashBoardScreen()
                    ExpensesScreen(onAddExpenseClick = {
                        NavigationUtils.navigateToAddExpenseScreen(navController)
                    })
                }

                composable(route = NavigationItem.OnBoardingScreenItem.route) {
                    OnBoardingScreen(
                        navController, onGetStartedClick = {
                            NavigationUtils.navigateToSignUpScreen(navController)
                        },
                        onLoginClick = {
                            NavigationUtils.navigateToSignInScreen(navController)
                        })
                }

                composable(route = NavigationItem.AddExpenseScreenItem.route) {
                    AddExpenseScreen(
                    )
                }


            }
        }

    }

}
