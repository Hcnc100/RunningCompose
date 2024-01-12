package com.nullpointer.runningcompose.ui.screens.intro


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nullpointer.runningcompose.ui.actions.IntroActions.NEXT
import com.nullpointer.runningcompose.ui.actions.IntroActions.PREV
import com.nullpointer.runningcompose.ui.actions.IntroActions.START
import com.nullpointer.runningcompose.ui.navigation.MainNavGraph
import com.nullpointer.runningcompose.ui.preview.config.SimplePreview
import com.nullpointer.runningcompose.ui.screens.intro.componets.ButtonsPrevAndNext
import com.nullpointer.runningcompose.ui.screens.intro.componets.IntroductionPager
import com.nullpointer.runningcompose.ui.screens.intro.componets.PageIndicator
import com.nullpointer.runningcompose.ui.screens.intro.state.IntroductionState
import com.nullpointer.runningcompose.ui.screens.intro.state.rememberIntroductionScreenState
import com.nullpointer.runningcompose.ui.screens.intro.viewModel.IntroductionViewModel
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalFoundationApi::class)
@MainNavGraph
@Destination
@Composable
fun IntroductionScreen(
    introductionState: IntroductionState = rememberIntroductionScreenState(countPager = 3),
    introductionViewModel: IntroductionViewModel = hiltViewModel()
) {
    Scaffold(
        scaffoldState = introductionState.scaffoldState
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .background(MaterialTheme.colors.primary)
        ) {
            IntroductionPager(
                pagerState = introductionState.pagerState,
                modifier = Modifier.weight(1F)
            )
            Column(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PageIndicator(
                    colorCurrent = Color.Cyan,
                    colorOther = Color.White,
                    numberPages = introductionState.pageCount,
                    currentPage = introductionState.currentPager
                )
                ButtonsPrevAndNext(
                    isFirstPage = introductionState.isFirstPage,
                    isLastPage = introductionState.isLastPage,
                    actionIntro = { action ->
                        when (action) {
                            PREV -> introductionState.prevPage()
                            NEXT -> introductionState.nextPage()
                            START -> introductionViewModel.initApp()
                        }
                    }
                )
            }

        }

    }
}


@SimplePreview
@Composable
fun IntroductionScreenPreview() {
    IntroductionScreen()
}