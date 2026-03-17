package com.vaultly.util

import com.vaultly.domain.model.AppCategory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CategoryMapperTest {
    @Test
    fun `maps food categories`() {
        assertEquals(AppCategory.FOOD, CategoryMapper.fromPlaid(listOf("Food and Drink")))
    }
}
