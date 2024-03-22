package com.example.coolbox_mobiiliprojekti_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coolbox_mobiiliprojekti_app.ui.theme.CoolBoxmobiiliprojektiAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoolBoxmobiiliprojektiAppTheme {
                // A surface container using the 'background' color from the theme
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
                        /*TODO*/
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
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}
