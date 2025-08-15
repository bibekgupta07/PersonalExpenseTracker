package com.bibekgupta.personalexpensetracker.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// HOME
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit,
    navigateToTransactions: () -> Unit
) {
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Home")
        Spacer(Modifier.height(16.dp))
        Button(onClick = { navigateToDetail("item_1") }) { Text("Open Item") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = navigateToTransactions) { Text("Go to Transactions Tab") }
    }
}

@Composable
fun HomeDetailScreen(itemId: String, navigateToTransactions: () -> Unit) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Home Detail: $itemId")
        Spacer(Modifier.height(16.dp))
        Button(onClick = navigateToTransactions) { Text("Go to Transactions Tab") }
    }
}

// TRANSACTIONS
@Composable
fun TransactionsScreen(modifier: Modifier = Modifier, navigateToTxnDetail: (String) -> Unit) {
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Transactions")
        Spacer(Modifier.height(16.dp))
        Button(onClick = { navigateToTxnDetail("txn_123") }) { Text("Open Transaction") }
    }
}

@Composable
fun TransactionDetailScreen(txnId: String) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Transaction Detail: $txnId")
    }
}

// BUDGETS
@Composable
fun BudgetsScreen(modifier: Modifier = Modifier, navigateToBudgetDetail: (String) -> Unit) {
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Budgets")
        Spacer(Modifier.height(16.dp))
        Button(onClick = { navigateToBudgetDetail("budget_abc") }) { Text("Open Budget") }
    }
}

@Composable
fun BudgetDetailScreen(budgetId: String) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Budget Detail: $budgetId")
    }
}

// ANALYTICS
@Composable
fun AnalyticsScreen(modifier: Modifier = Modifier, navigateToChart: (String) -> Unit) {
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Analytics")
        Spacer(Modifier.height(16.dp))
        Button(onClick = { navigateToChart("chart_xyz") }) { Text("Open Chart") }
    }
}

@Composable
fun AnalyticsDetailScreen(chartId: String) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Analytics Detail: $chartId")
    }
}
