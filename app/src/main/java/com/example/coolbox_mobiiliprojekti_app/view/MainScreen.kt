package com.example.coolbox_mobiiliprojekti_app.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coolbox_mobiiliprojekti_app.R
import com.example.coolbox_mobiiliprojekti_app.datastore.UserPreferences
import com.example.coolbox_mobiiliprojekti_app.ui.theme.PanelColor
import com.example.coolbox_mobiiliprojekti_app.ui.theme.PanelTextButtonColor
import com.example.coolbox_mobiiliprojekti_app.ui.theme.PanelTextColor
import com.example.coolbox_mobiiliprojekti_app.ui.theme.TextsLightColor
import com.example.coolbox_mobiiliprojekti_app.ui.theme.TopAppBarColor
import com.example.coolbox_mobiiliprojekti_app.viewmodel.MainScreenViewModel
import com.example.coolbox_mobiiliprojekti_app.viewmodel.ProductionViewModel
import com.example.coolbox_mobiiliprojekti_app.viewmodel.ConsumptionViewModel
import com.example.coolbox_mobiiliprojekti_app.viewmodel.TemperaturesViewModel
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onMenuClick: () -> Unit,
    goToConsumption: () -> Unit,
    goToProduction: () -> Unit
) {
    val mainScreenVm: MainScreenViewModel = viewModel()
    val consumptionVM: ConsumptionViewModel = viewModel()
    // DataStoren käyttöönotto
    val context = LocalContext.current
    val preferenceDataStore = UserPreferences(context)
    // Haetaan DataStoresta booleanit, joiden avulla laitetaan paneelit näkyviin/pois näkyvistä
    val conPanelVisible = preferenceDataStore.getConsumptionActive.collectAsState(initial = true)
    val prodPanelVisible = preferenceDataStore.getProductionActive.collectAsState(initial = true)
    val batPanelVisible = preferenceDataStore.getBatteryActive.collectAsState(initial = true)
    val tempPanelVisible = mainScreenVm.tempPanelVisible



    Scaffold(
        topBar = {
            Surface(shadowElevation = 2.dp) {
                CenterAlignedTopAppBar(
                    title = { Text(text = stringResource(R.string.front_page)) },
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
                ) {
                    if (conPanelVisible.value) { // Jos boolean on tosi, näytetään paneeli
                        item {
                            Card(
                                modifier = Modifier
                                    .wrapContentSize(Alignment.Center)
                                    .fillMaxWidth()
                                    .padding(horizontal = 2.dp, vertical = 4.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = PanelColor,
                                    contentColor = TextsLightColor
                                )
                            ) {
                                ConsumptionPanel7Days(goToConsumption)
                            }
                        }
                    }
                    if (prodPanelVisible.value) {
                        item {
                            Card(
                                modifier = Modifier
                                    .wrapContentSize(Alignment.Center)
                                    .fillMaxWidth()
                                    .padding(horizontal = 2.dp, vertical = 4.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = PanelColor,
                                    contentColor = TextsLightColor
                                )
                            ) {
                                ProductionPanel7Days(goToProduction)
                            }
                        }
                    }
                    if (batPanelVisible.value) {
                        item {
                            Card(
                                modifier = Modifier
                                    .wrapContentSize(Alignment.Center)
                                    .fillMaxWidth()
                                    .padding(horizontal = 2.dp, vertical = 4.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = PanelColor,
                                    contentColor = PanelTextColor
                                )
                            ) {
                                BatteryChart()
                            }
                        }
                    }
                    if (tempPanelVisible) {
                        item {
                            Card(
                                modifier = Modifier
                                    .wrapContentSize(Alignment.Center)
                                    .fillMaxWidth()
                                    .padding(horizontal = 2.dp, vertical = 4.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = PanelColor,
                                    contentColor = PanelTextColor
                                )
                            ){
                                TemperatureDatas()
                            }
                        }
                    }

                } // Column loppu
            }
        }
    }

}

@Composable
fun TemperatureDatas() {
    val viewModel: TemperaturesViewModel = viewModel()

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp)) {
        // Tarkistetaan ViewModelin tilaa ja valitaan näytettävä sisältö sen mukaan.
        when {
            // Jos data on latautumassa, näytetään latausindikaattori keskellä.
            viewModel.temperaturesChartState.value.loading -> CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )

            // Jos lämpötiladatat ovat tyhjiä tai null, näytetään virheviesti.
            viewModel.temperaturesStatsData.isNullOrEmpty() -> Text(
                text = stringResource(R.string.temperature_data_not_available),
                modifier = Modifier.align(Alignment.Center),
                fontSize = 20.sp,
                color = Color.Red // Virhetekstin väri asetettu punaiseksi.
            )

            // Jos datat ovat saatavilla, näytetään ne sarakkeessa.
            else -> Column(
                modifier = Modifier.fillMaxWidth(), // Sarake täyttää leveyssuunnassa koko tilan.
                verticalArrangement = Arrangement.spacedBy(10.dp), // Elementtien välinen pystysuuntainen etäisyys.
                horizontalAlignment = Alignment.CenterHorizontally // Vaakasuora keskitys.
            ) {
                // Otsikkoteksti lämpötiladatalle.
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    fontSize = 20.sp,
                    text = "Lämpötila datat - Last updated: ${viewModel.lastFetchTime ?: "Not available"}",
                    textAlign = TextAlign.Center
                )

                // Iteroidaan läpi lämpötiladatat ja luodaan jokaiselle sensorille oma tekstielementti.
                viewModel.temperaturesStatsData!!.forEach { (sensor, temperature) ->
                    Text(
                        text = "$sensor: $temperature °C", // Sensorin nimi ja lämpötila Celsius-asteina.
                        fontSize = 30.sp,
                        color = PanelTextButtonColor, // Tekstin väri, joka on määritetty sovelluksen teemassa.
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun ProductionPanel7Days(
    goToProduction: () -> Unit
) {
    val viewModel: ProductionViewModel = viewModel()
    // Alusta nykyinen aikaväli tilamuuttuja
    val currentTimeInterval by remember { mutableStateOf(TimeInterval.MAIN) }

    // Määritä nykyisen viikon ensimmäinen päivä
    val currentWeekStartDate by remember { mutableStateOf(LocalDate.now()) }

    // Päivitä kulutustilastot ja lämpötilatilastot haettaessa dataa
    LaunchedEffect(key1 = currentWeekStartDate, key2 = Unit) {
        // Päivitä datan haku sen mukaan, mikä aikaväli on valittu
        if (currentTimeInterval == TimeInterval.MAIN) {
            viewModel.fetchData(TimeInterval.MAIN, currentWeekStartDate.toString())
        }
    }
    // Näytön sisältö
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        when {
            // Latauspalkki
            viewModel.productionChartState.value.loading -> CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )

            // Näytä sisältö
            else -> Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Piirrä kaavio
                ProductionChart7Days(
                    viewModel.productionStatsData,
                    goToProduction
                )
            }
        }
    }
}

@Composable
fun ConsumptionPanel7Days(
    goToConsumption: () -> Unit
) {
    val viewModel: ConsumptionViewModel = viewModel()
    // Alusta nykyinen aikaväli tilamuuttuja
    val currentTimeInterval by remember { mutableStateOf(TimeInterval.MAIN) }

    // Määritä nykyisen viikon ensimmäinen päivä
    val currentWeekStartDate by remember { mutableStateOf(LocalDate.now()) }
    // Päivitä kulutustilastot ja lämpötilatilastot haettaessa dataa
    LaunchedEffect(key1 = currentWeekStartDate, key2 = Unit) {
        // Päivitä datan haku sen mukaan, mikä aikaväli on valittu
        if (currentTimeInterval == TimeInterval.MAIN) {
            viewModel.consumptionFetchData(TimeInterval.MAIN, currentWeekStartDate)
        }
    }
    // Näytön sisältö
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        when {
            // Latauspalkki
            viewModel.consumptionChartState.value.loading -> CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )

            // Näytä sisältö
            else -> Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Piirrä kaavio
                ConsumptionChart7Days(
                    viewModel.consumptionStatsData,
                    viewModel.temperatureStatsData,
                    goToConsumption
                )
            }
        }
    }
}