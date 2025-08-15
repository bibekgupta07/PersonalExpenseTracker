package com.bibekgupta.personalexpensetracker.utils.navigator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.bibekgupta.personalexpensetracker.presentation.AnalyticsDetailScreen
import com.bibekgupta.personalexpensetracker.presentation.AnalyticsScreen
import com.bibekgupta.personalexpensetracker.presentation.BudgetDetailScreen
import com.bibekgupta.personalexpensetracker.presentation.BudgetsScreen
import com.bibekgupta.personalexpensetracker.presentation.HomeDetailScreen
import com.bibekgupta.personalexpensetracker.presentation.HomeScreen
import com.bibekgupta.personalexpensetracker.presentation.TransactionDetailScreen
import com.bibekgupta.personalexpensetracker.presentation.TransactionsScreen

import kotlinx.serialization.Serializable

// ---------- Nav keys (per-screen) ----------
@Serializable
data object HomeRoot : NavKey

@Serializable
data class HomeDetail(val itemId: String) : NavKey

@Serializable
data object TransactionsRoot : NavKey

@Serializable
data class TransactionDetail(val txnId: String) : NavKey

@Serializable
data object BudgetsRoot : NavKey

@Serializable
data class BudgetDetail(val budgetId: String) : NavKey

@Serializable
data object AnalyticsRoot : NavKey

@Serializable
data class AnalyticsDetail(val chartId: String) : NavKey

// ---------- Tabs enum ----------
enum class BottomTab { Home, Transactions, Budgets, Analytics }

// ---------- AppNavigator composable ----------
@Composable
fun AppNavigator(modifier: Modifier = Modifier) {
    // Create one nav3 back stack per tab (saveable)
    val homeStack = rememberNavBackStack(HomeRoot)
    val transactionsStack = rememberNavBackStack(TransactionsRoot)
    val budgetsStack = rememberNavBackStack(BudgetsRoot)
    val analyticsStack = rememberNavBackStack(AnalyticsRoot)

    // currently selected tab (index)
    var selectedIndex by remember { mutableIntStateOf(0) }
    val currentTab = BottomTab.values()[selectedIndex]

    // helper to map tab -> corresponding backStack
    val activeBackStack = when (currentTab) {
        BottomTab.Home -> homeStack
        BottomTab.Transactions -> transactionsStack
        BottomTab.Budgets -> budgetsStack
        BottomTab.Analytics -> analyticsStack
    }

    // Surface to hold bottom bar + NavDisplay
    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // single NavDisplay driven by the current tab's backStack
            NavDisplay(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(bottom = 56.dp),
                backStack = activeBackStack,
                onBack = {
                    // pop if possible; if at root, optionally switch to Home or finish
                    if (activeBackStack.size > 1) {
                        activeBackStack.removeAt(activeBackStack.lastIndex)
                    } else {
                        // At root: if not on Home, switch to Home instead of exiting
                        if (currentTab != BottomTab.Home) {
                            selectedIndex = BottomTab.Home.ordinal
                        } else {
                            // If already on Home root, you may let Activity handle back (finish)
                            // here we do nothing so system back can finish the activity
                        }
                    }
                },
                entryProvider = entryProvider {
                    // HOME tab entries
                    entry<HomeRoot> {
                        HomeScreen(
                            navigateToDetail = { id -> homeStack.add(HomeDetail(id)) },
                            navigateToTransactions = {
                                selectedIndex = BottomTab.Transactions.ordinal
                            }
                        )
                    }
                    entry<HomeDetail> { key ->
                        HomeDetailScreen(
                            itemId = key.itemId,
                            navigateToTransactions = {
                                selectedIndex = BottomTab.Transactions.ordinal
                            }
                        )
                    }

                    // TRANSACTIONS tab entries
                    entry<TransactionsRoot> {
                        TransactionsScreen(
                            navigateToTxnDetail = { txnId ->
                                transactionsStack.add(
                                    TransactionDetail(
                                        txnId
                                    )
                                )
                            },
                        )
                    }
                    entry<TransactionDetail> { key ->
                        TransactionDetailScreen(txnId = key.txnId)
                    }

                    // BUDGETS tab entries
                    entry<BudgetsRoot> {
                        BudgetsScreen(
                            navigateToBudgetDetail = { id -> budgetsStack.add(BudgetDetail(id)) }
                        )
                    }
                    entry<BudgetDetail> { key ->
                        BudgetDetailScreen(budgetId = key.budgetId)
                    }

                    // ANALYTICS tab entries
                    entry<AnalyticsRoot> {
                        AnalyticsScreen(
                            navigateToChart = { id -> analyticsStack.add(AnalyticsDetail(id)) }
                        )
                    }
                    entry<AnalyticsDetail> { key ->
                        AnalyticsDetailScreen(chartId = key.chartId)
                    }
                }
            )

            // Bottom navigation bar
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                NavigationBar {
                    val items = listOf(
                        BottomTab.Home to Pair("Home", Icons.Default.Home),
                        BottomTab.Transactions to Pair("Transactions", Icons.Default.AttachMoney),
                        BottomTab.Budgets to Pair("Budgets", Icons.Default.BarChart),
                        BottomTab.Analytics to Pair("Analytics", Icons.Default.Analytics)
                    )

                    items.forEachIndexed { index, (tab, pair) ->
                        val (label, icon) = pair
                        NavigationBarItem(
                            selected = selectedIndex == index,
                            onClick = {
                                if (selectedIndex == index) {
                                    // if tapped selected tab -> pop to root of that tab
                                    val stack = when (tab) {
                                        BottomTab.Home -> homeStack
                                        BottomTab.Transactions -> transactionsStack
                                        BottomTab.Budgets -> budgetsStack
                                        BottomTab.Analytics -> analyticsStack
                                    }
                                    // pop to root: clear everything and add root key
                                    stack.clear()
                                    val rootKey = when (tab) {
                                        BottomTab.Home -> HomeRoot
                                        BottomTab.Transactions -> TransactionsRoot
                                        BottomTab.Budgets -> BudgetsRoot
                                        BottomTab.Analytics -> AnalyticsRoot
                                    }
                                    stack.add(rootKey)
                                } else {
                                    // switch to tapped tab (preserve its backStack)
                                    selectedIndex = index
                                }
                            },
                            icon = {
                                androidx.compose.material3.Icon(
                                    icon,
                                    contentDescription = label
                                )
                            },
                            label = { Text(label) }
                        )
                    }
                }
            }
        }
    }
}



























