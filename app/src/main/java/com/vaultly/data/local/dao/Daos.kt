package com.vaultly.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.vaultly.data.local.entity.AccountEntity
import com.vaultly.data.local.entity.BudgetEntity
import com.vaultly.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Upsert
    suspend fun upsertAll(transactions: List<TransactionEntity>)

    @Query("SELECT * FROM transactions WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getByDateRange(startDate: String, endDate: String): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE userId = :userId ORDER BY date DESC")
    fun getByUserId(userId: String): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE customCategory = :category")
    fun getByCategory(category: String): Flow<List<TransactionEntity>>

    @Query("UPDATE transactions SET customCategory = :category WHERE id = :id")
    suspend fun updateCategory(id: String, category: String)

    @Query("UPDATE transactions SET note = :note WHERE id = :id")
    suspend fun updateNote(id: String, note: String)

    @Query("SELECT * FROM transactions WHERE syncStatus != 'SYNCED'")
    suspend fun getPendingSync(): List<TransactionEntity>

    @Query("SELECT customCategory as category, SUM(amount) as total FROM transactions WHERE userId = :userId AND date BETWEEN :start AND :end GROUP BY customCategory ORDER BY total DESC")
    fun getTotalByCategory(userId: String, start: String, end: String): Flow<List<CategoryTotal>>

    @Query("SELECT strftime('%Y-%m', date) as month, SUM(amount) as total FROM transactions WHERE userId = :userId AND date >= :since GROUP BY month ORDER BY month ASC")
    fun getMonthlyTotals(userId: String, since: String): Flow<List<MonthlyTotalRow>>
}

data class CategoryTotal(val category: String?, val total: Double)

data class MonthlyTotalRow(val month: String, val total: Double)

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<AccountEntity>)

    @Query("SELECT * FROM accounts WHERE userId = :userId ORDER BY name ASC")
    fun getAll(userId: String): Flow<List<AccountEntity>>

    @Query("SELECT * FROM accounts WHERE id = :id")
    suspend fun getById(id: String): AccountEntity?
}

@Dao
interface BudgetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: BudgetEntity)

    @Query("SELECT * FROM budgets WHERE userId = :userId")
    fun getAll(userId: String): Flow<List<BudgetEntity>>

    @Query("SELECT b.category as category, b.amount as budgetAmount, COALESCE(SUM(t.amount), 0) as spentAmount FROM budgets b LEFT JOIN transactions t ON t.customCategory = b.category AND t.userId = :userId WHERE b.userId = :userId GROUP BY b.category, b.amount")
    fun getBudgetWithSpending(userId: String): Flow<List<BudgetSpendingProjection>>
}

data class BudgetSpendingProjection(val category: String, val budgetAmount: Double, val spentAmount: Double)
