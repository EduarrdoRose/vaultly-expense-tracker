package com.vaultly.domain.model

import java.time.LocalDate

data class Transaction(
    val id: String,
    val userId: String,
    val accountId: String,
    val merchantName: String?,
    val amount: Double,
    val date: LocalDate,
    val customCategory: String?,
    val note: String?,
    val pending: Boolean,
)

data class Account(
    val id: String,
    val userId: String,
    val name: String,
    val mask: String?,
    val currentBalance: Double,
)

data class Budget(
    val id: String,
    val userId: String,
    val category: String,
    val amount: Double,
)

data class MonthlyTotal(val month: String, val total: Double)

enum class AppCategory(val displayName: String, val emoji: String, val colorHex: Long) {
    FOOD("Food & Dining", "🍔", 0xFFEF4444L),
    TRANSPORT("Transport", "🚗", 0xFF3B82F6L),
    SHOPPING("Shopping", "🛍️", 0xFF8B5CF6L),
    ENTERTAINMENT("Entertainment", "🎮", 0xFFF59E0BL),
    UTILITIES("Utilities", "⚡", 0xFF10B981L),
    HEALTHCARE("Healthcare", "🏥", 0xFF06B6D4L),
    INCOME("Income", "💰", 0xFF22C55EL),
    OTHER("Other", "📦", 0xFF6B7280L)
}
