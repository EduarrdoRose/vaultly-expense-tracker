package com.vaultly.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaultly.domain.model.AuthState
import com.vaultly.domain.repository.auth.AuthRepository
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

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthState.Loading
            authRepository.signIn(email, password).onFailure {
                _uiState.value = AuthState.Error(it.message ?: "Login failed")
            }
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthState.Loading
            authRepository.signUp(email, password).onFailure {
                _uiState.value = AuthState.Error(it.message ?: "Signup failed")
            }
        }
    }

    fun signOut() {
        viewModelScope.launch { authRepository.signOut() }
    }

}
