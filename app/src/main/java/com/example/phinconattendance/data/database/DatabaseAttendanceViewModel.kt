package com.example.phinconattendance.data.database

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phinconattendance.data.auth.AuthRepository
import com.example.phinconattendance.data.auth.ResultState
import com.example.phinconattendance.data.model.AttendanceResponse
import com.example.phinconattendance.data.model.PlaceResponse
import com.example.phinconattendance.data.preference.DataStoreRepository
import com.example.phinconattendance.navigation.Screen
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatabaseAttendanceViewModel@Inject constructor(
    private val repository: DatabaseRepository,
    private val auth : AuthRepository,
    private val data: DataStoreRepository
) : ViewModel() {

    private val currentUser: FirebaseUser? get() = auth.currentUser

    private val _res: MutableState<ItemStateAttendance> = mutableStateOf(ItemStateAttendance())
    val res: State<ItemStateAttendance> = _res

    private val _listRes: MutableState<PlaceResponse.Place> = mutableStateOf(PlaceResponse.Place())
    val listRes: State<PlaceResponse.Place> = _listRes

    fun setDataItem(data: PlaceResponse.Place) {
        _listRes.value = data
    }

    fun insertAttendance(userId: String, key: String, items: AttendanceResponse.Attendance) =
        repository.insertAttendance(userId, key, items)

    fun getAttendance() {
        viewModelScope.launch {
            repository.getAttendance(currentUser!!.uid).collect {
                when (it) {
                    is ResultState.Success -> {
                        _res.value = ItemStateAttendance(
                            item = it.data
                        )
                    }
                    is ResultState.Failure -> {
                        _res.value = ItemStateAttendance(
                            error = it.msg.toString()
                        )
                    }
                    ResultState.Loading -> {
                        _res.value = ItemStateAttendance(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }

    private val _isButtonClicked = MutableStateFlow(false)
    val isButtonClicked = _isButtonClicked

    suspend fun setButtonClick(click:Boolean){
        data.saveOnButtonClickState(click)
    }

    fun getButtonClicked(){
        viewModelScope.launch {
            data.readOnBoardingState().collect { click ->
                _isButtonClicked.value = !click
            }
        }
    }

}


data class ItemStateAttendance(
    val item:List<AttendanceResponse> = emptyList(),
    val error:String  = "",
    val isLoading:Boolean = false
)
