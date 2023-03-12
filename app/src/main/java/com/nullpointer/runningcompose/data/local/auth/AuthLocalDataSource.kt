package com.nullpointer.runningcompose.data.local.auth

import com.nullpointer.runningcompose.models.AuthData
import kotlinx.coroutines.flow.Flow

interface AuthLocalDataSource {

    fun getUserData(): Flow<AuthData>
    suspend fun saveUserData(authData: AuthData)
    suspend fun saveUserData(name: String? = null, weight: Float? = null, photo: String? = null)

}