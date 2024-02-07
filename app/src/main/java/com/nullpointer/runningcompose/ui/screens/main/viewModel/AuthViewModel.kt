package com.nullpointer.runningcompose.ui.screens.main.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nullpointer.runningcompose.core.states.Resource
import com.nullpointer.runningcompose.domain.auth.AuthRepository
import com.nullpointer.runningcompose.domain.config.ConfigRepository
import com.nullpointer.runningcompose.models.data.InitAppData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    authRepository: AuthRepository,
    configRepository: ConfigRepository
) : ViewModel() {

    val initAppData =
        authRepository.authData.combine(configRepository.settingsData) { authData, settings ->
            Resource.Success( InitAppData(authData, settings.isFirstOpen))
        }.flowOn(Dispatchers.IO)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                Resource.Loading
            )


}