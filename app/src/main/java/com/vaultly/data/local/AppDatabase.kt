package com.vaultly.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.vaultly.data.local.dao.AccountDao
import com.vaultly.data.local.dao.BudgetDao
import com.vaultly.data.local.dao.TransactionDao
import com.vaultly.data.local.entity.AccountEntity
import com.vaultly.data.local.entity.BudgetEntity
import com.vaultly.data.local.entity.SyncStatus
import com.vaultly.data.local.entity.TransactionEntity

@Database(
    entities = [TransactionEntity::class, AccountEntity::class, BudgetEntity::class],
    version = 1,
    exportSchema = true,
)
@TypeConverters(AppConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun accountDao(): AccountDao
    abstract fun budgetDao(): BudgetDao
}

class AppConverters {
    @TypeConverter fun fromSync(value: SyncStatus): String = value.name
    @TypeConverter fun toSync(value: String): SyncStatus = SyncStatus.valueOf(value)
    @TypeConverter fun fromList(value: List<String>): String = value.joinToString("|")
    @TypeConverter fun toList(value: String): List<String> = if (value.isBlank()) emptyList() else value.split("|")
}
