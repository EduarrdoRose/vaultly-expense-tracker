package com.vaultly.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vaultly.domain.model.Transaction
import java.time.LocalDate

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val accountId: String,
    val plaidCategory: List<String>,
    val customCategory: String?,
    val merchantName: String?,
    val amount: Double,
    val currencyCode: String,
    val date: String,
    val note: String?,
    val pending: Boolean,
    val syncStatus: SyncStatus,
)

enum class SyncStatus { SYNCED, PENDING, FAILED }

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val name: String,
    val officialName: String?,
    val type: String?,
    val subtype: String?,
    val currentBalance: Double,
    val availableBalance: Double?,
    val mask: String?,
)

@Entity(tableName = "budgets")
data class BudgetEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val category: String,
    val amount: Double,
    val period: String,
)

@Entity(tableName = "plaid_items")
data class PlaidItemEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val institutionId: String,
    val institutionName: String,
    val status: String,
)


fun TransactionEntity.toDomain() = Transaction(
    id = id, userId = userId, accountId = accountId,
    merchantName = merchantName, amount = amount,
    date = LocalDate.parse(date), customCategory = customCategory,
    note = note, pending = pending
)
