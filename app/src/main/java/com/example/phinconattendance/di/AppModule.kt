package com.example.phinconattendance.di

import android.content.Context
import com.example.phinconattendance.data.preference.DataStoreRepository
import com.example.phinconattendance.data.auth.AuthRepository
import com.example.phinconattendance.data.auth.AuthRepositoryImpl
import com.example.phinconattendance.data.database.DatabaseRepository
import com.example.phinconattendance.data.database.DatabaseRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataStoreRepository(
        @ApplicationContext context: Context
    ) = DataStoreRepository(context = context)

//    @Provides
//    @Singleton
//    fun provideFirebaseAuth() = FirebaseAuth.getInstance()
//
//    @Provides
//    @Singleton
//    fun provideAuthImpl(firebaseAuth: FirebaseAuth) : AuthRepository{
//        return AuthRepositoryImpl(firebaseAuth)
//    }

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun providesAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    @Singleton
    fun providesRealtimeDatabase(): DatabaseReference =
        Firebase.database.reference


    @Provides
    fun provideUserRepository(impl: DatabaseRepositoryImpl): DatabaseRepository = impl
}