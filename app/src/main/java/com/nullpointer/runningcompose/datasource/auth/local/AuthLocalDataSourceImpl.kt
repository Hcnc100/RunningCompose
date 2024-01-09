package com.nullpointer.runningcompose.datasource.auth.local

import com.nullpointer.runningcompose.data.auth.local.AuthDataStore
import com.nullpointer.runningcompose.models.data.AuthData
import kotlinx.coroutines.flow.Flow

class AuthLocalDataSourceImpl(
    private val authDataStore: AuthDataStore
) : AuthLocalDataSource {
    override suspend fun saveUserData(authData: AuthData) =
        authDataStore.saveAuthData(authData)

    override suspend fun saveUserData(name: String?, weight: Float?, photo: String?) =
        authDataStore.saveUserData(name, weight, photo)

    override fun getUserData(): Flow<AuthData?> =
        authDataStore.getAuthData()


}