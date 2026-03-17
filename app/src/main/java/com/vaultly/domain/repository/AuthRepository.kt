package com.vaultly.domain.repository.auth

import com.vaultly.domain.model.AuthState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun authState(): Flow<AuthState>
    suspend fun signIn(email: String, password: String): Result<Unit>
    suspend fun signUp(email: String, password: String): Result<Unit>
    suspend fun signOut(): Result<Unit>
}
