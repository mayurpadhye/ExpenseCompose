package com.zaplogic.expensetracker.presentation.ui.screens.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.zaplogic.expensetracker.R
import com.zaplogic.expensetracker.presentation.viewmodels.LoginEvent
import com.zaplogic.expensetracker.presentation.ui.components.CustomDropDown
import com.zaplogic.expensetracker.presentation.ui.theme.Dimens
import com.zaplogic.expensetracker.presentation.ui.components.ReadOnlyTextFieldWithCalendar
import com.zaplogic.expensetracker.presentation.ui.components.RoundedCornerEditText
import com.zaplogic.expensetracker.presentation.viewmodels.AuthenticationViewModel
import com.zaplogic.expensetracker.presentation.common.SnackbarManager
import kotlinx.coroutines.delay

@Composable
fun SignUpScreen(
    viewModel: AuthenticationViewModel,
    onDashBoardCallback: () -> Unit = {},
) {

    val signupUiState by viewModel.signupUiState

    LaunchedEffect(key1 = true) {
        viewModel.loginEvent.collect { event ->
            when (event) {
                is LoginEvent.NavigateToDashboard -> {
                    delay(200)
                    onDashBoardCallback()
                }

                is LoginEvent.SignUpEventFail -> {
                    SnackbarManager.showMessage(event.message)

                }
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(horizontal = Dimens.dp_15)
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(Dimens.dp_130))
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = Dimens.sp_30,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Medium,
            text = stringResource(R.string.str_sign_up)
        )
        Spacer(modifier = Modifier.height(Dimens.dp_15))
        Text(
            modifier = Modifier.fillMaxWidth(),
            color = colorResource(R.color.black),
            fontSize = Dimens.sp_15,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            fontStyle = FontStyle.Italic,
            text = stringResource(R.string.str_signup_msg)
        )

        Spacer(modifier = Modifier.height(Dimens.dp_20))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.tertiary,
                        shape = RoundedCornerShape(Dimens.dp_10)
                    )
                    .width(Dimens.dp_130),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(Dimens.dp_10)
                ) {
                    Image(
                        modifier = Modifier
                            .size(Dimens.dp_25)
                            .clip(RoundedCornerShape(Dimens.dp_20))
                            .background(color = Color.Transparent),
                        contentScale = ContentScale.Crop,
                        painter = painterResource(R.drawable.fb_logo),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.width(Dimens.dp_5))
                    Text(
                        text = "Facebook"
                    )
                }
            }

            Spacer(modifier = Modifier.padding(Dimens.dp_15))

            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.tertiary,
                        shape = RoundedCornerShape(Dimens.dp_10)
                    )
                    .defaultMinSize(minWidth = Dimens.dp_130),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(Dimens.dp_10)
                ) {
                    Image(
                        modifier = Modifier
                            .size(Dimens.dp_25)
                            .clip(RoundedCornerShape(Dimens.dp_20))
                            .background(color = Color.Transparent),
                        contentScale = ContentScale.Crop,
                        painter = painterResource(R.drawable.google_logo),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.width(Dimens.dp_5))
                    Text(
                        text = "Google"
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(Dimens.dp_20))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(
                modifier = Modifier
                    .height(Dimens.dp_1)
                    .fillMaxWidth()
                    .weight(0.45f)
                    .background(color = Color.LightGray)
            )
            Text("Or", modifier = Modifier.padding(horizontal = Dimens.dp_10))
            Spacer(
                modifier = Modifier
                    .weight(0.45f)
                    .height(Dimens.dp_1)
                    .fillMaxWidth()
                    .background(color = Color.LightGray)
            )

        }

        RoundedCornerEditText(
            placeHolder = "Enter name",
            isError = !signupUiState.nameValidationError.isNullOrEmpty(),
            errorMsg = signupUiState.nameValidationError
        ) {
            viewModel.onUserNameChange(it)
        }
        RoundedCornerEditText(
            placeHolder = "Enter EmailId",
            isError = !signupUiState.emailValidationError.isNullOrEmpty(),
            errorMsg = signupUiState.emailValidationError,
            keyboardType = KeyboardType.Email,
            onValueChanged = {
                viewModel.onUserEmailChange(it)
            }
        )
        RoundedCornerEditText(
            placeHolder = "Enter Mobile Number",
            isMobileNumber = true,
            keyboardType = KeyboardType.Phone,
            isError = !signupUiState.mobileNumberValidationError.isNullOrEmpty(),
            errorMsg = signupUiState.mobileNumberValidationError
        ) {
            viewModel.onUserMobileNumberChange(it)
        }

        ReadOnlyTextFieldWithCalendar(
            signupUiState.dob ?: "",
            isError =
                !signupUiState.dobValidationError.isNullOrEmpty(),
            errorMsg = signupUiState.dobValidationError
        ) {
            viewModel.onUserDobChange(it)
        }

        CustomDropDown(
            signupUiState.gender,
            isError =
                !signupUiState.genderValidationError.isNullOrEmpty(),
            errorMsg = signupUiState.genderValidationError, onValueSelected = {
                viewModel.onUserGenderChange(it)
            })

        RoundedCornerEditText(
            placeHolder = "Enter Address",
            isError = !signupUiState.addressValidationError.isNullOrEmpty(),
            errorMsg = signupUiState.addressValidationError
        ) {
            viewModel.onUserAddressChange(it)
        }

        RoundedCornerEditText(
            placeHolder = "Enter Password",
            keyboardType = KeyboardType.Password,
            isError = !signupUiState.passwordValidationError.isNullOrEmpty(),
            errorMsg = signupUiState.passwordValidationError
        ) {
            viewModel.onUserPasswordChange(it)
        }

        Spacer(modifier = Modifier.padding(top = Dimens.dp_15))

        ElevatedButton(
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = Dimens.dp_10,
                pressedElevation = Dimens.dp_15,
                focusedElevation = Dimens.dp_10
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.color_primary),
                contentColor = Color.White,
                disabledContentColor = Color.DarkGray,
                disabledContainerColor = Color.LightGray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.dp_50)
                .padding(horizontal = Dimens.dp_15),
            onClick = {
                viewModel.validateSignUpForm()
            }

        ) {
            Text(stringResource(R.string.str_sign_up))
        }

    }

}