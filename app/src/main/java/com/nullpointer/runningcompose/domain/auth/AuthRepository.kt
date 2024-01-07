package com.nullpointer.runningcompose.domain.auth

import com.nullpointer.runningcompose.models.data.AuthData
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val authData: Flow<AuthData>
    suspend fun saveUserData(authData: AuthData)
    suspend fun saveUserData(name: String? = null, weight: Float? = null, photo: String? = null)

}