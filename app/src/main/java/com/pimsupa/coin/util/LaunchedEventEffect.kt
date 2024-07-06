package com.pimsupa.coin.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalInspectionMode

@Composable
fun <T> LaunchedEventEffect(event: StateEvent<T>, onConsumed: (T) -> Unit, action: (T) -> Unit) {
    fun <T> consumeStateEvent(
        event: StateEvent<T>,
        action: (T) -> Unit,
        onConsumed: (T) -> Unit,
    ) {
        if (event is StateEventTriggered<T>) {
            action(event.content)
            onConsumed(event.content)
        }
    }

    val isPreview = LocalInspectionMode.current
    if (isPreview) {
        consumeStateEvent(event, action, onConsumed)
    } else {
        LaunchedEffect(key1 = event, key2 = onConsumed) {
            consumeStateEvent(event, action, onConsumed)
        }
    }
}
