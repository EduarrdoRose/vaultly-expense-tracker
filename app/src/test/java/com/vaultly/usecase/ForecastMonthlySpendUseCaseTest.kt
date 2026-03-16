package com.vaultly.usecase

import com.vaultly.domain.model.MonthlyTotal
import com.vaultly.domain.model.Transaction
import com.vaultly.domain.repository.TransactionRepository
import com.vaultly.domain.usecase.ForecastMonthlySpendUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ForecastMonthlySpendUseCaseTest {
    @Test
    fun `projects monthly spend`() = runTest {
        val repo = object : TransactionRepository {
            override fun getTransactions(userId: String): Flow<List<Transaction>> = flowOf(emptyList())
            override suspend fun syncFromRemote(userId: String): Result<Unit> = Result.success(Unit)
            override suspend fun updateCategory(id: String, category: String): Result<Unit> = Result.success(Unit)
            override suspend fun addNote(id: String, note: String): Result<Unit> = Result.success(Unit)
            override fun getSpendingByCategory(startDate: LocalDate, endDate: LocalDate): Flow<Map<String, Double>> = flowOf(mapOf("Food" to 100.0))
            override fun getMonthlyTotals(months: Int): Flow<List<MonthlyTotal>> = flowOf(emptyList())
        }
        val result = ForecastMonthlySpendUseCase(repo)("Food", LocalDate.of(2026, 1, 10), 400.0)
        assertTrue(result.projectedTotal > result.currentSpend)
    }
}
