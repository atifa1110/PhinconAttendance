package com.example.phinconattendance.screen.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phinconattendance.data.repository.AuthRepository
import com.example.phinconattendance.data.model.ResultState
import com.example.phinconattendance.data.repository.DatabaseRepository
import com.example.phinconattendance.data.repository.DataStoreRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val name : String? = "",
    val role : String? = "",
    val address : String? = "",
    val password : String? = "",
    val employeeNumber: String? ="",
    val isLoading : Boolean = false,
    val errorMessage : Throwable? = null
)
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val authRepository: AuthRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel(){

    private val _uiState: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState())
    val uiState: MutableStateFlow<ProfileUiState> get() = _uiState

    private val currentUser: FirebaseUser? get() = authRepository.currentUser

    init {
        getCurrentUserData()
    }

    private fun getCurrentUserData(){
        viewModelScope.launch {
            databaseRepository.getUser(currentUser?.uid ?: "").collect { response ->
                when(response){
                    is ResultState.Failure -> {
                        _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = response.msg)
                    }
                    ResultState.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is ResultState.Success -> {
                        val result = response.data
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            name = result.item?.name,
                            address = result.item?.address,
                            role = "PROGRAMMER",
                            employeeNumber = result.item?.employee.toString()
                        )
                    }
                }
            }
        }
    }

    fun saveOnLoginState(complete: Boolean){
        viewModelScope.launch (Dispatchers.IO){
            dataStoreRepository.saveOnLoginState(complete)
        }
    }
    fun onLogout() = authRepository.logout()
}