package com.example.phinconattendance.screen.main.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel () : ViewModel() {
        // The current time as a MutableLiveData
        private val nowTimeLiveData: MutableLiveData<Long> = MutableLiveData(1)
        val currentDateLiveData: MutableLiveData<String> = MutableLiveData("")
        val currentTimeLiveData: MutableLiveData<String> = MutableLiveData("")

        init {
            // Update the current time when the ViewModel is initialized
            updateTime()
        }


        // Function to update the current time
        fun updateTime() {
            // Get the current time in the desired format

            var now = System.currentTimeMillis()
            val currentTimeFormatted = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.getDefault()).format(now)
            val mString = currentTimeFormatted!!.split(" ").toTypedArray()

            val date = mString[0]+" "+mString[1]+" "+mString[2]
            val time = mString[3]

            // Update the MutableLiveData with the current time
            nowTimeLiveData.value = now
            currentDateLiveData.value = date
            currentTimeLiveData.value = time
        }

}