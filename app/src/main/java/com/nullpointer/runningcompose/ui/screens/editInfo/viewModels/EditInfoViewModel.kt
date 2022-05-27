package com.nullpointer.runningcompose.ui.screens.editInfo.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.delegates.SavableComposeState
import com.nullpointer.runningcompose.domain.config.ConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditInfoViewModel @Inject constructor(
    private val configRepository: ConfigRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    companion object {
        private const val KEY_NAME_USER = "KEY_NAME_USER"
        private const val KEY_WEIGHT_USER = "KEY_WEIGHT_USER"
        private const val MAX_LENGTH_NAME = 120
        private const val MIN_WEIGHT = 10F
        private const val MAX_WEIGHT = 300F
    }

    var nameUser by SavableComposeState(savedStateHandle, KEY_NAME_USER, "")
        private set
    var weightUser by SavableComposeState(savedStateHandle, KEY_WEIGHT_USER, "")
        private set
    var errorNamed by SavableComposeState(savedStateHandle, KEY_WEIGHT_USER, -1)
        private set
    var errorWeight by SavableComposeState(savedStateHandle, KEY_WEIGHT_USER, -1)
        private set

    private val _messageEditInfo = Channel<Int>()
    val messageEditInfo = _messageEditInfo.receiveAsFlow()

    val nameLength get() = "${nameUser.length}/$MAX_LENGTH_NAME"

    var isDataComplete by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            val userData = withContext(Dispatchers.IO) { configRepository.userConfig.first() }
            isDataComplete = userData != null
            nameUser = userData?.name?:""
            weightUser = userData?.weight?.toString() ?: ""
        }
    }

    fun changeNameUser(newName: String) {
        nameUser = newName
        errorNamed = when {
            nameUser.isEmpty() -> R.string.error_empty_name
            nameUser.length > MAX_LENGTH_NAME -> R.string.error_max_lenght_name
            else -> -1
        }
    }

    fun changeWeight(newWeight: String) {
        weightUser = newWeight
        val weightValue=weightUser.toFloatOrNull() ?: -1F
        errorWeight = when  {
            (weightValue !in MIN_WEIGHT..MAX_WEIGHT) -> R.string.error_range_weight
            else -> -1
        }
    }

    fun validateDataUser(): Boolean {
        return if (errorWeight == -1 && errorNamed == -1) {
            true
        } else {
            _messageEditInfo.trySend(R.string.error_validate_data)
            false
        }
    }
}