package com.nullpointer.runningcompose.ui.screens.editInfo.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.delegates.PropertySavableString
import com.nullpointer.runningcompose.core.utils.launchSafeIO
import com.nullpointer.runningcompose.domain.auth.AuthRepository
import com.nullpointer.runningcompose.models.data.AuthData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditInfoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authRepository: AuthRepository,
) : ViewModel() {

    companion object {
        private const val MAX_LENGTH_NAME = 120
        private const val MAX_LENGTH_WEIGHT = 5
        private const val MIN_WEIGHT = 10F
        private const val MAX_WEIGHT = 300F
        private const val TAG_NAME_USER = "TAG_NAME_USER"
        private const val TAG_WEIGHT_USER = "TAG_WEIGHT_USER"
    }

    val nameUser = PropertySavableString(
        savedState = savedStateHandle,
        label = R.string.label_name_user,
        hint = R.string.hint_name_user,
        maxLength = MAX_LENGTH_NAME,
        emptyError = R.string.error_empty_name,
        lengthError = R.string.error_max_length_name,
        tagSavable = TAG_NAME_USER
    )

    val weightUser = PropertySavableString(
        savedState = savedStateHandle,
        label = R.string.label_weight_user,
        hint = R.string.hint_weight_user,
        maxLength = MAX_LENGTH_WEIGHT,
        emptyError = R.string.error_empty_weight,
        lengthError = R.string.error_max_length_weight,
        tagSavable = TAG_WEIGHT_USER
    )

    private val _messageEditInfo = Channel<Int>()
    val messageEditInfo = _messageEditInfo.receiveAsFlow()


    private val isDataValidate
        get() = !nameUser.hasError && !weightUser.hasError

    fun restoreSaveData() = launchSafeIO {
        val userData = withContext(Dispatchers.IO) { authRepository.authData.first() }
        nameUser.changeValue(userData.name, isInit = true)
        weightUser.changeValue(userData.weight.toString(), isInit = true)
    }

    private fun reValueWeightUser() {
        val userWeight = weightUser.currentValue.toFloatOrNull() ?: 0F
        if (!weightUser.hasError && userWeight !in MIN_WEIGHT..MAX_WEIGHT) {
            weightUser.setAnotherError(R.string.error_range_weight)
        }
    }

    fun validateDataUser(): AuthData? {
        nameUser.reValueField()
        weightUser.reValueField()
        reValueWeightUser()
        return if (isDataValidate) {
            AuthData(
                name = nameUser.currentValue,
                weight = weightUser.currentValue.toFloat()
            )
        } else {
            _messageEditInfo.trySend(R.string.error_validate_data)
            null
        }
    }

    fun addMessage(message: Int) {
        _messageEditInfo.trySend(message)
    }
}