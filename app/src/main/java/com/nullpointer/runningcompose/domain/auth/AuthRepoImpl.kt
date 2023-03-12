package com.nullpointer.runningcompose.domain.auth

import com.nullpointer.runningcompose.data.local.auth.AuthLocalDataSource
import com.nullpointer.runningcompose.models.AuthData
import kotlinx.coroutines.flow.Flow

class AuthRepoImpl(
    private val authLocalDataSource: AuthLocalDataSource
) : AuthRepository {

    override val authData: Flow<AuthData> =
        authLocalDataSource.getUserData()

    override suspend fun saveUserData(authData: AuthData) =
        authLocalDataSource.saveUserData(authData)

    override suspend fun saveUserData(name: String?, weight: Float?, photo: String?) =
        authLocalDataSource.saveUserData(name, weight, photo)

}