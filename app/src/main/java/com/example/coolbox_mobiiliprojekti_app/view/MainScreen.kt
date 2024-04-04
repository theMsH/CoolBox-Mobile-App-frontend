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
import androidx.compose.material3.CardColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coolbox_mobiiliprojekti_app.model.ConsumptionStatsResponse
import com.example.coolbox_mobiiliprojekti_app.viewmodel.ConsumptionViewModel
import com.example.coolbox_mobiiliprojekti_app.viewmodel.MainScreenViewModel
import com.example.datachartexample2.model.test.ConsumptionColumnChart


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onMenuClick: () -> Unit,
    gotoConsumption: () -> Unit,
    gotoProduction: () -> Unit
) {
    val mainScreenVm: MainScreenViewModel = viewModel()

    val consumptionChartVm: ConsumptionViewModel = viewModel()

    // Havaitaan kulutustilastot viewmodelista
    var consumptionStatsData = consumptionChartVm.consumptionStatsData
    var temperatureStatsData = consumptionChartVm.temperatureStatsData
    Scaffold(
        topBar = {
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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
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
                    item {
                        Card(
                            modifier = Modifier
                                .wrapContentSize(Alignment.Center)
                                .fillMaxWidth()
                                .padding(horizontal = 2.dp, vertical = 4.dp)
                                .clickable(onClick = gotoConsumption)
                            ,
                            colors = CardColors(
                                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                contentColor = MaterialTheme.colorScheme.primary,
                                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                disabledContentColor = MaterialTheme.colorScheme.secondary
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
                            ConsumptionColumnChart(consumptionStatsData)
                        }
                    }
                    item {
                        Card(
                            modifier = Modifier
                                .wrapContentSize(Alignment.Center)
                                .fillMaxWidth()
                                .padding(horizontal = 2.dp, vertical = 4.dp)
                                .clickable(onClick = gotoProduction)
                            ,
                            colors = CardColors(
                                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                contentColor = MaterialTheme.colorScheme.primary,
                                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                disabledContentColor = MaterialTheme.colorScheme.secondary
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
                    item {
                        Card(
                            modifier = Modifier
                                .wrapContentSize(Alignment.Center)
                                .fillMaxWidth()
                                .padding(horizontal = 2.dp, vertical = 4.dp)
                                .clickable(onClick = {})
                            ,
                            colors = CardColors(
                                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                contentColor = MaterialTheme.colorScheme.primary,
                                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                disabledContentColor = MaterialTheme.colorScheme.secondary
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
                    item {
                        Card(
                            modifier = Modifier
                                .wrapContentSize(Alignment.Center)
                                .fillMaxWidth()
                                .padding(horizontal = 2.dp, vertical = 4.dp)
                                .clickable(onClick = {})
                            ,
                            colors = CardColors(
                                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                contentColor = MaterialTheme.colorScheme.primary,
                                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                disabledContentColor = MaterialTheme.colorScheme.secondary
                            )
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(top = 20.dp)
                                ,
                                fontSize = 20.sp,
                                text = "Random graafi"
                            )
                            Spacer(modifier = Modifier.height(300.dp)) // Poista kun tulee oikea content
                        }
                    }
                    item {
                        Card(
                            modifier = Modifier
                                .wrapContentSize(Alignment.Center)
                                .fillMaxWidth()
                                .padding(horizontal = 2.dp, vertical = 4.dp)
                                .clickable(onClick = {})
                            ,
                            colors = CardColors(
                                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                contentColor = MaterialTheme.colorScheme.primary,
                                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                disabledContentColor = MaterialTheme.colorScheme.secondary
                            )
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(top = 20.dp)
                                ,
                                fontSize = 20.sp,
                                text = "Biopolttoaine- ja harmaavesisäiliö %"
                            )
                            Spacer(modifier = Modifier.height(120.dp)) // Poista kun tulee oikea content
                        }
                    }

                }
            }
        }
    }

}