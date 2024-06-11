package com.example.phinconattendance.data.auth

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    val current: MutableState<String> = mutableStateOf("")

    val currentUser: FirebaseUser? get() = repository.currentUser

    init {
        if (repository.currentUser != null) {
            current.value = currentUser!!.uid
        }
    }

    fun resetPassword(email: String) = repository.resetPassword(email)

    fun changePassword (password: String) = repository.updatePassword(password)

    fun loginUser(email: String, password: String) = repository.loginUser(email,password)

    fun registerUser(name: String,email: String, password: String) = repository.registerUser(name,email,password)

    fun logout() {
        repository.logout()
        current.value = " "
    }

}