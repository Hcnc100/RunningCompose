package com.nullpointer.runningcompose.presentation

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.nullpointer.runningcompose.core.delegates.SavableComposeState
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.entities.RunEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
):ViewModel() {
    companion object{
        private const val KEY_RUN_LIST_ID = "KEY_RUN_LIST_ID"
    }

    private var listSelect:List<Long> by SavableComposeState(
        savedStateHandle,
        KEY_RUN_LIST_ID,
        emptyList()
    )
    val listRunsSelected = mutableStateMapOf<Long, RunData>()

    val isSelectEnable get() = listSelect.isNotEmpty()

    val sizeSelected get() = listSelect.size

    fun changeSelect(runData: RunData){
        if(listSelect.contains(runData.id)){
            listRunsSelected.remove(runData.id)
        }else{
            listRunsSelected[runData.id]=runData
        }
    }



    fun getListForDeleter(): List<Long> {
        val listTempIds= listOf(*listSelect.toTypedArray())
        clearSelect()
        return listTempIds
    }

    fun clearSelect(){
//        listRunsSelect.forEach{ it.isSelected=false }
//        listSelect= emptyList()
//        listRunsSelect.clear()
    }

}