package com.example.phinconattendance.data.preference


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

//to save on boarding state is it open already or not
class DataStoreRepository(context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "on_boarding_pref")
    private val dataStore = context.dataStore

    private object PreferencesKey {
        val onBoardingKey = booleanPreferencesKey(name = "on_boarding_completed")
        val onButtonClicked = booleanPreferencesKey(name = "homeButtonClicked")
    }

    //input data to save shared preference
    suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.onBoardingKey] = completed
        }
    }

    //get data boarding state
    fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val onBoardingState = preferences[PreferencesKey.onBoardingKey] ?: false
                onBoardingState
            }
    }

    //input data to save shared preference
    suspend fun saveOnButtonClickState(click: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.onButtonClicked] = click
        }

    }

    //get data boarding state
    fun readOnButtonClickedState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val onButtonClickState = preferences[PreferencesKey.onButtonClicked] ?: false
                onButtonClickState
            }
    }

    suspend fun clearData(){
        dataStore.edit {
            it.clear()
        }
    }

}