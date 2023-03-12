package com.nullpointer.runningcompose.data.local.auth

import com.nullpointer.runningcompose.data.local.dataStore.AuthDataStore
import com.nullpointer.runningcompose.models.AuthData
import kotlinx.coroutines.flow.Flow

class AuthLocalDataSourceImpl(
    private val authDataStore: AuthDataStore
) : AuthLocalDataSource {
    override suspend fun saveUserData(authData: AuthData) =
        authDataStore.saveAuthData(authData)

    override suspend fun saveUserData(name: String?, weight: Float?, photo: String?) =
        authDataStore.saveUserData(name, weight, photo)

    override fun getUserData(): Flow<AuthData> =
        authDataStore.getAuthData()


}