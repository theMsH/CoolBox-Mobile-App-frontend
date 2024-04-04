package com.example.coolbox_mobiiliprojekti_app.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.util.Locale
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coolbox_mobiiliprojekti_app.viewmodel.ConsumptionViewModel
import kotlinx.coroutines.Job
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.time.temporal.WeekFields


// Aikavälin luetelman määrittely
enum class TimeInterval {
    DAYS, HOURS, WEEKS
}

fun LocalDate.startOfWeek(): LocalDate {
    return this.minusDays(this.dayOfWeek.value.toLong() - 1)
}

fun LocalDate.endOfWeek(): LocalDate {
    return this.startOfWeek().plusDays(6)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsumptionScreen(
    goBack: () -> Boolean,
    onMenuClick: () -> Job
) {
    val viewModel: ConsumptionViewModel = viewModel()
    var currentTimeInterval by remember { mutableStateOf(TimeInterval.DAYS) }

    // Määritä nykyisen viikon aikaväli
    var currentWeekStartDate by remember { mutableStateOf(LocalDate.now().startOfWeek()) }
    var currentWeekEndDate = currentWeekStartDate.plusDays(6)

    // Päivitä kulutustilastot ja lämpötilatilastot haettaessa dataa
    LaunchedEffect(key1 = currentWeekStartDate, key2 = Unit) {
        when (currentTimeInterval) {
            TimeInterval.DAYS -> viewModel.fetchDailyConsumptionStats(currentWeekStartDate)
            TimeInterval.HOURS -> viewModel.fetchHourlyConsumptionsData(currentWeekStartDate)
            TimeInterval.WEEKS -> viewModel.fetchWeeklyConsumptionsData(currentWeekStartDate)
        }
    }

    // Määritä näytön sisältö
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = { goBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                title = { Text(text = "Consumption") },
                actions = {
                    IconButton(onClick = { onMenuClick() }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "Menu"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Blue
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
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

                            viewModel.fetchWeeklyConsumptionsData(currentWeekStartDate)
                            currentTimeInterval =
                                TimeInterval.WEEKS // Päivitä nykyinen aikaväli
                        },
                    ) {
                        Text(text = "Week")
                    }

                    Button(
                        onClick = {
                            currentWeekStartDate = LocalDate.now().startOfWeek()
                            viewModel.fetchDailyConsumptionStats(currentWeekStartDate)
                            currentTimeInterval = TimeInterval.DAYS
                        },
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Text(text = "Days")
                    }

                    Button(
                        onClick = {
                            currentWeekStartDate = LocalDate.now()
                            viewModel.fetchHourlyConsumptionsData(currentWeekStartDate)
                            currentTimeInterval = TimeInterval.HOURS
                        },
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
                // Loading Circle
                viewModel.chartState.value.loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )

                // Draw screen
                else -> Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Piirrä chart
                    ConsumptionColumnChart(
                        viewModel.consumptionStatsData
                    )

                    // Piirrä nuolinapit ja niiden väliin chartin aikaväli teksti
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Nuoli vasemmalle
                        IconButton(
                            onClick = {
                                when (currentTimeInterval) {
                                    TimeInterval.DAYS -> {
                                        // Siirry seuraavan viikon alkuun
                                        currentWeekStartDate =
                                            currentWeekStartDate.minusWeeks(1).startOfWeek()
                                        viewModel.fetchDailyConsumptionStats(currentWeekStartDate)
                                    }

                                    TimeInterval.HOURS -> {
                                        // Siirry seuraavan viikon alkuun
                                        currentWeekStartDate = currentWeekStartDate.minusDays(1)
                                        viewModel.fetchHourlyConsumptionsData(currentWeekStartDate)
                                    }

                                    TimeInterval.WEEKS -> {
                                        // Siirry seuraavan viikon alkuun
                                        currentWeekStartDate =
                                            currentWeekStartDate.minusWeeks(1).startOfWeek()
                                        viewModel.fetchWeeklyConsumptionsData(currentWeekStartDate)
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null
                            )
                        }
                        Spacer(modifier = Modifier.weight(0.5f))

                        // Päivämäärä kohta
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
                                    val weekNumber = currentWeekStartDate.get(
                                        WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()
                                    )
                                    "Week $weekNumber"
                                }
                            },
                            fontSize = 30.sp
                        )
                        Spacer(modifier = Modifier.weight(0.5f))

                        // Nuoli oikealle
                        IconButton(
                            onClick = {
                                // Haetaan data valitun aikavälin perusteella
                                when (currentTimeInterval) {
                                    TimeInterval.DAYS -> {
                                        // Siirry seuraavan viikon alkuun
                                        currentWeekStartDate =
                                            currentWeekStartDate.plusWeeks(1).startOfWeek()
                                    }

                                    TimeInterval.HOURS -> {
                                        // Siirry seuraavan viikon alkuun
                                        currentWeekStartDate = currentWeekStartDate.plusDays(1)
                                    }

                                    TimeInterval.WEEKS -> {
                                        // Siirry seuraavan viikon alkuun
                                        currentWeekStartDate =
                                            currentWeekStartDate.plusWeeks(1).startOfWeek()
                                    }
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

                    // TOTAL kohta
                    Text(
                        modifier = Modifier
                            .padding(vertical = 16.dp),
                        text = "Total:  ${
                            String.format(
                                Locale.US,
                                "%.2f",
                                viewModel.consumptionStatsData?.values?.sum() ?: 0f
                            )
                        } kwh",
                        fontSize = 30.sp
                    )

                    // AVG kohta
                    Text(
                        modifier = Modifier
                            .padding(vertical = 16.dp),
                        text = "Avg:  ${
                            String.format(
                                Locale.US,
                                "%.2f",
                                viewModel.consumptionStatsData?.values?.average() ?: 0f
                            )
                        } kwh",
                        fontSize = 30.sp
                    )

                    // Piirrä temperaturelle AVG jos käytössä
                    if (currentTimeInterval == TimeInterval.HOURS) {
                        Text(
                            modifier = Modifier
                                .padding(vertical = 16.dp),
                            text = "Avg temperature:  ${
                                String.format(
                                    Locale.US,
                                    "%.1f",
                                    viewModel.temperatureStatsData?.values?.average() ?: 0f
                                )
                            } °C",
                            fontSize = 30.sp, // Aseta tekstin koko 16 sp (scaled pixels)
                        )
                    }
                }
            }
        }
    }

}

