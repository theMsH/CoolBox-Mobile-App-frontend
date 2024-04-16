package com.example.coolbox_mobiiliprojekti_app.view

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coolbox_mobiiliprojekti_app.R
import com.example.coolbox_mobiiliprojekti_app.datastore.UserPreferences
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PanelsScreen(
    onMenuClick: () -> Unit,
    goBack: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val preferenceDataStore = UserPreferences(context)

    val consumptionChecked = preferenceDataStore.getConsumptionActive.collectAsState(initial = true)
    val productionChecked = preferenceDataStore.getProductionActive.collectAsState(initial = true)
    val batteryChecked = preferenceDataStore.getBatteryActive.collectAsState(initial = true)
    val tempChecked = preferenceDataStore.getTempActive.collectAsState(initial = true)
    val darkColoursChecked = preferenceDataStore.getDarkMode.collectAsState(initial = isSystemInDarkTheme())

    Scaffold(
        topBar = {
            Surface(shadowElevation = 2.dp) {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        IconButton(onClick = { goBack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    title = { Text(text = stringResource(R.string.panels_title)) },
                    actions = {
                        IconButton(onClick = { onMenuClick() }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    )
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                    text = stringResource(R.string.panels_ui),
                    fontSize = 25.sp,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )

                Card(
                    modifier = Modifier
                        .padding(bottom = 30.dp, top = 10.dp, start = 10.dp, end = 10.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                        contentColor = MaterialTheme.colorScheme.primary,
                    )
                ) {
                    // Use DarkTheme switchi
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.use_darkmode),
                            fontSize = 20.sp
                        )
                        Switch(
                            checked = darkColoursChecked.value,
                            onCheckedChange = {
                                scope.launch {
                                    preferenceDataStore.setDarkMode(it)
                                }
                            }
                        )
                    }
                }

                Text(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                    text = stringResource(R.string.panels_description),
                    fontSize = 25.sp,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )

                Card(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                        contentColor = MaterialTheme.colorScheme.primary,
                    )
                ) {

                    Row(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.con_panel_name),
                            fontSize = 20.sp
                        )
                        Switch(
                            checked = consumptionChecked.value,
                            onCheckedChange = {
                                scope.launch {
                                    preferenceDataStore.setConsumptionActive(it)
                                }
                            }
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.pro_panel_name),
                            fontSize = 20.sp
                        )
                        Switch(
                            checked = productionChecked.value,
                            onCheckedChange = {
                                scope.launch {
                                    preferenceDataStore.setProductionActive(it)
                                }
                            }
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.bat_panel_name),
                            fontSize = 20.sp
                        )
                        Switch(
                            checked = batteryChecked.value,
                            onCheckedChange = {
                                scope.launch {
                                    preferenceDataStore.setBatteryActive(it)
                                }
                            }
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.temp_panel_name),
                            fontSize = 20.sp
                        )
                        Switch(
                            checked = tempChecked.value,
                            onCheckedChange = {
                                scope.launch {
                                    preferenceDataStore.setTempActive(it)
                                }
                            }
                        )
                    }
                }

            }
        }
    }
}