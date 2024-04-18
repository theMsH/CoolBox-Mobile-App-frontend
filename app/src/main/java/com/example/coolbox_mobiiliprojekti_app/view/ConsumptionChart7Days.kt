package com.example.coolbox_mobiiliprojekti_app.view

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coolbox_mobiiliprojekti_app.R
import com.example.coolbox_mobiiliprojekti_app.ui.theme.CoolBoxmobiiliprojektiAppTheme
import com.example.coolbox_mobiiliprojekti_app.ui.theme.GraphKwhColorPanel
import com.example.coolbox_mobiiliprojekti_app.ui.theme.GraphKwhColorPanelDark
import com.example.coolbox_mobiiliprojekti_app.viewmodel.ConsumptionViewModel
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.rememberAxisLabelComponent
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.CartesianChartHost
import com.patrykandpatrick.vico.compose.chart.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.chart.layout.fullWidth
import com.patrykandpatrick.vico.compose.chart.rememberCartesianChart
import com.patrykandpatrick.vico.compose.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.component.shape.dashedShape
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.chart.layout.HorizontalLayout
import com.patrykandpatrick.vico.core.model.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.model.ExtraStore
import com.patrykandpatrick.vico.core.model.columnSeries
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.component.shape.Shapes
import java.util.Locale

@Composable
fun ConsumptionChart7Days(
    consumptionStatsData: Map<String, Float?>?,
    goToConsumption: () -> Unit = {}
) {

    // Haetaan viewmodel
    val viewModel: ConsumptionViewModel = viewModel()

    // Luodaan modelProducer, joka vastaa chartin datan käsittelystä
    val modelProducer = remember { CartesianChartModelProducer.build() }

    // Avain labelien tallentamiseen extraStoreen
    val labelListKey = remember { ExtraStore.Key<List<String>>() }

    // Määritetään akselin arvojen muotoilu
    val valueFormatterString =
        AxisValueFormatter<AxisPosition.Horizontal.Bottom> { x, chartValues, _ ->
            chartValues.model.extraStore[labelListKey][x.toInt()]
        }

    val systemLocale = Locale.getDefault()

    // Käynnistetään effect, joka reagoi consumptionStatsData:n ja temperatureStatsData:n muutoksiin
    LaunchedEffect(key1 = consumptionStatsData) {
        viewModel.consumptionStatsData?.let { consumptionStatsData ->
            // Yritetään suorittaa transaktio modelProducerilla
            modelProducer.tryRunTransaction {
                // Haetaan datan avaimet ja arvot listoiksi
                val dates = consumptionStatsData.keys.toList()

                val splittedDates = dates.map { date ->
                    val parts = date.split("-")
                    val month = parts[1].toInt().toString()
                    val day = parts[2].toInt().toString()
                    var formattedDate = "$day/$month"
                    if (systemLocale.language == "fi") {
                        formattedDate = "$day.$month."
                    }
                    formattedDate
                }.toList()

                val consumptions = consumptionStatsData.values.toList()

                // Luodaan sarakkeet kulutusdatalle
                columnSeries {
                    series(consumptions)
                }
                // Päivitetään extras lisäämällä päivämäärät
                updateExtras {
                    it[labelListKey] = splittedDates
                }
            }
        }
    }

    // Luodaan teema
    CoolBoxmobiiliprojektiAppTheme {
        // Pinta, joka kattaa koko näytön leveyden
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp, start = 3.dp, end = 8.dp),
            color = MaterialTheme.colorScheme.secondary
        ) {
            // Kortti, joka toimii paneelina
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
            ) {
                // Teksti paneelin keskelle
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 20.dp),
                    fontSize = 20.sp,
                    text = stringResource(R.string.total_consumption_kwh),
                    textAlign = TextAlign.Center
                )
                // CartesianChartHost, joka sisältää chartin
                CartesianChartHost(
                    chart =
                    rememberCartesianChart(
                        rememberColumnCartesianLayer(
                            columns = listOf(
                                rememberLineComponent(
                                    color = if (isSystemInDarkTheme()) GraphKwhColorPanelDark else GraphKwhColorPanel,
                                    thickness = 8.dp, // Adjust as needed
                                )
                            ),
                        ),
                        startAxis = rememberStartAxis(
                            label = rememberAxisLabelComponent(
                                color = MaterialTheme.colorScheme.onSecondary
                            ),
                            axis = rememberLineComponent(
                                color = MaterialTheme.colorScheme.onSecondary
                            ),
                            guideline = rememberLineComponent(
                                color = MaterialTheme.colorScheme.onSecondary,
                                shape = remember {
                                    Shapes.dashedShape(
                                        shape = Shapes.rectShape,
                                        dashLength = 3.dp,
                                        gapLength = 3.dp,
                                    )
                                },
                            )
                        ),
                        bottomAxis = rememberBottomAxis(
                            label = rememberAxisLabelComponent(
                                color = MaterialTheme.colorScheme.onSecondary
                            ),
                            axis = rememberLineComponent(
                                color = MaterialTheme.colorScheme.onSecondary
                            ),
                            guideline = null,
                            valueFormatter = valueFormatterString,
                            itemPlacer = remember {
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
                TextButton(
                    onClick = { goToConsumption() },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = stringResource(R.string.more), color = MaterialTheme.colorScheme.inverseOnSurface)
                }

            } // Paneeli loppuu

        }
    }
}
