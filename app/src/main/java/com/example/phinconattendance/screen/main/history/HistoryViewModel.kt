package com.example.phinconattendance.screen.main.history

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phinconattendance.R
import com.example.phinconattendance.data.repository.AuthRepository
import com.example.phinconattendance.data.model.ResultState
import com.example.phinconattendance.data.repository.DatabaseRepository
import com.example.phinconattendance.data.model.AttendanceResponse
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.Year
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import javax.inject.Inject

data class HistoryUiState(
    val isLoading : Boolean = false,
    val item : List<AttendanceResponse> = emptyList(),
    val filterUiState: FilterUiState = FilterUiState(),
    val errorMessage : Throwable? = null,
)

const val HISTORY_FILTER_SAVED_STATE_KEY = "SELECTED_FILTER"

data class FilterUiState(
    val noTasksLabel: Int = R.string.no_history_day,
)

@HiltViewModel
class HistoryViewModel@Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val authRepository: AuthRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(){
    private val currentUser : FirebaseUser? get() = authRepository.currentUser

    private val _isLoading = MutableStateFlow(false)
    private val _selectedFilter = savedStateHandle.getStateFlow(HISTORY_FILTER_SAVED_STATE_KEY,HistoryFilterType.DAY)
    val selectedFilter : StateFlow<HistoryFilterType> = _selectedFilter

    private val _filterUiInfo = _selectedFilter.map { getFilterUiInfo(it) }.distinctUntilChanged()
    private val _getAttendance = databaseRepository.getAttendance(currentUser?.uid?:"")

    @RequiresApi(Build.VERSION_CODES.O)
    val uiState : StateFlow<HistoryUiState> = combine(
        _selectedFilter, _filterUiInfo,_isLoading, _getAttendance
    ){ selectedFilter,filterUiInfo, isLoading, response ->
        when(response){
            is ResultState.Failure -> {
                HistoryUiState(isLoading = false)
            }
            ResultState.Loading -> {
                HistoryUiState(isLoading = true)
            }
            is ResultState.Success -> {
                val result = filterTasks(response.data,selectedFilter)
                HistoryUiState(isLoading = isLoading, filterUiState = filterUiInfo, item = result)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HistoryUiState(isLoading = true)
    )

    fun setFiltering(requestType: HistoryFilterType) {
        savedStateHandle[HISTORY_FILTER_SAVED_STATE_KEY] = requestType
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun filterTasks(data: List<AttendanceResponse>, filteringType: HistoryFilterType): List<AttendanceResponse> {
        val attendance = when (filteringType) {
            HistoryFilterType.DAY-> filterByDay(data)
            HistoryFilterType.MONTH -> filterByWeek(data)
            HistoryFilterType.WEEK -> filterByMonth(data)
            HistoryFilterType.YEAR -> filterByYear(data)
        }
        return attendance
    }

    private fun getFilterUiInfo(requestType: HistoryFilterType): FilterUiState =
        when (requestType) {
            HistoryFilterType.DAY -> {
                FilterUiState(
                    R.string.no_history_day
                )
            }
            HistoryFilterType.WEEK -> {
                FilterUiState(
                    R.string.no_history_week
                )
            }
            HistoryFilterType.MONTH -> {
                FilterUiState(
                    R.string.no_history_month
                )
            }
            HistoryFilterType.YEAR -> {
                FilterUiState(
                    R.string.no_history_year
                )
            }
        }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun filterByDay(data: List<AttendanceResponse>): List<AttendanceResponse> {
        val currentDate = LocalDate.now()
        return data.filter {
            val attendanceDate = Instant.ofEpochMilli(it.item?.time?:0)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
            attendanceDate.isEqual(currentDate)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun filterByWeek(data: List<AttendanceResponse>): List<AttendanceResponse> {
        // Get the current date and the start and end of the current week (Monday to Sunday)
        val currentDate = LocalDate.now()
        val startOfWeek = currentDate.with(DayOfWeek.MONDAY) // Start of the current week (Monday)
        val endOfWeek = currentDate.with(DayOfWeek.SUNDAY)  // End of the current week (Sunday)

        return data.filter { attendance ->
            // Convert the attendance timestamp to LocalDate
            val attendanceDate = Instant.ofEpochMilli(attendance.item?.time ?: 0)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()

            // Check if the attendance date is within the current week range
            !attendanceDate.isBefore(startOfWeek) && !attendanceDate.isAfter(endOfWeek)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun filterByMonth(data: List<AttendanceResponse>): List<AttendanceResponse> {
        // Get the current date, the first day of the month, and the last day of the month
        val currentDate = LocalDate.now()
        val startOfMonth = currentDate.withDayOfMonth(1) // Start of the current month
        val endOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth()) // End of the current month

        return data.filter {
            val attendanceDate = Instant.ofEpochMilli(it.item?.time?:0)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
            !attendanceDate.isBefore(startOfMonth) && !attendanceDate.isAfter(endOfMonth)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun filterByYear(data: List<AttendanceResponse>): List<AttendanceResponse> {
        // Get the current date and set the start and end of the current year
        val currentDate = LocalDate.now()
        val startOfYear = currentDate.withDayOfYear(1) // Start of the current year (January 1st)
        val endOfYear = currentDate.withDayOfYear(currentDate.lengthOfYear()) // End of the current year (December 31st)

        return data.filter {
            val attendanceDate = Instant.ofEpochMilli(it.item?.time?:0)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
            !attendanceDate.isBefore(startOfYear) && !attendanceDate.isAfter(endOfYear)
        }
    }
}
