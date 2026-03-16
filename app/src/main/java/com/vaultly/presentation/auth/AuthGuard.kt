package com.vaultly.presentation.auth

import androidx.compose.runtime.Composable
import com.vaultly.domain.repository.AuthState

@Composable
fun AuthGuard(state: AuthState, authenticated: @Composable () -> Unit, unauthenticated: @Composable () -> Unit) {
    when (state) {
        is AuthState.Authenticated -> authenticated()
        else -> unauthenticated()
    }
}
