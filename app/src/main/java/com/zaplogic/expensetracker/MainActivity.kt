package com.zaplogic.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.zaplogic.expensetracker.presentation.navigation.NavigationItem
import com.zaplogic.expensetracker.presentation.navigation.NavigationStack
import com.zaplogic.expensetracker.presentation.ui.components.SessionExpiredDialog
import com.zaplogic.expensetracker.presentation.ui.theme.ExpenseTrackerTheme
import com.zaplogic.expensetracker.core.events.SessionEvents
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var sessionEvents: SessionEvents
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            ExpenseTrackerTheme {
                val navController = rememberNavController()
                var showExpiryDialog by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    sessionEvents.logoutEvent.collect {
                        showExpiryDialog = true
                    }
                }
                Surface(
                    color = MaterialTheme.colorScheme.background) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        NavigationStack()
                        if (showExpiryDialog) {
                            SessionExpiredDialog(
                                onConfirm = {
                                    showExpiryDialog = false
                                    navController.navigate(NavigationItem.OnBoardingScreenItem.route) {
                                        popUpTo(NavigationItem.OnBoardingScreenItem.route) {
                                            inclusive = true
                                        }
                                    }
                                })}

                    }


                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ExpenseTrackerTheme {
        Greeting("Android")
    }
}