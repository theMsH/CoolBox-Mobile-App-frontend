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
import com.example.coolbox_mobiiliprojekti_app.ui.theme.ProductionLineColorPanel
import com.example.coolbox_mobiiliprojekti_app.ui.theme.ProductionLineColorPanelDark
import com.example.coolbox_mobiiliprojekti_app.viewmodel.ProductionViewModel
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.rememberAxisLabelComponent
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.CartesianChartHost
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineSpec
import com.patrykandpatrick.vico.compose.chart.layout.fullWidth
import com.patrykandpatrick.vico.compose.chart.rememberCartesianChart
import com.patrykandpatrick.vico.compose.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.component.shape.dashedShape
import com.patrykandpatrick.vico.compose.component.shape.shader.color
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.chart.layout.HorizontalLayout
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.model.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.model.ExtraStore
import com.patrykandpatrick.vico.core.model.lineSeries
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import java.util.Locale

@Composable
fun ProductionChart7Days(
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
            chartValues.model.extraStore[labelListKey][x.toInt()]
        }

    val systemLocale = Locale.getDefault()

    // Käynnistetään effect, joka reagoi productionStatsDatan muutoksiin
    LaunchedEffect(key1 = productionStatsData) {
        viewModel.productionStatsData?.let { productionStatsData ->
            // Yritetään suorittaa transaktio modelProducerilla
            modelProducer.tryRunTransaction {
                // Haetaan datan avaimet ja arvot listoiksi
                val dates = productionStatsData.keys.toList()

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

                val productions = productionStatsData.values.toList()

                // Luodaan sarakkeet tuottodatalle
                lineSeries {
                    series(productions)
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
                    text = stringResource(R.string.total_production_kwh),
                    textAlign = TextAlign.Center
                )
                // CartesianChartHost, joka sisältää chartin
                CartesianChartHost(
                    chart = rememberCartesianChart(
                        rememberLineCartesianLayer(
                            lines = listOf(
                                rememberLineSpec(
                                    shader = DynamicShaders.color(
                                        if (isSystemInDarkTheme()) ProductionLineColorPanelDark else ProductionLineColorPanel
                                    )
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
                            valueFormatter = valueFormatterString,
                            itemPlacer = remember {
                                AxisItemPlacer.Horizontal.default(
                                    spacing = 1,
                                    addExtremeLabelPadding = true
                                )
                            },
                            guideline = null,
                        ),
                    ),
                    marker = rememberMarker(),
                    modelProducer = modelProducer,
                    horizontalLayout = HorizontalLayout.fullWidth(),
                )
                TextButton(
                    onClick = { gotoProduction() },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = stringResource(R.string.more), color = MaterialTheme.colorScheme.inverseOnSurface)
                }
            } // Kortti loppuu

        }
    }
}
