package com.vaultly.domain.usecase

import com.vaultly.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import javax.inject.Inject

data class ForecastResult(
    val currentSpend: Double,
    val projectedTotal: Double,
    val budgetLimit: Double,
    val projectedOverage: Double,
    val daysRemaining: Int,
    val dailyAverage: Double,
    val suggestedDailyLimit: Double,
)

class ForecastMonthlySpendUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    suspend operator fun invoke(category: String, currentDate: LocalDate, budgetLimit: Double): ForecastResult {
        val start = currentDate.withDayOfMonth(1)
        val spend = transactionRepository.getSpendingByCategory(start, currentDate).first()[category] ?: 0.0
        val elapsedDays = currentDate.dayOfMonth.coerceAtLeast(1)
        val dailyAverage = spend / elapsedDays
        val daysRemaining = currentDate.lengthOfMonth() - elapsedDays
        val projectedTotal = spend + (dailyAverage * daysRemaining)
        val projectedOverage = projectedTotal - budgetLimit
        val suggested = if (daysRemaining > 0) (budgetLimit - spend) / daysRemaining else 0.0
        return ForecastResult(spend, projectedTotal, budgetLimit, projectedOverage, daysRemaining, dailyAverage, suggested)
    }
}
