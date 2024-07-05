package com.pimsupa.coin.util

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<UiState>(
    initialState: UiState,
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    protected fun setState(builder: UiState.() -> UiState) {
        _uiState.update(builder)
    }
}
