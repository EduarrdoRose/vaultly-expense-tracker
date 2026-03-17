package com.vaultly.data.remote.plaid

import androidx.activity.ComponentActivity
import com.vaultly.domain.repository.PlaidRepository
import javax.inject.Inject

class PlaidLinkManager @Inject constructor(
    private val plaidRepository: PlaidRepository,
) {
    suspend fun getLinkToken(): String = plaidRepository.getLinkToken().getOrThrow()

    fun openLink(
        activity: ComponentActivity,
        linkToken: String,
        onSuccess: (String) -> Unit,
        onExit: () -> Unit,
    ) {
        // TODO integrate Plaid Link SDK launcher.
        onExit()
    }

    suspend fun exchangeToken(publicToken: String): Result<Unit> = plaidRepository.exchangeToken(publicToken)
}
