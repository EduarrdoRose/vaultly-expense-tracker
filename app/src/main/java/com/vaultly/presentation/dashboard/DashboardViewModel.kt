package com.vaultly.presentation.dashboard

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class DashboardUiState { data object Loading : DashboardUiState(); data object Ready : DashboardUiState() }
class DashboardViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()
    fun onEvent(event: DashboardEvent) { _uiState.value = DashboardUiState.Ready }
}
sealed interface DashboardEvent { data object Refresh : DashboardEvent }
