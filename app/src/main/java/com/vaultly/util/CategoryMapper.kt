package com.vaultly.util

import com.vaultly.domain.model.AppCategory

object CategoryMapper {
    fun fromPlaid(path: List<String>, merchantName: String? = null): AppCategory {
        val root = path.firstOrNull()?.lowercase().orEmpty()
        return when {
            root.contains("food") || merchantName?.contains("cafe", true) == true -> AppCategory.FOOD
            root.contains("transport") || root.contains("travel") -> AppCategory.TRANSPORT
            root.contains("shopping") -> AppCategory.SHOPPING
            root.contains("entertainment") -> AppCategory.ENTERTAINMENT
            root.contains("utility") -> AppCategory.UTILITIES
            root.contains("health") -> AppCategory.HEALTHCARE
            else -> AppCategory.OTHER
        }
    }
}
