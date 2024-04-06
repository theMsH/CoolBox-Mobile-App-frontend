package com.example.coolbox_mobiiliprojekti_app.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coolbox_mobiiliprojekti_app.ui.theme.PanelColor
import com.example.coolbox_mobiiliprojekti_app.ui.theme.TextsLightColor
import com.example.coolbox_mobiiliprojekti_app.ui.theme.TopAppBarColor
import com.example.coolbox_mobiiliprojekti_app.viewmodel.MainScreenViewModel
import com.example.datachartexample2.tests.test3.ConsumptionViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onMenuClick: () -> Unit,
    gotoConsumption: () -> Unit,
    gotoProduction: () -> Unit
) {
    val mainScreenVm: MainScreenViewModel = viewModel()
    val consumptionVM: ConsumptionViewModel = viewModel()
    val conPanelVisible = mainScreenVm.conPanelVisible
    val prodPanelVisible = mainScreenVm.prodPanelVisible
    val batPanelVisible = mainScreenVm.batPanelVisible
    val tempPanelVisible = mainScreenVm.tempPanelVisible

    Scaffold(
        topBar = {
            Surface(shadowElevation = 2.dp) {
                CenterAlignedTopAppBar(
                    title = { Text(text = "Main") },
                    actions = {
                        IconButton(
                            onClick = { onMenuClick() }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = TopAppBarColor
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
            when {
                mainScreenVm.mainScreenState.value.loading -> CircularProgressIndicator(
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )
                else -> LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                    ,
                    // Miikan edit: Laitetaan padding mieluummin itemeille,
                    // niin scrollatessa näkee visuaalisesti milloin tulee "seinä vastaan"
                    // Benkun vastaus: Kuulostaa hyvältä ja järkevältä, tuo verticalArrangement
                    // olikin tuossa vaan niitä placeholdereita varten, ettei ne ollut kiinni toisissaan
                    //verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (conPanelVisible) { // Jos boolean on tosi, näytetään paneeli
                        item {
                            Card(
                                modifier = Modifier
                                    .wrapContentSize(Alignment.Center)
                                    .fillMaxWidth()
                                    .padding(horizontal = 2.dp, vertical = 4.dp)
                                    .clickable(onClick = gotoConsumption)
                                ,
                                colors = CardDefaults.cardColors(
                                    containerColor = PanelColor,
                                    contentColor = TextsLightColor
                                )
                            ) {
                                Text(
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .padding(top = 20.dp)
                                    ,
                                    fontSize = 20.sp,
                                    text = "Kulutus graafi"
                                )
                                Spacer(modifier = Modifier.height(300.dp)) // Poista kun tulee oikea content
                            }
                        }
                    }
                    if (prodPanelVisible) {
                        item {
                            Card(
                                modifier = Modifier
                                    .wrapContentSize(Alignment.Center)
                                    .fillMaxWidth()
                                    .padding(horizontal = 2.dp, vertical = 4.dp)
                                    .clickable(onClick = gotoProduction)
                                ,
                                colors = CardDefaults.cardColors(
                                    containerColor = PanelColor,
                                    contentColor = TextsLightColor
                                )
                            ) {
                                Text(
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .padding(top = 20.dp)
                                    ,
                                    fontSize = 20.sp,
                                    text = "Tuotto graafi"
                                )
                                Spacer(modifier = Modifier.height(300.dp)) // Poista kun tulee oikea content
                            }
                        }
                    }
                    if (batPanelVisible) {
                        item {
                            Card(
                                modifier = Modifier
                                    .wrapContentSize(Alignment.Center)
                                    .fillMaxWidth()
                                    .padding(horizontal = 2.dp, vertical = 4.dp)
                                    .clickable(onClick = {})
                                ,
                                colors = CardDefaults.cardColors(
                                    containerColor = PanelColor,
                                    contentColor = TextsLightColor
                                )
                            ) {
                                Text(
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .padding(top = 20.dp)
                                    ,
                                    fontSize = 20.sp,
                                    text = "Akun dataa"
                                )
                                Spacer(modifier = Modifier.height(120.dp)) // Poista kun tulee oikea content
                            }
                        }
                    }
                    if (tempPanelVisible) {
                        item {
                            Card(
                                modifier = Modifier
                                    .wrapContentSize(Alignment.Center)
                                    .fillMaxWidth()
                                    .padding(horizontal = 2.dp, vertical = 4.dp)
                                    .clickable(onClick = {})
                                ,
                                colors = CardDefaults.cardColors(
                                    containerColor = PanelColor,
                                    contentColor = TextsLightColor
                                )
                            ) {
                                Text(
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .padding(top = 20.dp)
                                    ,
                                    fontSize = 20.sp,
                                    text = "Lämpötiladataa"
                                )
                                Spacer(modifier = Modifier.height(300.dp)) // Poista kun tulee oikea content
                            }
                        }
                    }

                } // Column loppu
            }
        }
    }

}