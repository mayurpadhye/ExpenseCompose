package com.zaplogic.expensetracker.presentation.viewmodels

import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaplogic.expensetracker.data.remote.dto.SignUpRequestDto
import com.zaplogic.expensetracker.data.network.Resource
import com.zaplogic.expensetracker.domain.shared_prefernce.PreferenceManager
import com.zaplogic.expensetracker.presentation.ui.screens.authentication.state.LoginUiState
import com.zaplogic.expensetracker.domain.usecases.LoginUseCase
import com.zaplogic.expensetracker.domain.usecases.SignUpUseCase
import com.zaplogic.expensetracker.presentation.ui.screens.authentication.state.SignupUiState
import com.zaplogic.expensetracker.core.enum_types.GenderTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val sharedPreference: PreferenceManager
) : ViewModel() {

    private val _loginState : MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val loginState: StateFlow<LoginUiState> = _loginState.asStateFlow()

    private val _signUpState = mutableStateOf(SignupUiState())
    val signupUiState: MutableState<SignupUiState> = _signUpState

    private val _loginEvent = Channel<LoginEvent>()
    val loginEvent = _loginEvent.receiveAsFlow()


    fun doLogin() {

        viewModelScope.launch {
            loginUseCase(
                username = _loginState.value.emailInput,
                password = _loginState.value.passwordInput
            ).onEach { value ->
                when (value) {
                    is Resource.Error -> {
                        _loginState.value =
                            LoginUiState(error = value.message ?: "Unexpected error occurred")
                    }

                    is Resource.Loading -> {
                        _loginState.value = LoginUiState(isLoading = true)
                    }

                    is Resource.Success -> {
                        if (value.data != null) {
                            sharedPreference.setIsUserLoggedIn(true)
                            sharedPreference.saveUserId(value.data.id)
                            sharedPreference.saveUserEmail(value.data.email)
                            sharedPreference.saveAccessToken(value.data.token)
                            delay(200)
                            _loginEvent.send(LoginEvent.NavigateToDashboard)
                        }

                    }
                }

            }.launchIn(viewModelScope)

        }
    }

    fun validateData() {
        val isUsernameValid = validateUserName(_loginState.value.emailInput)
        if (!isUsernameValid) {
            _loginState.value = _loginState.value.copy(
                usernameError = "Please enter a valid email"
            )
        }
        val isPasswordValid = validatePassword(_loginState.value.passwordInput)
        if (!isPasswordValid) {
            _loginState.value = _loginState.value.copy(
                passwordError = "Password cannot be empty"
            )
        }

        if (isUsernameValid && isPasswordValid) {
            doLogin()
        }
    }

    fun onEmailChange(newValue: String) {
        _loginState.value = _loginState.value.copy(
            emailInput = newValue,
            usernameError = null // Clear error when user types
        )
    }

    fun onPasswordChange(newValue: String) {
        _loginState.update { currentState ->
            currentState.copy(
                passwordInput = newValue,
                passwordError = null // Clear error when user types
            )
        }
    }

    fun onShowPassword() {
        _loginState.value = _loginState.value.copy(
            isShowPassword = !_loginState.value.isShowPassword
        )
    }

    fun onRememberMeChange() {
        _loginState.value = _loginState.value.copy(
            rememberMe = !_loginState.value.rememberMe
        )
    }

    fun validateUserName(username: String): Boolean {
        val isUsernameValid =
            username.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(username).matches()
        return isUsernameValid
    }

    fun validatePassword(password: String): Boolean {
        val isPasswordValid = password.isNotEmpty()
        return isPasswordValid
    }

    fun checkIsUserAlreadyLoggedIn(): Boolean {
        return sharedPreference.isUserLoggedIn()
    }

    fun onUserNameChange(name: String) {
        _signUpState.value = _signUpState.value.copy(
            name = name,
            nameValidationError = null
        )
    }

    fun onUserEmailChange(email: String) {
        _signUpState.value = _signUpState.value.copy(
            email = email,
            emailValidationError = null
        )
    }

    fun onUserMobileNumberChange(mobile: String) {
        _signUpState.value = _signUpState.value.copy(
            mobileNumber = mobile,
            mobileNumberValidationError = null
        )

    }

    fun onUserDobChange(dob: String) {
        _signUpState.value = _signUpState.value.copy(
            dob = dob,
            dobValidationError = null

        )

    }

    fun onUserGenderChange(gender: GenderTypes?) {
        _signUpState.value = _signUpState.value.copy(
            gender = gender,
            genderValidationError = null
        )

    }

    fun onUserAddressChange(address: String) {
        _signUpState.value = _signUpState.value.copy(
            address = address,
            addressValidationError = null
        )
    }

    fun onUserPasswordChange(password: String) {
        _signUpState.value = _signUpState.value.copy(
            password = password,
            passwordValidationError = null
        )
    }

    fun validateSignUpForm() { val name = _signUpState.value.name.orEmpty()
        val email = _signUpState.value.email.orEmpty()
        val mobile = _signUpState.value.mobileNumber.orEmpty()
        val gender = _signUpState.value.gender
        val dob = _signUpState.value.dob
        val address = _signUpState.value.address.orEmpty()
        val password = _signUpState.value.password.orEmpty()

        // Validate individual fields
        val isNameValid = name.isNotEmpty()
        val isEmailValid = validateUserName(email)
        val isMobileValid = validateMobileNumber(mobile)
        val isGenderValid = validateGender(gender)
        val isDobValid = validateDOB(dob) // Ensure DOB is NOT empty
        val isAddressValid = address.isNotEmpty()
        val isPasswordValid = validatePassword(password)

        // Update state with error messages if invalid
        _signUpState.value = _signUpState.value.copy(
            nameValidationError = if (isNameValid) null else "Name cannot be empty",
            emailValidationError = if (isEmailValid) null else "Please enter a valid email",
            mobileNumberValidationError = if (isMobileValid) null else "Please enter a valid mobile number",
            genderValidationError = if (isGenderValid) null else "Please select a gender",
            dobValidationError = if (isDobValid) null else "Please select a date of birth",
            addressValidationError = if (isAddressValid) null else "Address cannot be empty",
            passwordValidationError = if (isPasswordValid) null else "Password cannot be empty"
        )

        // Only proceed if ALL validations pass
        if (isNameValid && isEmailValid && isMobileValid && isGenderValid && isDobValid && isAddressValid && isPasswordValid) {
            signUp()
        }
    }

    fun validateMobileNumber(mobileNumber: String): Boolean {
        val regex = Regex("^[6-9]\\d{9}$")
        return regex.matches(mobileNumber)
    }

    fun validateGender(gender: GenderTypes?): Boolean {
        return gender != null
    }

    fun validateDOB(dob: String?): Boolean {
        return !dob.isNullOrEmpty()
    }

    fun signUp() {
        viewModelScope.launch {
            signUpUseCase(
                SignUpRequestDto(
                    name = _signUpState.value.name,
                    email = _signUpState.value.email,
                    mobileNumber = _signUpState.value.mobileNumber?.toLong(),
                    gender = _signUpState.value.gender?.name,
                    address = _signUpState.value.address,
                    password = _signUpState.value.password,
                    dob = _signUpState.value.dob
                )
            ).onEach {
                when (it) {
                    is Resource.Error-> {
                        _signUpState.value =_signUpState.value.copy(
                            isLoading = false,
                            isSignUpSuccess = false
                        )
                        _loginEvent.send(LoginEvent.SignUpEventFail(it.message.toString()))
                    }
                    is Resource.Loading -> {
                        _signUpState.value = _signUpState.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        if (it.data != null) {
                            sharedPreference.setIsUserLoggedIn(true)
                            sharedPreference.saveUserEmail(it.data.email)
                            _loginState.value = _loginState.value.copy(
                                emailInput = _signUpState.value.email.toString(),
                                passwordInput = _signUpState.value.password.toString()
                            )
                            //sharedPreference.saveAccessToken(it.data.token)
                            delay(200)
                            doLogin()
                        //  _loginEvent.send(LoginEvent.NavigateToDashboard)
                        }
                    }
                }
            }.launchIn(viewModelScope)
        }

    }

}

sealed class LoginEvent {
    object NavigateToDashboard : LoginEvent()
    data class SignUpEventFail(val message : String) : LoginEvent()
}