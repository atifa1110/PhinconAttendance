package com.example.phinconattendance.screen.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phinconattendance.data.repository.AuthRepository
import com.example.phinconattendance.data.model.ResultState
import com.example.phinconattendance.data.repository.DatabaseRepository
import com.example.phinconattendance.data.model.Place
import com.example.phinconattendance.data.mapper.asAttendance
import com.example.phinconattendance.data.repository.DataStoreRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

data class HomeUiState(
    val currentTime : String = "",
    val currentDate : String = "",
    val now : Long = 0,
    val isLoading : Boolean = false,
    val selectedLocation : Place = Place(),
    val isButtonEnable : Boolean = false,
    val isButtonCheckIn: Boolean = false,
    val checkInState : Boolean = false,
    val userMessage : String? = null,
    val errorMessage : Throwable? = null,
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val database: DatabaseRepository,
    private val auth : AuthRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: MutableStateFlow<HomeUiState> get() = _uiState

    private val currentUser: FirebaseUser? get() = auth.currentUser

    init {
        // Update the current time when the ViewModel is initialized
        setCurrentTimeDate()
        observeCheckInState()
        observeButtonEnableState()
        observeSelectedLocationState()
    }

    // Function to observe the check-in state and update the UI State
    private fun observeCheckInState() {
        viewModelScope.launch {
            dataStoreRepository.onCheckInState().collectLatest { checkIn ->
                _uiState.value = _uiState.value.copy(isButtonCheckIn = checkIn)
            }
        }
    }

    private fun observeButtonEnableState() {
        viewModelScope.launch {
            dataStoreRepository.onButtonEnableState().collectLatest { enable ->
                _uiState.value = _uiState.value.copy(isButtonEnable = enable)
            }
        }
    }

    private fun observeSelectedLocationState(){
        viewModelScope.launch {
            dataStoreRepository.onSelectedLocationState().collectLatest { location ->
                _uiState.value = _uiState.value.copy(selectedLocation = location)
            }
        }
    }

    private fun setCheckInState(checkIn : Boolean){
        viewModelScope.launch {
            dataStoreRepository.saveOnCheckInState(checkIn)
        }
    }

    private fun setButtonEnableState(enable : Boolean){
        viewModelScope.launch {
            dataStoreRepository.saveOnButtonEnableState(enable)
        }
    }

    private fun setSelectionLocationState(place: Place){
        viewModelScope.launch {
            dataStoreRepository.saveOnSelectedLocationState(place)
        }
    }

    // Function to observe current time and update to UI State
    private fun setCurrentTimeDate() {
        // Get the current time in the desired format
        val now = System.currentTimeMillis()
        val currentTimeFormatted = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.getDefault()).format(now)
        val mString = currentTimeFormatted.split(" ").toTypedArray()

        val date = mString[0]+" "+mString[1]+" "+mString[2]
        val time = mString[3]

        // Update the time and save to UI State
        _uiState.value = _uiState.value.copy(currentTime = time, currentDate = date)
    }

    // Function to when list location being click and update to UI State
    fun onLocationSelected(place: Place){
        // List Location being click will set button enable true
        setButtonEnableState(true)
        observeButtonEnableState()
        setSelectionLocationState(place)
        observeSelectedLocationState()
    }

    // Function when button check in being click and update To UI State
    fun onCheckInButtonClick(){
        val isButtonClick = _uiState.value.isButtonCheckIn
        val place = _uiState.value.selectedLocation

        if(isButtonClick){
            // if button state is true than check out
            // save check in state into false
            setCheckInState(false)
            // observe and update to UI
            observeCheckInState()
            // insert check out data to database
            insertCheckInOutToDatabase(place, "Check out")
            // After check out button enable will false and selected data will be null
            setButtonEnableState(false)
            setSelectionLocationState(Place())
        }else{
            // if button state is false than check in
            // save check in state into true
            setCheckInState(true)
            // observe and update to UI
            observeCheckInState()
            // insert check in data to database
            insertCheckInOutToDatabase(place, "Check in")
        }
    }

    // Function to when button check in click will save data to database and Update UI State
    private fun insertCheckInOutToDatabase(place : Place, attend : String) {
        viewModelScope.launch {
            val key = System.currentTimeMillis()
            val attendance = place.asAttendance(time = key, attend = attend)
            database.insertAttendance(currentUser?.uid ?: "", key.toString(), attendance).collect { response ->
                when (response) {
                    // if response is failed
                    is ResultState.Failure -> {
                        // update loading and error message state
                        _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = response.msg)
                    }

                    // if response is loading
                    ResultState.Loading -> {
                        // update loading state
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }

                    // if response is success
                    is ResultState.Success -> {
                        // update loading and userMessage state
                        _uiState.value =
                            _uiState.value.copy(isLoading = false, userMessage = response.data)
                    }
                }
            }
        }
    }
}