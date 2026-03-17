package com.vaultly.data.remote.plaid

import com.vaultly.data.local.entity.SyncStatus
import com.vaultly.data.local.entity.TransactionEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionDto(
    @SerialName("id") val id: String,
    @SerialName("user_id") val userId: String,
    @SerialName("account_id") val accountId: String,
    @SerialName("merchant_name") val merchantName: String? = null,
    @SerialName("amount") val amount: Double,
    @SerialName("date") val date: String,
    @SerialName("custom_category") val customCategory: String? = null,
    @SerialName("note") val note: String? = null,
    @SerialName("pending") val pending: Boolean = false,
) {
    fun toEntity() = TransactionEntity(
        id = id,
        userId = userId,
        accountId = accountId,
        plaidCategory = emptyList(),
        customCategory = customCategory,
        merchantName = merchantName,
        amount = amount,
        currencyCode = "USD",
        date = date,
        note = note,
        pending = pending,
        syncStatus = SyncStatus.SYNCED,
    )
}
