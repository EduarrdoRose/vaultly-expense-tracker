package com.vaultly.presentation.components

import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable fun TransactionItem(name: String, amount: String) { Card { Text("$name • $amount") } }
@Composable fun AccountCard(title: String, balance: String) { Card { Text("$title $balance") } }
@Composable fun BudgetProgressCard(category: String, progress: Float) { Card { Text("$category ${progress * 100}%") } }
@Composable fun CategoryChip(label: String) { Card { Text(label) } }
@Composable fun ShimmerBox() { Card { Text("Loading…") } }
@Composable fun AmountText(amount: String) { Text(amount) }
