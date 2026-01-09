package com.zaplogic.expensetracker.presentation.ui.screens.authentication

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zaplogic.expensetracker.presentation.ui.theme.BackgroundColor
import com.zaplogic.expensetracker.presentation.ui.theme.ExpenseTrackerTheme
import com.zaplogic.expensetracker.presentation.ui.theme.Dimens
import com.zaplogic.expensetracker.presentation.viewmodels.LoginEvent
import com.zaplogic.expensetracker.presentation.viewmodels.AuthenticationViewModel
import kotlinx.coroutines.delay

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    onForgotPasswordClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
    onDashBoardCallback: () -> Unit = {},
    loginViewModel: AuthenticationViewModel = hiltViewModel()
) {

    val loginState by loginViewModel.loginState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        loginViewModel.loginEvent.collect { event ->
            when (event) {
                is LoginEvent.NavigateToDashboard -> {
                    delay(200)
                    onDashBoardCallback()
                }

                else -> {

                }
            }
        }
    }

    Box {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            SignInHeader()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Sign in",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF313131)
                    )
                )

                // --- EMAIL FIELD ---
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Email",
                        style = MaterialTheme.typography.labelLarge.copy(color = Color.Gray)
                    )
                    OutlinedTextField(
                        value = loginState.emailInput,
                        onValueChange = {
                            loginViewModel.onEmailChange(it.trim())
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "Email",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        // Show error state red outline
                        isError = !loginState.usernameError.isNullOrEmpty(),
                        // Show error text below field
                        supportingText = {
                            if (!loginState.usernameError.isNullOrEmpty()) {
                                Text(
                                    text = loginState.usernameError!!,
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        },
                        placeholder = { Text(text = "demo@email.com") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true,
                        shape = RoundedCornerShape(18.dp),
                        colors = textFieldColors(),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // --- PASSWORD FIELD ---
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Password",
                        style = MaterialTheme.typography.labelLarge.copy(color = Color.Gray)
                    )
                    OutlinedTextField(
                        value = loginState.passwordInput,
                        onValueChange = {
                            loginViewModel.onPasswordChange(it.trim())
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Password",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = if (loginState.isShowPassword) {
                                    Icons.Filled.Visibility
                                } else {
                                    Icons.Filled.VisibilityOff
                                },
                                contentDescription = "Toggle password visibility",
                                tint = Color.Gray,
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
                                        loginViewModel.onShowPassword()
                                    }
                            )
                        },
                        // Show error state red outline
                        isError = !loginState.passwordError.isNullOrEmpty(),
                        // Show error text below field
                        supportingText = {
                            if (!loginState.passwordError.isNullOrEmpty()) {
                                Text(
                                    text = loginState.passwordError!!,
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        },
                        placeholder = { Text(text = "Enter your password") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = if (loginState.isShowPassword) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(18.dp),
                        colors = textFieldColors(),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = loginState.rememberMe,
                            onCheckedChange = {
                                loginViewModel.onRememberMeChange()

                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = MaterialTheme.colorScheme.primary
                            )
                        )
                        Text(
                            text = "Remember Me",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color(0xFF444444)
                            )
                        )
                    }
                    TextButton(onClick = onForgotPasswordClick) {
                        Text(
                            text = "Forgot Password?",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Button(
                    onClick = {
                        loginViewModel.validateData()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Login",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Don't have an Account?",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                    )
                    Spacer(modifier.width(Dimens.dp_10))
                    Text(
                        modifier = Modifier.clickable {
                            onSignUpClick.invoke()
                        },
                        text = "Sign up",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Loading Indicator Overlay
        if (loginState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
private fun textFieldColors(): TextFieldColors {
    return TextFieldDefaults.colors(
        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
        unfocusedIndicatorColor = Color.LightGray,
        errorIndicatorColor = MaterialTheme.colorScheme.error, // Added error color
        cursorColor = MaterialTheme.colorScheme.primary,
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        errorContainerColor = Color.White // Keep white background even on error
    )
}

@Composable
private fun SignInHeader() {
    val gradientColors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .background(color = BackgroundColor)
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            drawRect(brush = Brush.verticalGradient(gradientColors))

            val path = Path().apply {
                moveTo(0f, size.height * 0.75f)
                quadraticBezierTo(
                    size.width * 0.25f,
                    size.height,
                    size.width * 0.5f,
                    size.height * 0.9f
                )
                quadraticBezierTo(
                    size.width * 0.75f,
                    size.height * 0.8f,
                    size.width,
                    size.height
                )
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            drawPath(
                path = path,
                color = BackgroundColor
            )
        }

        Text(
            text = "", // Add your app title here if needed
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 48.dp),
            color = Color.White,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SignInPreview() {
    ExpenseTrackerTheme {
        SignInScreen()
    }
}

