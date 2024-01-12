package com.nullpointer.runningcompose.ui.screens.intro.viewModel

import androidx.lifecycle.ViewModel
import com.nullpointer.runningcompose.core.utils.launchSafeIO
import com.nullpointer.runningcompose.domain.config.ConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IntroductionViewModel @Inject constructor(
    private val configRepository: ConfigRepository
) : ViewModel() {

    fun initApp() = launchSafeIO {
        configRepository.changeIsFirstOpen()
    }
}