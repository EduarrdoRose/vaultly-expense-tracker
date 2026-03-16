package com.vaultly.presentation.components

import androidx.compose.ui.graphics.Color
import com.vaultly.domain.model.AppCategory

val AppCategory.color: Color get() = Color(this.colorHex)
