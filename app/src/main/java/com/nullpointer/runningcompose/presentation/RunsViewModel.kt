package com.nullpointer.runningcompose.presentation

import androidx.lifecycle.ViewModel
import com.nullpointer.runningcompose.domain.runs.RunRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RunsViewModel @Inject constructor(
    runsRepository: RunRepoImpl
):ViewModel() {

    val listRuns=
}