package com.example.phinconattendance.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.phinconattendance.data.model.Place
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException

//to save on boarding state is it open already or not
private val Context.dataStore by preferencesDataStore("user_prefs")
class DataStoreRepository(context: Context) {

    private val dataStore = context.dataStore

    companion object {
        private val ONBOARDING_STATE_KEY = booleanPreferencesKey("onboarding_state")
        private val LOGIN_STATE_KEY = booleanPreferencesKey("login_state")
        private val CHECK_IN_STATE_KEY = booleanPreferencesKey("check_in_state")
        private val BUTTON_ENABLE_STATE_KEY = booleanPreferencesKey("button_enable_state")
        private val SELECTED_LOCATION_NAME = stringPreferencesKey("selected_location_name")
    }

    // save boarding state
    suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[ONBOARDING_STATE_KEY] = completed
        }
    }

    // get boarding state
    fun onBoardingState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val onBoardingState = preferences[ONBOARDING_STATE_KEY] ?: false
                onBoardingState
            }
    }

    // save login state
    suspend fun saveOnLoginState(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[LOGIN_STATE_KEY] = completed
        }

    }

    // get login state
    fun onLoginState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val onLoginState = preferences[LOGIN_STATE_KEY] ?: false
                onLoginState
            }
    }

    // save check in state
    suspend fun saveOnCheckInState(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[CHECK_IN_STATE_KEY] = completed
        }
    }

    // get check in state
    fun onCheckInState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val onCheckInState = preferences[CHECK_IN_STATE_KEY] ?: false
                onCheckInState
            }
    }

    // save button enable state
    suspend fun saveOnButtonEnableState(enable: Boolean) {
        dataStore.edit { preferences ->
            preferences[BUTTON_ENABLE_STATE_KEY] = enable
        }
    }

    // get button enable state
    fun onButtonEnableState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val onButtonEnableState = preferences[BUTTON_ENABLE_STATE_KEY] ?: false
                onButtonEnableState
            }
    }

    // save selected location state
    suspend fun saveOnSelectedLocationState(place : Place) {
        val placeJson = Json.encodeToString(place)
        dataStore.edit{ preferences ->
            preferences[SELECTED_LOCATION_NAME] = placeJson
        }
    }

    // save check in state
    fun onSelectedLocationState(): Flow<Place> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                // Retrieve the JSON string from DataStore using the key SELECTED_LOCATION_NAME
                val placeJson = preferences[SELECTED_LOCATION_NAME] ?: ""

                // Check if the JSON string is empty or invalid and handle gracefully
                if (placeJson.isNotBlank()) {
                    try {
                        // Attempt to decode the JSON string to a Place object
                        Json.decodeFromString(placeJson)
                    } catch (e: Exception) {
                        // Handle any decoding exceptions and log or provide a default object
                        println("Error decoding JSON: ${e.message}")
                        Place("", "") // Provide a default Place object or handle as needed
                    }
                } else {
                    // Handle case where JSON string is blank or missing
                    println("Error: JSON input is empty or missing")
                    Place("", "") // Provide a default or empty Place object
                }
            }
    }


    suspend fun clearData(){
        dataStore.edit {
            it.clear()
        }
    }

}