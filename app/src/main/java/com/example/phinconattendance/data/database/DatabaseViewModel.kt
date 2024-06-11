package com.example.phinconattendance.data.database

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phinconattendance.data.auth.AuthRepository
import com.example.phinconattendance.data.auth.ResultState
import com.example.phinconattendance.data.model.AttendanceResponse
import com.example.phinconattendance.data.model.UserResponse
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatabaseViewModel @Inject constructor(
    private val repository: DatabaseRepository,
    private val auth: AuthRepository
): ViewModel() {

    private val _resUser: MutableState<ItemStateUser> = mutableStateOf(ItemStateUser())
    val resUser: State<ItemStateUser> = _resUser

    fun insert(key:String,items: UserResponse.User) = repository.insertUser(key, items)

    private val currentUser: FirebaseUser? get() = auth.currentUser

    fun logout() {
        auth.logout()
    }

    suspend fun getUser(){
        viewModelScope.launch {
            repository.getUser(currentUser!!.uid).collect {
                when (it) {
                    is ResultState.Success -> {
                        _resUser.value = ItemStateUser(
                            item = it.data
                        )
                    }
                    is ResultState.Failure -> {
                        _resUser.value = ItemStateUser(
                            error = it.msg.toString()
                        )
                    }
                    ResultState.Loading -> {
                        _resUser.value = ItemStateUser(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }
}


data class ItemStateUser(
    val item:UserResponse?=UserResponse(UserResponse.User()),
    val error:String  = "",
    val isLoading:Boolean = false
)