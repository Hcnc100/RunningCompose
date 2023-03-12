package com.nullpointer.runningcompose.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nullpointer.runningcompose.core.states.Resource
import com.nullpointer.runningcompose.domain.auth.AuthRepository
import com.nullpointer.runningcompose.models.AuthData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val authData = authRepository.authData.transform<AuthData, Resource<AuthData>> {
        emit(Resource.Success(it))
    }.catch {
        emit(Resource.Failure)
    }.flowOn(Dispatchers.IO)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            Resource.Loading
        )


}