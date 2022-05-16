package com.nullpointer.runningcompose.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.nullpointer.runningcompose.core.delegates.SavableComposeState
import com.nullpointer.runningcompose.models.Run
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
):ViewModel() {
    companion object{
        const val KEY_RUN_LIST_ID="KEY_RUN_LIST_ID"
    }

    private var listSelect:List<Long> by SavableComposeState(
        savedStateHandle,
        KEY_RUN_LIST_ID,
        emptyList()
    )
    private var listRunsSelect = mutableListOf<Run>()

    fun changeSelect(runSelect:Run){
        listSelect=if(listSelect.contains(runSelect.id!!)){
            runSelect.isSelected=false
            listRunsSelect.remove(runSelect)
            listSelect-runSelect.id
        }else{
            runSelect.isSelected=true
            listRunsSelect.add(runSelect)
            listSelect+runSelect.id
        }
    }

    fun restoreSelect(listRuns:List<Run>){
        listRuns.filter { listSelect.contains(it.id!!) }.onEach {
            it.isSelected=true
        }.let {
            listRunsSelect.addAll(listRuns)
        }
    }

    fun getListForDeleter(): List<Long> {
        val listTempIds= listOf(*listSelect.toTypedArray())
        clearSelect()
        return listTempIds
    }

    fun clearSelect(){
        listSelect= emptyList()
        listRunsSelect.clear()
    }

}