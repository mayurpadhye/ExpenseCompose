package com.zaplogic.expensetracker.presentation.ui.screens.splash_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.zaplogic.expensetracker.R
import com.zaplogic.expensetracker.presentation.navigation.NavigationUtils
import com.zaplogic.expensetracker.presentation.viewmodels.AuthenticationViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController,
    loginViewModel: AuthenticationViewModel = hiltViewModel()
){

    LaunchedEffect(key1 = Unit) {
        delay(2000)
        if (loginViewModel.checkIsUserAlreadyLoggedIn()){
            NavigationUtils.navigateToDashboard(navController)
        } else {
            NavigationUtils.navigateToOnBoardingScreen(navController)
        }

    }


    Box (
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF63B5AF),
                        Color(0xFF2C6975),
                    )
                )
            )
    ){

        Text(text = stringResource(R.string.str_expense_tracker),
            color = Color.White,
            style = TextStyle.Default,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Black,
            fontSize = 25.sp)
    }

}