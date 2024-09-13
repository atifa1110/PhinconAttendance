package com.example.phinconattendance.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phinconattendance.data.repository.AuthRepository
import com.example.phinconattendance.data.model.ResultState
import com.example.phinconattendance.data.repository.DataStoreRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val email : String = "",
    val emailError : String? = null,
    val password : String = "",
    val passwordError : String? = null,
    val isLoading: Boolean = false,
    val isLogin : Boolean = false,
    val currentUser: FirebaseUser? = null,
    val userMessage : String?= null,
    val errorMessage: Throwable? = null
)
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val uiState: MutableStateFlow<LoginUiState> get() = _uiState

    init {
        // Initialize UI state with the current user if already logged in
        if (authRepository.currentUser != null) {
            _uiState.value = _uiState.value.copy(
                currentUser = authRepository.currentUser
            )
        }
    }

    // check if email is accordance with format or not
    fun onEmailChange(newEmail: String) {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)\$"
        val error = when {
            newEmail.isBlank() -> "Email cannot be empty"
            !newEmail.matches(emailRegex.toRegex()) -> "Invalid email format"
            else -> null
        }
        _uiState.value = _uiState.value.copy(
            email = newEmail, emailError = error
        )
    }

    // check if password is accordance with format or not
    fun onPasswordChange(newPassword: String) {
        val error = when {
            newPassword.isBlank() -> "Password cannot be empty"
            newPassword.length < 8 -> "Password must be at least 8 characters"
            else -> null
        }
        _uiState.value = _uiState.value.copy(
            password = newPassword, passwordError = error
        )
    }

    // save login state when button click
    fun saveOnLoginState(complete : Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveOnLoginState(complete)
        }
    }

    // login data
    fun loginAccount(){
        viewModelScope.launch {
            authRepository.loginUser(_uiState.value.email, _uiState.value.password).collect { response ->
                when(response){
                    is ResultState.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _uiState.value = _uiState.value.copy(isLoading = false, isLogin = true, userMessage = response.data)
                    }
                    is ResultState.Failure -> {
                        _uiState.value = _uiState.value.copy(isLoading = false, isLogin = false, errorMessage = response.msg)
                    }
                }
            }
        }
    }
}