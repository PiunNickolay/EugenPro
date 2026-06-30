package com.example.eugenpro.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

@Composable
fun WheelPicker(
    items: List<Int>,
    selectedItem: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    visibleItemsCount: Int = 5,
    itemHeight: Dp = 52.dp
) {

    require(visibleItemsCount % 2 == 1)

    val middle = visibleItemsCount / 2

    val startIndex = remember(selectedItem) {
        items.indexOf(selectedItem).coerceAtLeast(0)
    }

    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = startIndex
    )

    val flingBehavior = rememberSnapFlingBehavior(
        lazyListState = listState
    )

    val currentIndex by remember {
        derivedStateOf {
            val index =
                listState.firstVisibleItemIndex

            index.coerceIn(
                0,
                items.lastIndex
            )
        }
    }

    LaunchedEffect(Unit) {

        snapshotFlow {
            listState.isScrollInProgress
        }
            .filter { scrolling -> !scrolling }
            .map {
                listState.firstVisibleItemIndex
                    .coerceIn(
                        0,
                        items.lastIndex
                    )
            }
            .distinctUntilChanged()
            .collect { index ->

                val value = items[index]

                if (value != selectedItem) {
                    onItemSelected(value)
                }
            }
    }

    Box(
        modifier = modifier
            .width(110.dp)
            .height(itemHeight * visibleItemsCount)
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
            flingBehavior = flingBehavior,
            contentPadding = PaddingValues(
                vertical = itemHeight * middle
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            itemsIndexed(items) { index, value ->

                val distance =
                    kotlin.math.abs(
                        currentIndex - index
                    )
                val targetScale = when (distance) {
                    0 -> 1.25f
                    1 -> 1.1f
                    else -> 1f
                }

                val targetAlpha = when (distance) {
                    0 -> 1f
                    1 -> 0.6f
                    2 -> 0.35f
                    else -> 0.15f
                }

                val scale by animateFloatAsState(
                    targetValue = targetScale,
                    animationSpec = tween(150),
                    label = "scale"
                )

                val alpha by animateFloatAsState(
                    targetValue = targetAlpha,
                    animationSpec = tween(150),
                    label = "alpha"
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(itemHeight),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = value.toString().padStart(2, '0'),
                        fontSize = 28.sp,
                        fontWeight = if (distance == 0)
                            FontWeight.Bold
                        else
                            FontWeight.Normal,
                        color = if (distance == 0)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                            }
                            .alpha(alpha)
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(itemHeight)
        ) {

            HorizontalDivider(
                modifier = Modifier.align(Alignment.TopCenter),
                color = MaterialTheme.colorScheme.primary
            )

            HorizontalDivider(
                modifier = Modifier.align(Alignment.BottomCenter),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}