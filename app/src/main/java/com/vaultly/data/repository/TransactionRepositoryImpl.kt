package com.vaultly.data.repository

import com.vaultly.data.local.dao.TransactionDao
import com.vaultly.data.local.entity.toDomain
import com.vaultly.data.remote.plaid.TransactionDto
import com.vaultly.domain.model.MonthlyTotal
import com.vaultly.domain.model.Transaction
import com.vaultly.domain.repository.TransactionRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.result.decodeList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao,
    private val supabase: SupabaseClient,
) : TransactionRepository {

    override fun getTransactions(userId: String): Flow<List<Transaction>> =
        transactionDao.getByUserId(userId).map { list -> list.map { it.toDomain() } }

    override suspend fun syncFromRemote(userId: String): Result<Unit> = runCatching {
        val remote = supabase.postgrest["transactions"]
            .select {
                filter { eq("user_id", userId) }
            }
            .decodeList<TransactionDto>()
        transactionDao.upsertAll(remote.map { it.toEntity() })
    }

    override suspend fun updateCategory(id: String, category: String): Result<Unit> = runCatching {
        transactionDao.updateCategory(id, category)
        supabase.postgrest["transactions"].update(
            {
                set("custom_category", category)
            }
        ) {
            filter { eq("id", id) }
        }
    }

    override suspend fun addNote(id: String, note: String): Result<Unit> = runCatching {
        transactionDao.updateNote(id, note)
        supabase.postgrest["transactions"].update(
            {
                set("note", note)
            }
        ) {
            filter { eq("id", id) }
        }
    }

    override fun getSpendingByCategory(startDate: LocalDate, endDate: LocalDate): Flow<Map<String, Double>> =
        transactionDao.getTotalByCategory(startDate.toString(), endDate.toString())
            .map { rows -> rows.associate { (it.category ?: "Other") to it.total } }

    override fun getMonthlyTotals(months: Int): Flow<List<MonthlyTotal>> =
        transactionDao.getMonthlyTotals(
            LocalDate.now().minusMonths(months.toLong()).toString()
        ).map { rows -> rows.map { MonthlyTotal(it.month, it.total) } }
}
