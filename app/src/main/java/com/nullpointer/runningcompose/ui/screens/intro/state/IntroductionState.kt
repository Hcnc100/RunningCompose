package com.nullpointer.runningcompose.ui.screens.intro.state

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.nullpointer.runningcompose.ui.states.SimpleScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
@OptIn(ExperimentalFoundationApi::class)
class IntroductionState(
    context: Context,
    val pagerState: PagerState,
    scaffoldState: ScaffoldState,
    private val coroutineScope: CoroutineScope
) : SimpleScreenState(context, scaffoldState) {

    val currentPager get() = pagerState.currentPage

    val isLastPage get() = pagerState.currentPage == pagerState.pageCount - 1

    val isFirstPage get() = pagerState.currentPage == 0

    val pageCount get() = pagerState.pageCount

    fun nextPage() = coroutineScope.launch {
        pagerState.animateScrollToPage(pagerState.currentPage + 1)
    }

    fun prevPage() = coroutineScope.launch {
        pagerState.animateScrollToPage(pagerState.currentPage - 1)
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun rememberIntroductionScreenState(
    countPager: Int,
    context: Context = LocalContext.current,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    pagerState: PagerState = rememberPagerState { countPager }
) = remember(scaffoldState, context, pagerState) {
    IntroductionState(
        context = context,
        pagerState = pagerState,
        scaffoldState = scaffoldState,
        coroutineScope = coroutineScope
    )
}