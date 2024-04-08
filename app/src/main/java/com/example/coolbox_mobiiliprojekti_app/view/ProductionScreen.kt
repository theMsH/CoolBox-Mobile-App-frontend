package com.example.coolbox_mobiiliprojekti_app.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.coolbox_mobiiliprojekti_app.viewmodel.ProductionViewModel
import kotlinx.coroutines.Job
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.Locale


// Aikavälin luetelman määrittely
enum class TimeInterval {
    DAYS, HOURS, WEEKS, MONTHS, MAIN
}

fun LocalDate.startOfWeek(): LocalDate {
    return this.minusDays(this.dayOfWeek.value.toLong() - 1)
}

fun LocalDate.endOfWeek(): LocalDate {
    return this.startOfWeek().plusDays(6)
}

// Funktio muuntaa päivämäärät tekstimuotoon näytettäväksi viikonpäivän lyhyellä nimellä (esim. "Ma", "Ti")
fun formatToDateToDayOfWeek(dateList: List<String>): List<String> {
    return dateList.map { dateString ->
        val datePattern = Regex("\\d{4}-\\d{2}-\\d{2}")
        if (dateString.matches(datePattern)) {
            // Päivämäärä on muodossa "YYYY-MM-DD"
            val date = LocalDate.parse(dateString)
            // Päivän nimi lyhyellä nimellä (esim. "Ma", "Ti")
            val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
            // Ota talteen vain kaksi ensimmäistä merkkiä päivän nimestä
            dayOfWeek.take(2)
        } else {
            // Päivämäärä ei ole odotetussa muodossa, oletetaan että se on kuukauden päivä
            dateString
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductionScreen(
    onMenuClick: () -> Job,
    goBack: () -> Unit
) {
    val viewModel: ProductionViewModel = viewModel()
    // Alusta nykyinen aikaväli tilamuuttuja
    var currentTimeInterval by remember { mutableStateOf(TimeInterval.DAYS) }

    // Määritä nykyisen viikon ensimmäinen päivä
    var currentWeekStartDate by remember { mutableStateOf(LocalDate.now().startOfWeek()) }
    var currentWeekEndDate = currentWeekStartDate.plusDays(6)

    // Päivitä kulutustilastot ja lämpötilatilastot haettaessa dataa
    LaunchedEffect(key1 = currentWeekStartDate, key2 = Unit) {
        // Päivitä datan haku sen mukaan, mikä aikaväli on valittu
        when (currentTimeInterval) {
            TimeInterval.HOURS -> {
                viewModel.productionFetchData(TimeInterval.HOURS, currentWeekStartDate)
            }
            TimeInterval.DAYS -> {
                viewModel.productionFetchData(TimeInterval.DAYS, currentWeekStartDate)
            }
            TimeInterval.WEEKS -> {
                viewModel.productionFetchData(TimeInterval.WEEKS, currentWeekStartDate)
            }
            TimeInterval.MONTHS -> {
                viewModel.productionFetchData(TimeInterval.MONTHS, currentWeekStartDate)
            }

            TimeInterval.MAIN -> {
                viewModel.productionFetchData(TimeInterval.MAIN, currentWeekStartDate)
            }
        }
    }

    // Määritä näytön sisältö
    Scaffold(
        topBar = {
            // Yläpalkki
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                // Navigointinappi (takaisin)
                navigationIcon = {
                    IconButton(onClick = { goBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                // Otsikko
                title = { Text(text = "Production") },
                // Toiminnot
                actions = {
                    IconButton(onClick = { onMenuClick() }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Menu"
                        )
                    }
                }
            )
        },
        bottomBar = {
            // Alapalkki
            BottomAppBar(
                containerColor = Color.Blue
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Kuukausi-nappi
                    Button(
                        onClick = {
                            // Lisää logiikka kuukausidataan siirtymiseen
                            val currentMonthStartDate = LocalDate.now().withDayOfMonth(1)
                            viewModel.productionFetchData(TimeInterval.MONTHS, currentMonthStartDate)
                            currentTimeInterval = TimeInterval.MONTHS
                        }
                    ) {
                        Text(text = "Month")
                    }
                    // Viikko-nappi
                    Button(
                        onClick = {
                            // Hae viikkodata
                            val currentWeekNumber = LocalDate.now()
                                .get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear())
                            val currentYear = LocalDate.now().year

                            val firstDayOfWeek = LocalDate.ofYearDay(currentYear, 1)
                                .with(
                                    WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear(),
                                    currentWeekNumber.toLong()
                                )
                                .with(DayOfWeek.MONDAY)

                            currentWeekStartDate = firstDayOfWeek
                            currentWeekEndDate = firstDayOfWeek.plusDays(6)

                            viewModel.productionFetchData(TimeInterval.WEEKS, currentWeekStartDate)

                            currentTimeInterval = TimeInterval.WEEKS

                        }
                    ) {
                        Text(text = "Week")
                    }

                    // Päivä-nappi
                    Button(
                        onClick = {
                            // Hae päivädata
                            currentWeekStartDate = LocalDate.now().startOfWeek()
                            viewModel.productionFetchData(TimeInterval.DAYS, currentWeekStartDate)
                            currentTimeInterval = TimeInterval.DAYS
                        }
                    ) {
                        Text(text = "Days")
                    }

                    // Tunti-nappi
                    Button(
                        onClick = {
                            // Hae tuntidata
                            currentWeekStartDate = LocalDate.now()
                            viewModel.productionFetchData(TimeInterval.HOURS, currentWeekStartDate)
                            currentTimeInterval = TimeInterval.HOURS
                        }
                    ) {
                        Text(text = "Hours")
                    }
                }
            }
        }
    ) { innerPaddings ->
        // Näytön sisältö
        Box(
            modifier = Modifier
                .padding(innerPaddings)
                .fillMaxSize()
        ) {
            when {
                // Latauspalkki
                viewModel.productionChartState.value.loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )

                // Näytä sisältö
                else -> Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    ProductionChart(
                        viewModel.productionStatsData
                    )

                    // Piirrä nuolinapit ja niiden välissä oleva aikaväli
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Nuoli vasemmalle
                        IconButton(
                            onClick = {
                                when (currentTimeInterval) {
                                    TimeInterval.DAYS -> {
                                        // Siirry edellisen viikon alkuun
                                        currentWeekStartDate =
                                            currentWeekStartDate.minusWeeks(1).startOfWeek()

                                    }

                                    TimeInterval.HOURS -> {
                                        // Siirry edellisen päivän alkuun
                                        currentWeekStartDate = currentWeekStartDate.minusDays(1)

                                    }

                                    TimeInterval.WEEKS -> {
                                        // Siirry edellisen kuukauden alkuun
                                        currentWeekStartDate =
                                            currentWeekStartDate.minusMonths(1).startOfWeek()

                                    }
                                    TimeInterval.MONTHS -> {
                                        // Siirry edellisen vuoden alkuun
                                        currentWeekStartDate = currentWeekStartDate.minusYears(1)
                                    }

                                    TimeInterval.MAIN -> TODO()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null
                            )
                        }
                        Spacer(modifier = Modifier.weight(0.5f))

                        // Näytä päivämäärä
                        Text(
                            text = when (currentTimeInterval) {
                                TimeInterval.DAYS -> {
                                    "${currentWeekStartDate.dayOfMonth}/${currentWeekStartDate.monthValue} " +
                                            "- ${currentWeekEndDate.dayOfMonth}/${currentWeekEndDate.monthValue}"
                                }

                                TimeInterval.HOURS -> {
                                    currentWeekStartDate.dayOfWeek.getDisplayName(
                                        TextStyle.FULL, Locale.US
                                    ) + " (${currentWeekStartDate.dayOfMonth}/${currentWeekStartDate.monthValue})"
                                }

                                TimeInterval.WEEKS -> {
                                    "${currentWeekStartDate.month.name.toLowerCase().capitalize()} ${currentWeekStartDate.year}"
                                }
                                TimeInterval.MONTHS -> {
                                    "${currentWeekStartDate.year}"
                                }

                                TimeInterval.MAIN -> TODO()
                            },
                            fontSize = 30.sp
                        )
                        Spacer(modifier = Modifier.weight(0.5f))

                        // Nuoli oikealle
                        IconButton(
                            onClick = {
                                // Siirry seuraavan aikavälin alkuun
                                when (currentTimeInterval) {
                                    TimeInterval.DAYS -> {
                                        currentWeekStartDate =
                                            currentWeekStartDate.plusWeeks(1).startOfWeek()
                                    }

                                    TimeInterval.HOURS -> {
                                        currentWeekStartDate = currentWeekStartDate.plusDays(1)
                                    }

                                    TimeInterval.WEEKS -> {
                                        currentWeekStartDate =
                                            currentWeekStartDate.plusMonths(1).startOfWeek()
                                    }
                                    TimeInterval.MONTHS -> {
                                        currentWeekStartDate = currentWeekStartDate.plusYears(1)
                                    }

                                    TimeInterval.MAIN -> TODO()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null
                            )
                        }
                    }
                    Spacer(Modifier.height(16.dp))

                    // Näytä yhteenveto
                    Text(
                        modifier = Modifier
                            .padding(vertical = 16.dp),
                        text = "Total:  ${
                            String.format(
                                Locale.US,
                                "%.2f",
                                viewModel.productionStatsData?.values?.sum() ?: 0f
                            )
                        } kwh",
                        fontSize = 30.sp
                    )

                    // Näytä keskiarvo
                    Text(
                        modifier = Modifier
                            .padding(vertical = 16.dp),
                        text = "Avg:  ${
                            String.format(
                                Locale.US,
                                "%.2f",
                                viewModel.productionStatsData?.values?.average() ?: 0f
                            )
                        } kwh",
                        fontSize = 30.sp
                    )

                }
            }
        }
    }

}

