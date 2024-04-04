package com.example.coolbox_mobiiliprojekti_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coolbox_mobiiliprojekti_app.ui.theme.CoolBoxmobiiliprojektiAppTheme
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.CartesianChartHost
import com.patrykandpatrick.vico.compose.chart.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.chart.rememberCartesianChart
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.model.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.model.ExtraStore
import com.patrykandpatrick.vico.core.model.columnSeries
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// Jos haluat testaa tätä, käy vaihtamassa AndroidManifest.xml seuraava arvo:
// android:name=".MainActivity" --> android:name=".VicoChartEsimerkkiMainActivity"
// Mutta muista rollbackkaa muutos ennenkuin pushaat mitään, niin vältytään hämmennyksiltä

class VicoChartEsimerkkiMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            // Tämä pitäisi oikeasti tehdä viewmodeliin
            val modelProducer = remember { CartesianChartModelProducer.build() }

            ////////////////////// DATES ESIMERKKI /////////////////////////

            // Tämä on data, jota halutaan näyttää chartissa. Tämäkin oikeasti luodaan viewmodelissa
            // Tämä on ikäänkuin dictionaryn {avain:arvo}
            val datasetDates = mapOf(
                LocalDate.parse("2024-03-25") to 10,
                LocalDate.parse("2024-03-26") to 32,
                LocalDate.parse("2024-03-27") to 12.1,
                LocalDate.parse("2024-03-28") to 41.1,
                LocalDate.parse("2024-03-29") to 0,
                LocalDate.parse("2024-03-30") to 0,
                LocalDate.parse("2024-03-31") to 0
            )

            // Tallennetaan datet (epoch) "label" eli palkinnimi ExtraStore avainlistaan.
            val xToDateMapKey = ExtraStore.Key<Map<Float, LocalDate>>()

            // Tätä käytetään launched effectissä kun luodaan lineSeries.
            val xToDates = datasetDates.keys.associateBy { it.toEpochDay().toFloat() }

            // Formatoidaan date haluttuun muotoon epoch aikaleimasta.
            val dateTimeFormatter = DateTimeFormatter.ofPattern("d MMM")

            // Formatoidaan kolumnien nimet päivämääriksi, käy asettamassa tämä muuttuja chartin valueFormatter arvoon.
            val valueFormatterDate = AxisValueFormatter<AxisPosition.Horizontal.Bottom> { x, chartValues, _ ->
                (chartValues.model.extraStore[xToDateMapKey][x] ?: LocalDate.ofEpochDay(x.toLong()))
                    .format(dateTimeFormatter)
            }

            ////////////////////// STRING ESIMERKKI /////////////////////////

            // Tämä on data, jota halutaan näyttää chartissa. Tämäkin oikeasti luodaan viewmodelissa
            // Tämä on ikäänkuin dictionaryn {avain:arvo}
            val datasetStrings = mapOf(
                "25.3" to 10,
                "26.3" to 32,
                "27.3" to 12.1,
                "28.3" to 41.1,
                "29.3" to 0,
                "30.3" to 0,
                "31.3" to 0
            )

            // Tallennetaan stringit "label" eli palkinnimi ExtraStore avainlistaan.
            val labelListKey = ExtraStore.Key<List<String>>()

            // Formatoidaan kolumnien nimet string. Käy asettamassa tämä muuttuja chartin valueFormatter arvoon.
            val valueFormatterString = AxisValueFormatter<AxisPosition.Horizontal.Bottom> { x, chartValues, _ ->
                chartValues.model.extraStore[labelListKey][x.toInt()]
            }

            // Tämä ilmeisesti luo columnit charttiin. Jos niitä ei ole, charttia ei piirretä.
            // Tämä luultavasti tullaan muuttamaan siten, että Unitin tilalle tulee jokin viewmodelin muuttuja.
            // Esim. vm.chartState.value.dataset (kun datasetti muuttuu, suoritetaan launched effect.)
            LaunchedEffect(Unit) {
                modelProducer.tryRunTransaction {

                    // lineSeries on columnien y-arvot eli se "palkin korkeus".
                    // Linechartissakin on tavallaan "palkit" joita ei piirretä. Vaihda columnSeries, jos columnit käytössä.
                    // Muista vaihtaa myös CartesianChartHost(chart = rememberColumnCartesianLayer())

                    // updateExtras on columnien (eli bottom eli X-akselille tulevat) nimet


                    // JOS dateset STRINGS on käytössä:
                    columnSeries { series(datasetStrings.values) }
                    updateExtras { it[labelListKey] = datasetStrings.keys.toList() }

                    // JOS dateset DATE on käytössä.
                    //lineSeries { series(xToDates.keys, datasetDates.values) }
                    //updateExtras { it[xToDateMapKey] = xToDates }
                }
            }




            CoolBoxmobiiliprojektiAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        TextButton(
                            contentPadding = PaddingValues(0.dp),
                            shape = RectangleShape,
                            onClick = { /* TODO Open Total Consumption */ }
                        ) {
                            // Card toimii paneelina.
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                                    .wrapContentSize(Alignment.Center)
                                ,
                                colors = CardColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
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
                                    text = "Total Consumption"
                                )
                                // Tässä luodaan chart. Chart tarvitsee vähintään chart ja modelproducer muuttujat.
                                CartesianChartHost(
                                    modifier = Modifier
                                        .padding(20.dp)
                                    ,
                                    chart = rememberCartesianChart(
                                        rememberColumnCartesianLayer(),
                                        startAxis = rememberStartAxis(),
                                        // Tässä pitää ottaa valueFormatter käyttöön x akselille.
                                        bottomAxis = rememberBottomAxis( valueFormatter = valueFormatterString )

                                    ),
                                    modelProducer = modelProducer
                                )
                            }
                        } // End of panel

                    } // End of column
                }
            }
        }
    }

}
