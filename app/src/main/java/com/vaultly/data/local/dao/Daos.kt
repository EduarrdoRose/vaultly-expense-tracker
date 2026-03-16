package com.vaultly.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vaultly.data.local.entity.AccountEntity
import com.vaultly.data.local.entity.BudgetEntity
import com.vaultly.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<TransactionEntity>)

    @Query("SELECT * FROM transactions WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getByDateRange(startDate: String, endDate: String): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE customCategory = :category")
    fun getByCategory(category: String): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE syncStatus != 'SYNCED'")
    suspend fun getPendingSync(): List<TransactionEntity>

    @Query("SELECT customCategory as category, SUM(amount) as total FROM transactions GROUP BY customCategory")
    fun getTotalByCategory(): Flow<List<CategoryTotalProjection>>
}

data class CategoryTotalProjection(val category: String?, val total: Double)

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<AccountEntity>)

    @Query("SELECT * FROM accounts")
    fun getAll(): Flow<List<AccountEntity>>

    @Query("SELECT * FROM accounts WHERE id = :id")
    suspend fun getById(id: String): AccountEntity?
}

@Dao
interface BudgetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: BudgetEntity)

    @Query("SELECT * FROM budgets")
    fun getAll(): Flow<List<BudgetEntity>>

    @Query("SELECT b.category as category, b.amount as budgetAmount, COALESCE(SUM(t.amount), 0) as spentAmount FROM budgets b LEFT JOIN transactions t ON t.customCategory = b.category GROUP BY b.category, b.amount")
    fun getBudgetWithSpending(): Flow<List<BudgetSpendingProjection>>
}

data class BudgetSpendingProjection(val category: String, val budgetAmount: Double, val spentAmount: Double)
