package com.nullpointer.runningcompose.presentation

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.nullpointer.runningcompose.models.data.RunData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
):ViewModel() {
    companion object {
        private const val KEY_RUN_LIST_ID = "KEY_RUN_LIST_ID"
    }


    private val _listRunsSelected = mutableStateMapOf<Long, RunData>()
    val listRunsSelected get() = _listRunsSelected.toMap()


    fun changeSelect(runData: RunData) {
        if (_listRunsSelected.contains(runData.id)) {
            _listRunsSelected.remove(runData.id)
        } else {
            _listRunsSelected[runData.id] = runData
        }
    }



    fun getListForDeleter(): List<Long> {
        val listTempIds = _listRunsSelected.map { it.key }
        clearSelect()
        return listTempIds
    }

    fun clearSelect(){
        _listRunsSelected.clear()
    }

}