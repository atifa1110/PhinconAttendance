package com.example.phinconattendance.screen.splash

import androidx.lifecycle.ViewModel
import com.example.phinconattendance.data.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: DataStoreRepository
) : ViewModel() {

    // check boarding state
    val onboardingState: Flow<Boolean> = repository.onBoardingState()
    // check login state
    val loginState: Flow<Boolean> = repository.onLoginState()

}