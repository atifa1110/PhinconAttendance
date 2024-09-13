package com.example.phinconattendance.screen.forgot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phinconattendance.data.repository.AuthRepository
import com.example.phinconattendance.data.model.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ForgotUiState(
    val email : String = "",
    val emailError : String? = "",
    val isLoading : Boolean = false,
    val isSuccess : Boolean = false,
    val userMessage : String? = null,
    val errorMessage : Throwable? = null
)
@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val repository: AuthRepository
) :ViewModel() {

    private val _uiState: MutableStateFlow<ForgotUiState> = MutableStateFlow(ForgotUiState())
    val uiState: MutableStateFlow<ForgotUiState> get() = _uiState

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

    fun resetPassword() {
        viewModelScope.launch {
            repository.resetPassword(_uiState.value.email).collect{ response ->
                when(response) {
                    is ResultState.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _uiState.value = _uiState.value.copy(isLoading = false, isSuccess = true, userMessage = response.data)
                    }
                    is ResultState.Failure -> {
                        _uiState.value = _uiState.value.copy(isLoading = false, isSuccess = false, errorMessage = response.msg)
                    }
                }
            }
        }
    }
}