package com.example.phinconattendance.data.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override val currentUser: FirebaseUser? get() = firebaseAuth.currentUser

    override fun resetPassword(email: String): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)

        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
            if(it.isSuccessful){
                trySend(ResultState.Success("Please check your email to rest password"))
            }
        }.addOnFailureListener {
            trySend(ResultState.Failure(it))
        }

        awaitClose{
            close()
        }
    }

    override fun updatePassword(password: String): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)

        firebaseAuth.currentUser?.updatePassword(password)?.addOnCompleteListener {
            if(it.isSuccessful){
                trySend(ResultState.Success("Password has been update"))
            }
        }?.addOnFailureListener {
            trySend(ResultState.Failure(it))
        }

        awaitClose{
            close()
        }
    }


    override fun loginUser(email: String, password: String): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            trySend(ResultState.Success("Login is Success"))
        }.addOnFailureListener {
            trySend(ResultState.Failure(it))
        }

        awaitClose{
            close()
        }
    }

    override fun registerUser(name: String, email: String, password: String): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)

        val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        result.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())!!
            .addOnCompleteListener {
                trySend(ResultState.Success(result.user!!.uid))
                //Log.d("Register User",result.user!!.uid)
            }.addOnFailureListener {
                trySend(ResultState.Failure(it))
            }

        awaitClose{
            close()
        }
    }


    override fun logout() {
        firebaseAuth.signOut()
    }

}