package com.example.coolbox_mobiiliprojekti_app.view

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coolbox_mobiiliprojekti_app.R
import com.example.coolbox_mobiiliprojekti_app.model.rememberMarker
import com.example.coolbox_mobiiliprojekti_app.ui.theme.CoolBoxmobiiliprojektiAppTheme
import com.example.coolbox_mobiiliprojekti_app.ui.theme.ScreenPanelColor
import com.example.coolbox_mobiiliprojekti_app.ui.theme.TextsLightColor
import com.example.coolbox_mobiiliprojekti_app.viewmodel.ProductionViewModel
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.CartesianChartHost
import com.patrykandpatrick.vico.compose.chart.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineSpec
import com.patrykandpatrick.vico.compose.chart.layout.fullWidth
import com.patrykandpatrick.vico.compose.chart.rememberCartesianChart
import com.patrykandpatrick.vico.compose.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.component.shape.shader.color
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.chart.layout.HorizontalLayout
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.model.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.model.ExtraStore
import com.patrykandpatrick.vico.core.model.lineSeries
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter

@Composable
fun ProductionChart(
    productionStatsData: Map<String, Float?>?,
    goToProduction: () -> Unit = {},
    currentProductionType: ProductionTypeInterval,
    isLandscape: Boolean
) {

    // Haetaan viewmodel
    val viewModel: ProductionViewModel = viewModel()

    // Luodaan modelProducer, joka vastaa chartin datan käsittelystä
    val modelProducer = remember { CartesianChartModelProducer.build() }

    // Avain labelien tallentamiseen extraStoreen
    val labelListKey = remember { ExtraStore.Key<List<String>>() }

    // Määritetään akselin arvojen muotoilu
    val valueFormatterString =
        AxisValueFormatter<AxisPosition.Horizontal.Bottom> { x, chartValues, _ ->
            chartValues.model.extraStore[labelListKey]?.get(x.toInt()) ?: ""
        }

    //

    // Käynnistetään effect, joka reagoi productionStatsDatan muutoksiin
    LaunchedEffect(
        key1 = currentProductionType,
        key2 = viewModel.windStatsData,
        key3 = viewModel.productionStatsData
    ) {
        when (currentProductionType) {
            ProductionTypeInterval.Total -> {
                viewModel.productionStatsData?.let { productionStatsData ->
                    // Yritetään suorittaa transaktio modelProducerilla
                    modelProducer.tryRunTransaction {
                        // Haetaan datan avaimet ja arvot listoiksi
                        val dates = productionStatsData.keys.toList()

                        val productions = productionStatsData.values.toList()

                        // Muotoillaan päivämäärät päivän nimiksi
                        val datesFormatted = formatToDateToDayOfWeek(dates)

                        // Luodaan sarakkeet tuottodatalle
                        lineSeries {
                            series(productions)
                        }

                        // Päivitetään extras lisäämällä päivämäärät
                        updateExtras {
                            it[labelListKey] = datesFormatted
                        }
                    }
                }
            }

            ProductionTypeInterval.Wind -> {
                viewModel.windStatsData?.let { windStatsData ->
                    // Yritetään suorittaa transaktio modelProducerilla
                    modelProducer.tryRunTransaction {
                        // Haetaan datan avaimet ja arvot listoiksi
                        val dates = windStatsData.keys.toList()

                        val productions = windStatsData.values.toList()

                        // Muotoillaan päivämäärät päivän nimiksi
                        val datesFormatted = formatToDateToDayOfWeek(dates)

                        // Luodaan sarakkeet tuottodatalle
                        lineSeries {
                            series(productions)
                        }

                        // Päivitetään extras lisäämällä päivämäärät
                        updateExtras {
                            it[labelListKey] = datesFormatted
                        }
                    }
                }
            }

            ProductionTypeInterval.Solar -> {
                viewModel.solarStatsData?.let { solarStatsData ->
                    // Yritetään suorittaa transaktio modelProducerilla
                    modelProducer.tryRunTransaction {
                        // Haetaan datan avaimet ja arvot listoiksi
                        val dates = solarStatsData.keys.toList()

                        val productions = solarStatsData.values.toList()

                        // Muotoillaan päivämäärät päivän nimiksi
                        val datesFormatted = formatToDateToDayOfWeek(dates)

                        // Luodaan sarakkeet tuottodatalle
                        lineSeries {
                            series(productions)
                        }

                        // Päivitetään extras lisäämällä päivämäärät
                        updateExtras {
                            it[labelListKey] = datesFormatted
                        }
                    }
                }
            }

        }
    }

    // Luodaan teema
    CoolBoxmobiiliprojektiAppTheme {
        // Pinta, joka kattaa koko näytön leveyden
        Surface(
            modifier = Modifier
                .fillMaxWidth(
                    if (isLandscape) {
                        0.5f
                    } else {
                        1f
                    }
                ),
            color = MaterialTheme.colorScheme.background
        ) {
            // Sarake, joka täyttää koko leveyden
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { goToProduction() })
            ) {
                // Kortti, joka toimii paneelina
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.Center),
                    colors = CardColors(
                        containerColor = ScreenPanelColor,
                        contentColor = TextsLightColor,
                        disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        disabledContentColor = MaterialTheme.colorScheme.secondary
                    )
                ) {

                    // Teksti paneelin keskelle
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 20.dp),
                        fontSize = 20.sp,
                        text = when (currentProductionType) {
                            ProductionTypeInterval.Wind -> stringResource(R.string.pro_graph_title_wind)
                            ProductionTypeInterval.Solar -> stringResource(R.string.pro_graph_title_solar)
                            ProductionTypeInterval.Total -> stringResource(R.string.pro_graph_title_total)
                        },
                        color = Color.Black
                    )

                    // CartesianChartHost, joka sisältää chartin
                    CartesianChartHost(
                        chart =
                        rememberCartesianChart(
                            rememberColumnCartesianLayer(
                                columns = listOf(
                                    rememberLineComponent(
                                        color = Color.Blue,
                                        thickness = 8.dp, // Adjust as needed
                                    ),
                                    rememberLineComponent(
                                        color = Color.Blue,
                                        thickness = 8.dp, // Adjust as needed
                                    )
                                ),
                            ),
                            rememberLineCartesianLayer(
                                lines = listOf(
                                    rememberLineSpec(
                                        shader = DynamicShaders.color(Color.Red)
                                    ),
                                    rememberLineSpec(
                                        shader = DynamicShaders.color(Color.Red)
                                    )
                                ),
                            ),
                            startAxis = rememberStartAxis(),
                            bottomAxis =
                            rememberBottomAxis(
                                valueFormatter = valueFormatterString,
                                itemPlacer =
                                remember {
                                    AxisItemPlacer.Horizontal.default(
                                        spacing = 1,
                                        addExtremeLabelPadding = true
                                    )
                                },
                            ),
                        ),
                        marker = rememberMarker(),
                        modelProducer = modelProducer,
                        horizontalLayout = HorizontalLayout.fullWidth(),
                    )
                } // Paneeli loppuu
            } // Sarake loppuu
        }
    }
}