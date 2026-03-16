package com.vaultly.domain.model

import androidx.compose.ui.graphics.Color
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

enum class AppCategory(val displayName: String, val emoji: String, val color: Color) {
    FOOD("Food & Dining", "🍔", Color(0xFFEF4444)),
    TRANSPORT("Transport", "🚗", Color(0xFF3B82F6)),
    SHOPPING("Shopping", "🛍️", Color(0xFF8B5CF6)),
    ENTERTAINMENT("Entertainment", "🎮", Color(0xFFF59E0B)),
    UTILITIES("Utilities", "⚡", Color(0xFF10B981)),
    HEALTHCARE("Healthcare", "🏥", Color(0xFF06B6D4)),
    INCOME("Income", "💰", Color(0xFF22C55E)),
    OTHER("Other", "📦", Color(0xFF6B7280))
}
