package com.example.coolbox_mobiiliprojekti_app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.DashboardCustomize
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.coolbox_mobiiliprojekti_app.ui.theme.CoolAppText
import com.example.coolbox_mobiiliprojekti_app.ui.theme.CoolBoxmobiiliprojektiAppTheme
import com.example.coolbox_mobiiliprojekti_app.ui.theme.TertiaryColor
import com.example.coolbox_mobiiliprojekti_app.view.LoginScreen
import com.example.coolbox_mobiiliprojekti_app.view.MainScreen
import com.example.coolbox_mobiiliprojekti_app.view.PanelsScreen
import com.example.coolbox_mobiiliprojekti_app.view.ProductionScreen
import com.example.coolbox_mobiiliprojekti_app.view.RegisterScreen
import com.example.coolbox_mobiiliprojekti_app.view.ThemesScreen
import com.example.coolbox_mobiiliprojekti_app.viewmodel.LoginViewModel
import com.example.coolbox_mobiiliprojekti_app.view.ConsumptionScreen
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
                    val navBackStackEntry by navController.currentBackStackEntryAsState()

                    // Autologinin takia luodaan loginViewModel täällä.
                    val loginVm: LoginViewModel = viewModel()

                    LaunchedEffect(Unit) {
                        if (!loginVm.loginState.value.initialized) {
                            loginVm.setInitialized()
                            loginVm.tryAutoLogin()
                        }
                    }

                    ModalNavigationDrawer(
                        gesturesEnabled = (
                                navBackStackEntry?.destination?.route != "loginScreen" &&
                                        navBackStackEntry?.destination?.route != "registerScreen"
                                ),
                        drawerState = drawerState,
                        drawerContent = {
                            ModalDrawerSheet {

                                // Drawerlayoutin logo column

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 20.dp)
                                    ,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Top
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(painter = painterResource(id = R.drawable.coolapp_icon),
                                             contentDescription = "CoolAppIcon",
                                             Modifier.size(50.dp),
                                             tint = CoolAppText
                                        )
                                        Text(
                                            fontSize = 20.sp,
                                            text = "oolApp",
                                            color = CoolAppText
                                        )
                                    }
                                    Spacer(Modifier.height(20.dp))
                                }


                                // Käyttäjä

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(NavigationDrawerItemDefaults.ItemPadding)
                                        .padding(bottom = 20.dp, start = 12.dp, end = 12.dp)
                                    ,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        modifier = Modifier
                                            .size(50.dp)
                                        ,
                                        imageVector = Icons.Filled.AccountCircle,
                                        contentDescription = "User"
                                    )
                                    Spacer(Modifier.width(16.dp))
                                    Column {
                                        Text(
                                            text = stringResource(R.string.user)
                                        )
                                        Text(
                                            color = TertiaryColor,
                                            text = loginVm.user.value.user.username
                                        )
                                    }
                                }


                                // Navigation itemit

                                NavigationDrawerItem(
                                    modifier = Modifier
                                        .padding(NavigationDrawerItemDefaults.ItemPadding)
                                    ,
                                    label = { Text(text = "Logout") },
                                    selected = navBackStackEntry?.destination?.route == "loginScreen",
                                    onClick = {
                                        loginVm.logout()
                                        navController.navigate("loginScreen") {
                                            popUpTo("mainScreen") { inclusive = true }
                                        }
                                        scope.launch {
                                            drawerState.close()
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Filled.LockOpen,
                                            contentDescription = "Logout"
                                        )
                                    }
                                )

                                HorizontalDivider(modifier = Modifier.padding(15.dp))

                                NavigationDrawerItem(
                                    modifier = Modifier
                                        .padding(NavigationDrawerItemDefaults.ItemPadding)
                                    ,
                                    label = { Text(text = "Home") },
                                    selected = navBackStackEntry?.destination?.route == "mainScreen",
                                    onClick = {
                                        navController.navigate("mainScreen") {
                                            launchSingleTop = true
                                        }
                                        scope.launch {
                                            drawerState.close()
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Filled.Home,
                                            contentDescription = "Home"
                                        )
                                    }
                                )
                                NavigationDrawerItem(
                                    modifier = Modifier
                                        .padding(NavigationDrawerItemDefaults.ItemPadding)
                                    ,
                                    label = { Text(text = "Panels") },
                                    selected = navBackStackEntry?.destination?.route == "panelsScreen",
                                    onClick = {
                                        navController.navigate("panelsScreen") {
                                            launchSingleTop = true
                                        }
                                        scope.launch {
                                            drawerState.close()
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Filled.DashboardCustomize,
                                            contentDescription = "Panels"
                                        )
                                    }
                                )
                                NavigationDrawerItem(
                                    modifier = Modifier
                                        .padding(NavigationDrawerItemDefaults.ItemPadding)
                                    ,
                                    label = { Text(text = "Themes") },
                                    selected = navBackStackEntry?.destination?.route == "themesScreen",
                                    onClick = {
                                        navController.navigate("themesScreen") {
                                            launchSingleTop = true
                                        }
                                        scope.launch {
                                            drawerState.close()
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Filled.ColorLens,
                                            contentDescription = "Themes"
                                        )
                                    }
                                )

                            }
                        }
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = "loginScreen"
                        ) {
                            composable("loginScreen") {
                                LoginScreen(
                                    loginVm, // Välitetään täällä luotu loginVM LoginScreenin viewmodeliksi
                                    onLoginSuccess = {
                                        navController.navigate("mainScreen")
                                    },
                                    gotoRegister = {
                                        navController.navigate("registerScreen")
                                    }
                                )
                            }
                            composable("mainScreen") {
                                MainScreen(
                                    onMenuClick = {
                                        scope.launch { drawerState.open() }
                                    },
                                    goToConsumption = {
                                        navController.navigate("consumptionScreen") {
                                            launchSingleTop = true
                                        }
                                    },
                                    goToProduction = {
                                        navController.navigate("productionScreen") {
                                            launchSingleTop = true
                                        }
                                    }
                                )
                            }
                            composable("consumptionScreen") {
                                ConsumptionScreen(
                                    goBack = {
                                        navController.navigateUp()
                                    },
                                    onMenuClick = {
                                        scope.launch { drawerState.open() }
                                    }
                                )
                            }
                            composable("productionScreen") {
                                ProductionScreen(
                                    goBack = {
                                        navController.navigateUp()
                                    },
                                    onMenuClick = {
                                        scope.launch { drawerState.open() }
                                    }
                                )
                            }
                            composable("panelsScreen") {
                                PanelsScreen(
                                    goBack = {
                                        navController.navigateUp()
                                    },
                                    onMenuClick = {
                                        scope.launch { drawerState.open() }
                                    }
                                )
                            }
                            composable("themesScreen") {
                                ThemesScreen(
                                    goBack = {
                                        navController.navigateUp()
                                    },
                                    onMenuClick = {
                                        scope.launch { drawerState.open() }
                                    }
                                )
                            }
                            composable("registerScreen") {
                                RegisterScreen(
                                    onRegisterClick = {
                                        navController.navigateUp()
                                        Toast.makeText(
                                            this@MainActivity,
                                            "New account registered!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    },
                                    goBack = {
                                        navController.navigateUp()
                                    }
                                )
                            }

                        }
                    }
                }
            }
        }
    }

}

