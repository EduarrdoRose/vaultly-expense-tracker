package com.vaultly.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaultly.domain.repository.AuthRepository
import com.vaultly.domain.repository.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<AuthState>(AuthState.Loading)
    val uiState: StateFlow<AuthState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch { authRepository.authState().collect { _uiState.value = it } }
    }
}
