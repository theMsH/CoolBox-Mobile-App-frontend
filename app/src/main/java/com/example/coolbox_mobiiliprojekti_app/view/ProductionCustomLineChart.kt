package com.example.coolbox_mobiiliprojekti_app.view

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coolbox_mobiiliprojekti_app.model.rememberMarker
import com.example.coolbox_mobiiliprojekti_app.ui.theme.CoolBoxmobiiliprojektiAppTheme
import com.example.coolbox_mobiiliprojekti_app.ui.theme.PanelColor
import com.example.coolbox_mobiiliprojekti_app.ui.theme.TextsLightColor
import com.example.coolbox_mobiiliprojekti_app.viewmodel.ProductionViewModel
import com.example.datachartexample2.tests.test3.formatToDateToDayOfWeek
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
fun ProductionCustomChart(
    productionStatsData: Map<String, Float?>?,
    gotoProduction: () -> Unit
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

    // Käynnistetään effect, joka reagoi productionStatsDatan muutoksiin
    LaunchedEffect(key1 = productionStatsData) {
        viewModel.productionStatsData?.let { productionStatsData ->
            // Yritetään suorittaa transaktio modelProducerilla
            modelProducer.tryRunTransaction {
                // Haetaan datan avaimet ja arvot listoiksi
                val dates = productionStatsData.keys.toList()
                val productions = productionStatsData.values.toList()

                // Tulostetaan dataa debug-tarkoituksissa
                Log.d("Dorian", "TUOTTOPÄIVÄT: dates $dates   productions $productions")

                // Muotoillaan päivämäärät päivän nimiksi
                val datesFormatted = formatToDateToDayOfWeek(dates)
                Log.d("Dorian", "dates $datesFormatted  productions $productions")

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

    // Luodaan teema
    CoolBoxmobiiliprojektiAppTheme {
        // Pinta, joka kattaa koko näytön leveyden
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background
        ) {
            // Sarake, joka täyttää koko leveyden
            Column(modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { gotoProduction() })) {
                // Kortti, joka toimii paneelina
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.Center),
                    colors = CardColors(
                        containerColor = PanelColor,
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
                        text = "Total Production"
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