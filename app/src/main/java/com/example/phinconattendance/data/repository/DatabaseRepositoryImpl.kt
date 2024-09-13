package com.example.phinconattendance.data.repository

import android.util.Log
import com.example.phinconattendance.data.model.ResultState
import com.example.phinconattendance.data.model.Attendance
import com.example.phinconattendance.data.model.AttendanceResponse
import com.example.phinconattendance.data.model.User
import com.example.phinconattendance.data.model.UserResponse
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val database: DatabaseReference
): DatabaseRepository {

    override fun insertUser(key : String, item: User): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)

        database.child("User").child(key).setValue(item).addOnCompleteListener {
            if(it.isSuccessful)
                trySend(ResultState.Success("User insert Successfully.."))
        }.addOnFailureListener {
            trySend(ResultState.Failure(it))
        }

        awaitClose {
            close()
        }
    }

    override fun getUser(userId: String): Flow<ResultState<UserResponse>> = callbackFlow{
        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val items = UserResponse(snapshot.getValue(User::class.java),
                        key  = snapshot.key)
                trySend(ResultState.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Failure(error.toException()))
            }

        }

        database.child("User").child(userId).addValueEventListener(valueEvent)
        awaitClose {
            database.child("User").child(userId).removeEventListener(valueEvent)
            close()
        }
    }


    override fun insertAttendance(userId : String,key: String, item: Attendance): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)

        database.child("Attendance").child(userId).child(key).setValue(item).addOnCompleteListener {
            if(it.isSuccessful) trySend(ResultState.Success("Attendance insert Successfully.."))
        }.addOnFailureListener {
            trySend(ResultState.Failure(it))
        }

        awaitClose {
            close()
        }
    }

    override fun getAttendance(userId:String): Flow<ResultState<List<AttendanceResponse>>> = callbackFlow {
        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.map {
                    val s = it.child("time").getValue(Long::class.java)
                    Log.d("DatabaseAttendance",s.toString())
                    AttendanceResponse(
                        it.getValue(Attendance::class.java),
                        key = it.key
                    )
                }

                trySend(ResultState.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Failure(error.toException()))
            }

        }

        database.child("Attendance").child(userId).addValueEventListener(valueEvent)
        awaitClose {
            database.child("Attendance").child(userId).removeEventListener(valueEvent)
            close()
        }
    }


}
