package com.vaultly.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vaultly.data.local.dao.AccountDao
import com.vaultly.data.local.dao.BudgetDao
import com.vaultly.data.local.dao.TransactionDao
import com.vaultly.data.local.entity.AccountEntity
import com.vaultly.data.local.entity.Converters
import com.vaultly.data.local.entity.BudgetEntity
import com.vaultly.data.local.entity.TransactionEntity
import com.vaultly.data.local.entity.PlaidItemEntity

@Database(
    entities = [TransactionEntity::class, AccountEntity::class, BudgetEntity::class, PlaidItemEntity::class],
    version = 1,
    exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun accountDao(): AccountDao
    abstract fun budgetDao(): BudgetDao
}
