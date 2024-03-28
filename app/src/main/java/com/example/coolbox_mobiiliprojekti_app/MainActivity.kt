package com.example.coolbox_mobiiliprojekti_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coolbox_mobiiliprojekti_app.ui.theme.CoolBoxmobiiliprojektiAppTheme
import com.example.coolbox_mobiiliprojekti_app.view.ConsumptionScreen
import com.example.coolbox_mobiiliprojekti_app.view.LoginScreen
import com.example.coolbox_mobiiliprojekti_app.view.MainScreen
import com.example.coolbox_mobiiliprojekti_app.view.PanelsScreen
import com.example.coolbox_mobiiliprojekti_app.view.ProductionScreen
import com.example.coolbox_mobiiliprojekti_app.view.ThemesScreen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoolBoxmobiiliprojektiAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    val scope = rememberCoroutineScope()
                    ModalNavigationDrawer(
                        drawerState = drawerState,
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                        drawerContent = {
                            ModalDrawerSheet {
                                NavigationDrawerItem(
                                    label = { Text(text = "Panels") },
                                    selected = false,
                                    onClick = {
                                        navController.navigate("panelsScreen") {
                                            launchSingleTop = true
                                        }
                                        scope.launch {
                                            drawerState.close()
                                        }
                                    })
                                NavigationDrawerItem(
                                    label = { Text(text = "Themes") },
                                    selected = false,
                                    onClick = {
                                        navController.navigate("themesScreen") {
                                            launchSingleTop = true
                                        }
                                        scope.launch {
                                            drawerState.close()
                                        }
                                    })
                                NavigationDrawerItem(
                                    label = { Text(text = "Logout") },
                                    selected = false,
                                    onClick = {
                                        navController.navigate("loginScreen")
                                        scope.launch {
                                            drawerState.close()
                                        }
                                    })
                            }
                        }) {
                        NavHost(navController = navController, startDestination = "loginScreen") {
                            composable(route = "loginScreen") {
                                LoginScreen(onLoginClick = {
                                    navController.navigate("mainScreen")
                                })
                            }
                            composable("mainScreen") {
                                MainScreen(onMenuClick = {
                                    scope.launch { drawerState.open() }
                                }, gotoConsumption = {
                                    navController.navigate("consumptionScreen") {
                                        launchSingleTop = true
                                    }
                                }, gotoProduction = {
                                    navController.navigate("productionScreen") {
                                        launchSingleTop = true
                                    }
                                })
                            }
                            composable("consumptionScreen") {
                                ConsumptionScreen(goBack = {
                                    navController.navigateUp()
                                }, onMenuClick = {
                                    scope.launch { drawerState.open() }
                                })
                            }
                            composable("productionScreen") {
                                ProductionScreen(goBack = {
                                    navController.navigateUp()
                                }, onMenuClick = {
                                    scope.launch { drawerState.open() }
                                })
                            }
                            composable("panelsScreen") {
                                PanelsScreen(goBack = {
                                    navController.navigateUp()
                                }, onMenuClick = {
                                    scope.launch { drawerState.open() }
                                })
                            }
                            composable("themesScreen") {
                                ThemesScreen(goBack = {
                                    navController.navigateUp()
                                }, onMenuClick = {
                                    scope.launch { drawerState.open() }
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}
