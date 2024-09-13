package com.example.phinconattendance.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phinconattendance.data.repository.AuthRepository
import com.example.phinconattendance.data.model.ResultState
import com.example.phinconattendance.data.repository.DatabaseRepository
import com.example.phinconattendance.data.model.User
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RegisterUiState(
    val name : String = "",
    val nameError : String? = "",
    val email : String = "",
    val emailError : String? = null,
    val password : String = "",
    val passwordError : String? = null,
    val confirmPassword : String = "",
    val confirmError : String? = null,
    val isLoading: Boolean = false,
    val isRegister : Boolean = false,
    val currentUser: FirebaseUser? = null,
    val userMessage : String?= null,
    val errorMessage: Throwable? = null
)
@HiltViewModel
class RegisterViewModel  @Inject constructor(
    private val repository: AuthRepository,
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<RegisterUiState> = MutableStateFlow(RegisterUiState())
    val uiState: MutableStateFlow<RegisterUiState> get() = _uiState

    fun onNameChange(newName: String) {
        val error = when {
            newName.isBlank() -> "Name cannot be empty"
            else -> null
        }
        _uiState.value = _uiState.value.copy(
            name = newName, nameError = error
        )
    }

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

    fun onPasswordChange(newPassword: String, newConfirm: String) {
        val errorPassword = when {
            newPassword.isBlank() -> "Password cannot be empty"
            newPassword.length < 8 -> "Password must be at least 8 characters"
            newPassword != newConfirm -> "Password do not match"
            else -> null
        }

        val errorConfirm = when {
            newConfirm.isBlank() -> "Password cannot be empty"
            newConfirm.length < 8 -> "Password must be at least 8 characters"
            newPassword != newConfirm -> "Password do not match"
            else -> null
        }

        _uiState.value = _uiState.value.copy(
            password = newPassword, passwordError = errorPassword,
            confirmPassword = newConfirm, confirmError = errorConfirm
        )
    }

    fun registerEmailAndPassword() {
        viewModelScope.launch {
            repository.registerUser(_uiState.value.name,_uiState.value.email,_uiState.value.password).collect{response ->
                when(response){
                    is ResultState.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is ResultState.Success -> {
                        saveDataToDatabase(response.data,User(_uiState.value.name,_uiState.value.email,"","",0))
                    }
                    is ResultState.Failure -> {
                        _uiState.value = _uiState.value.copy(isLoading = false, isRegister = false, errorMessage = response.msg)
                    }
                }
            }
        }
    }

    private fun saveDataToDatabase(key : String,item: User){
        viewModelScope.launch {
            databaseRepository.insertUser(key,item).collect{response ->
                when(response){
                    is ResultState.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _uiState.value = _uiState.value.copy(isLoading = false, isRegister = true, userMessage = response.data)
                    }
                    is ResultState.Failure -> {
                        _uiState.value = _uiState.value.copy(isLoading = false, isRegister = false, errorMessage = response.msg)
                    }
                }
            }
        }
    }

}