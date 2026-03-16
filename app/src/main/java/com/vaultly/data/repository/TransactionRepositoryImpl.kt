package com.vaultly.data.repository

import com.vaultly.data.local.dao.TransactionDao
import com.vaultly.domain.model.MonthlyTotal
import com.vaultly.domain.model.Transaction
import com.vaultly.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao,
) : TransactionRepository {
    override fun getTransactions(userId: String): Flow<List<Transaction>> =
        transactionDao.getByDateRange("1900-01-01", "2999-12-31").map { list ->
            list.filter { it.userId == userId }.map {
                Transaction(
                    id = it.id,
                    userId = it.userId,
                    accountId = it.accountId,
                    merchantName = it.merchantName,
                    amount = it.amount,
                    date = LocalDate.parse(it.date),
                    customCategory = it.customCategory,
                    note = it.note,
                    pending = it.pending,
                )
            }
        }

    override suspend fun syncFromRemote(userId: String): Result<Unit> = Result.success(Unit)

    override suspend fun updateCategory(id: String, category: String): Result<Unit> = Result.success(Unit)

    override suspend fun addNote(id: String, note: String): Result<Unit> = Result.success(Unit)

    override fun getSpendingByCategory(startDate: LocalDate, endDate: LocalDate): Flow<Map<String, Double>> =
        transactionDao.getTotalByCategory().map { rows -> rows.associate { (it.category ?: "Other") to it.total } }

    override fun getMonthlyTotals(months: Int): Flow<List<MonthlyTotal>> =
        getSpendingByCategory(LocalDate.now().minusMonths(months.toLong()), LocalDate.now()).map { grouped ->
            listOf(MonthlyTotal(LocalDate.now().month.name, grouped.values.sum()))
        }
}
