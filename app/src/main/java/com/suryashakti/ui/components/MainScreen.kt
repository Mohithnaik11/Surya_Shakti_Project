package com.suryashakti.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.suryashakti.data.SettingsRepository
import com.suryashakti.repository.SolarRepository
import com.suryashakti.ui.screens.ChatScreen
import com.suryashakti.ui.screens.DashboardScreen
import com.suryashakti.ui.screens.EntryScreen
import com.suryashakti.ui.screens.ReportScreen
import com.suryashakti.ui.screens.SettingsScreen
import com.suryashakti.ui.theme.YellowMain
import com.suryashakti.ui.theme.BlackMain

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Dashboard : Screen("dashboard", "Home", Icons.Filled.Dashboard)
    object Entry : Screen("entry", "Add Data", Icons.Filled.List)
    object Chat : Screen("chat", "Ask AI", Icons.Filled.Chat)
    object Settings : Screen("settings", "Settings", Icons.Filled.Settings)
}

@Composable
fun MainScreen(repository: SolarRepository, settingsRepository: SettingsRepository) {
    val navController = rememberNavController()
    val items = listOf(
        Screen.Dashboard,
        Screen.Entry,
        Screen.Chat,
        Screen.Settings
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = BlackMain,
                contentColor = YellowMain
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = BlackMain,
                            selectedTextColor = YellowMain,
                            unselectedIconColor = androidx.compose.ui.graphics.Color.Gray,
                            unselectedTextColor = androidx.compose.ui.graphics.Color.Gray,
                            indicatorColor = YellowMain
                        ),
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = Screen.Dashboard.route, Modifier.padding(innerPadding)) {
            composable(Screen.Dashboard.route) { DashboardScreen(repository, settingsRepository, navController) }
            composable(Screen.Entry.route) { EntryScreen(repository, settingsRepository) }
            composable(Screen.Chat.route) { ChatScreen() }
            composable(Screen.Settings.route) { SettingsScreen() }
            composable("report") { ReportScreen(repository, settingsRepository, navController) }
        }
    }
}
