package com.vaultly.domain.repository

import com.vaultly.domain.model.MonthlyTotal
import com.vaultly.domain.model.Transaction
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TransactionRepository {
    fun getTransactions(userId: String): Flow<List<Transaction>>
    suspend fun syncFromRemote(userId: String): Result<Unit>
    suspend fun updateCategory(id: String, category: String): Result<Unit>
    suspend fun addNote(id: String, note: String): Result<Unit>
    fun getSpendingByCategory(startDate: LocalDate, endDate: LocalDate): Flow<Map<String, Double>>
    fun getMonthlyTotals(months: Int): Flow<List<MonthlyTotal>>
}

interface PlaidRepository {
    suspend fun getLinkToken(): Result<String>
    suspend fun exchangeToken(publicToken: String): Result<Unit>
}
