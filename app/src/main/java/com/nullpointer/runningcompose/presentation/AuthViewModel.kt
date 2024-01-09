package com.nullpointer.runningcompose.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nullpointer.runningcompose.core.states.Resource
import com.nullpointer.runningcompose.core.utils.launchSafeIO
import com.nullpointer.runningcompose.domain.auth.AuthRepository
import com.nullpointer.runningcompose.models.data.AuthData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val authData = authRepository.authData.transform<AuthData?, Resource<AuthData?>> {
        emit(Resource.Success(it))
    }.catch {
        emit(Resource.Failure)
    }.flowOn(Dispatchers.IO)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            Resource.Loading
        )

    var isUpdatedData by mutableStateOf(false)
        private set


    fun saveAuthData(authData: AuthData) = launchSafeIO(
        blockBefore = { isUpdatedData = true },
        blockAfter = { isUpdatedData = false },
        blockException = {
            Timber.e("Error saving auth data: $it")
        },
        blockIO = {
            authRepository.saveUserData(authData)
        }
    )

}