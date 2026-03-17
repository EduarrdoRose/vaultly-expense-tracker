package com.vaultly.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.vaultly.domain.model.Transaction
import java.time.LocalDate

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val accountId: String,
    val plaidCategory: List<String> = emptyList(),
    val customCategory: String? = null,
    val merchantName: String? = null,
    val amount: Double,
    val currencyCode: String = "USD",
    val date: String,
    val authorizedDate: String? = null,
    val paymentChannel: String? = null,
    val logoUrl: String? = null,
    val website: String? = null,
    val note: String? = null,
    val pending: Boolean = false,
    val createdAt: String? = null,
    val syncStatus: SyncStatus = SyncStatus.SYNCED,
)

enum class SyncStatus { SYNCED, PENDING, FAILED }

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val name: String,
    val officialName: String? = null,
    val type: String? = null,
    val subtype: String? = null,
    val currentBalance: Double,
    val availableBalance: Double? = null,
    val currencyCode: String = "USD",
    val mask: String? = null,
    val updatedAt: String? = null,
)

@Entity(tableName = "budgets")
data class BudgetEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val category: String,
    val amount: Double,
    val period: String = "monthly",
    val createdAt: String? = null,
)

@Entity(tableName = "plaid_items")
data class PlaidItemEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val institutionId: String,
    val institutionName: String,
    val status: String,
    val createdAt: String? = null,
)

fun TransactionEntity.toDomain() = Transaction(
    id = id, userId = userId, accountId = accountId,
    merchantName = merchantName, amount = amount,
    date = LocalDate.parse(date), customCategory = customCategory,
    note = note, pending = pending
)


class Converters {
    @TypeConverter
    fun fromStringList(list: List<String>): String = list.joinToString(",")

    @TypeConverter
    fun toStringList(value: String): List<String> = if (value.isBlank()) emptyList() else value.split(",")

    @TypeConverter
    fun fromSync(value: SyncStatus): String = value.name

    @TypeConverter
    fun toSync(value: String): SyncStatus = SyncStatus.valueOf(value)
}
