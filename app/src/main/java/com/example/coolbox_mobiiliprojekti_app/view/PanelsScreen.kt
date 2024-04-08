package com.example.coolbox_mobiiliprojekti_app.view

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coolbox_mobiiliprojekti_app.datastore.UserPreferences
import com.example.coolbox_mobiiliprojekti_app.model.PanelPreferences
import com.example.coolbox_mobiiliprojekti_app.viewmodel.MainScreenViewModel
import com.example.coolbox_mobiiliprojekti_app.viewmodel.PanelsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PanelsScreen(
    onMenuClick: () -> Unit,
    goBack: () -> Unit
) {
    val panelsVm: PanelsViewModel = viewModel()
    val mainScreenVm: MainScreenViewModel = viewModel()

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var preferenceDataStore = UserPreferences(context)

    Scaffold(
        topBar =
        {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { goBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowLeft,
                            contentDescription = "Back"
                        )
                    }
                },
                title = { Text(text = "Panels") },
                actions = {
                    IconButton(onClick = { onMenuClick() }) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                    }
                })
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Consumption panel")
                    Switch(
                        checked = panelsVm.consumptionChecked.value,
                        onCheckedChange = {
                            panelsVm.consumptionChecked.value = it
                            if (panelsVm.consumptionChecked.value) {
                                Log.d("itekki", "PanelsScreen: consumption check on")
                                var panelPreferences = PanelPreferences(
                                        it,
                                        panelsVm.productionChecked.value,
                                        panelsVm.batteryChecked.value)
                                scope.launch {
                                    preferenceDataStore.setPreferences(panelPreferences)
                                }
                                mainScreenVm.conPanelVisible = true
                                // Tämä value ei vielä tällä hetkellä ymmärtääkseni pysy siinä arvossa
                                // mihin se asetetaan kun tästä ruudusta poistutaan, tai
                                // mainscreeniin palaaminen ei päivitä UI:ta,
                                // ja switchi myös resetoituu aina kun tähän ruutuun mennään takaisin
                            }
                            else {
                                Log.d("itekki", "PanelsScreen: consumption check off")
                                mainScreenVm.conPanelVisible = false
                            }
                        })
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Production panel")
                    Switch(
                        checked = panelsVm.productionChecked.value,
                        onCheckedChange = {
                            panelsVm.productionChecked.value = it
                            if (panelsVm.productionChecked.value) {
                                // Aseta paneeli näkyväksi, kun switch on päällä
                            }
                            else {
                                // Piilota paneeli, kun switch on pois päältä
                            }
                        })
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Battery panel")
                    Switch(
                        checked = panelsVm.batteryChecked.value,
                        onCheckedChange = {
                            panelsVm.batteryChecked.value = it
                            if (panelsVm.batteryChecked.value) {
                                // Aseta paneeli näkyväksi, kun switch on päällä
                            }
                            else {
                                // Piilota paneeli, kun switch on pois päältä
                            }
                        })
                }
            }
        }
    }
}