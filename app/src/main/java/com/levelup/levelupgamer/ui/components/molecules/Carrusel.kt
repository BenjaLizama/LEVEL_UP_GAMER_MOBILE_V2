package com.levelup.levelupgamer.ui.components.molecules

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun <T> Carrusel(
    items: List<T>,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    pageContent: @Composable (T) -> Unit
) {
    HorizontalPager(state = pagerState, modifier = modifier) { pageIndex ->
        val item = items[pageIndex]
        pageContent(item)
    }
}