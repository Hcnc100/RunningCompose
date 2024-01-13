package com.nullpointer.runningcompose.ui.screens.intro.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nullpointer.runningcompose.core.utils.launchSafeIO
import com.nullpointer.runningcompose.domain.config.ConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class IntroductionViewModel @Inject constructor(
    private val configRepository: ConfigRepository
) : ViewModel() {

    val isFirsRequestLocation = configRepository.isFirstPermissionLocation.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = true
    )

    val isFirstRequestNotification = configRepository.isFirstPermissionNotify.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = true
    )

    fun initApp() = launchSafeIO {
        configRepository.changeIsFirstOpen()
    }

    fun updateFirstLocationPermission() = launchSafeIO {
        configRepository.changeIsFirstPermissionLocation()
    }

    fun updateFirstNotificationPermission() = launchSafeIO {
        configRepository.changeIsFirstPermissionNotify()
    }
}