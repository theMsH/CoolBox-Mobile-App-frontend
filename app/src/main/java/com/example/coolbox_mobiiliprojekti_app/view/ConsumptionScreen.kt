package com.example.datachartexample2.model.test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coolbox_mobiiliprojekti_app.viewmodel.ConsumptionChartViewModel
import kotlinx.coroutines.Job
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.Locale

// Aikavälin luetelman määrittely
enum class TimeInterval {
    DAYS, HOURS, WEEKS
}

// Vaatii API-tason tarkistuksen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsumptionScreen(
    goBack: () -> Boolean,
    onMenuClick: () -> Job
) {

    val context = LocalContext.current
    val viewModel: ConsumptionChartViewModel = viewModel()

    // Haetaan kulutustilastot kun koostettava käynnistetään ensimmäisen kerran
    LaunchedEffect(Unit) {
        viewModel.fetchDailyConsumptionStats(LocalDate.now())
        viewModel.fetchHourlyTemperaturesData(LocalDate.now())

    }
    var currentTimeInterval by remember { mutableStateOf(TimeInterval.DAYS) }

    // Havaitaan kulutustilastot viewmodelista
    var consumptionStatsData = viewModel.consumptionStatsData
    var temperatureStatsData = viewModel.temperatureStatsData

    fun LocalDate.startOfWeek(): LocalDate {
        return this.minusDays(this.dayOfWeek.value.toLong() - 1)
    }

    fun LocalDate.endOfWeek(): LocalDate {
        return this.startOfWeek().plusDays(6)
    }

    // Määritä nykyisen viikon aikaväli
    var currentWeekStartDate by remember { mutableStateOf(LocalDate.now().startOfWeek()) }
    var currentWeekEndDate = currentWeekStartDate.plusDays(6)

    // Päivitä kulutustilastot ja lämpötilatilastot haettaessa dataa
    LaunchedEffect(currentWeekStartDate) {
        // Tyhjennä aiemmat tiedot haettaessa uutta dataa
        consumptionStatsData = null
        temperatureStatsData = null
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
                navigationIcon = {
                    IconButton(onClick = { goBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowLeft,
                            contentDescription = "Back"
                        )
                    }
                },
                title = { Text(text = "Production") },
                actions = {
                    IconButton(onClick = { onMenuClick() }) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                    }
                })
        }
    ) { innerPadding ->
        // Näytön sisältö
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Näytä kaavio
            viewModel.ConsumptionColumnChart(
                consumptionStatsData,
            )
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Lisää rivi nuolilla oikealle ja vasemmalle
                Row(
                    modifier = Modifier
                        .padding(0.dp, 325.dp, 0.dp, 0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Nuoli vasemmalle
                    IconButton(onClick = {
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
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }

                    // Tilaa välissä
                    Spacer(modifier = Modifier.weight(0.5f))

                    // Päivämäärä kohta
                    Text(
                        text = when (currentTimeInterval) {
                            TimeInterval.DAYS -> "${currentWeekStartDate.dayOfMonth}/${currentWeekStartDate.monthValue} - ${currentWeekEndDate.dayOfMonth}/${currentWeekEndDate.monthValue}"
                            TimeInterval.HOURS -> currentWeekStartDate.dayOfWeek.getDisplayName(
                                TextStyle.FULL, Locale.US
                            ) +
                                    " (${currentWeekStartDate.dayOfMonth}/${currentWeekStartDate.monthValue})"

                            TimeInterval.WEEKS -> {
                                val weekNumber = currentWeekStartDate.get(
                                    WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()
                                )
                                "Week $weekNumber"
                            }
                        },
                        fontSize = 30.sp, // Aseta tekstin koko 16 sp (scaled pixels)
                    )
                    Spacer(modifier = Modifier.weight(0.5f))
                    // Nuoli oikealle
                    IconButton(onClick = {


                        // Haetaan data valitun aikavälin perusteella
                        when (currentTimeInterval) {
                            TimeInterval.DAYS -> {
                                // Siirry seuraavan viikon alkuun
                                currentWeekStartDate =
                                    currentWeekStartDate.plusWeeks(1).startOfWeek()
                                viewModel.fetchDailyConsumptionStats(currentWeekStartDate)
                            }

                            TimeInterval.HOURS -> {
                                // Siirry seuraavan viikon alkuun
                                currentWeekStartDate = currentWeekStartDate.plusDays(1)
                                viewModel.fetchHourlyConsumptionsData(currentWeekStartDate)
                            }

                            TimeInterval.WEEKS -> {
                                // Siirry seuraavan viikon alkuun
                                currentWeekStartDate =
                                    currentWeekStartDate.plusWeeks(1).startOfWeek()
                                viewModel.fetchWeeklyConsumptionsData(currentWeekStartDate)
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null
                        )
                    }

                }
                Row(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                ) {
                    // Päivämäärä kohta
                    Text(
                        text = "Total: ${
                            String.format(
                                Locale.US,
                                "%.2f",
                                consumptionStatsData?.values?.sum() ?: 0f
                            )
                        } kwh",
                        fontSize = 30.sp, // Aseta tekstin koko 16 sp (scaled pixels)
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                ) {

                    // Päivämäärä kohta
                    Text(
                        text = "Avg: ${
                            String.format(
                                Locale.US,
                                "%.2f",
                                consumptionStatsData?.values?.average() ?: 0f
                            )
                        } kwh",
                        fontSize = 30.sp, // Aseta tekstin koko 16 sp (scaled pixels)
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                ) {
                    if (currentTimeInterval == TimeInterval.HOURS) {
                        Text(
                            text = "Avg temerature: ${
                                String.format(
                                    Locale.US,
                                    "%.1f",
                                    temperatureStatsData?.values?.average() ?: 0f
                                )
                            } C",
                            fontSize = 30.sp, // Aseta tekstin koko 16 sp (scaled pixels)
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Row(
                    modifier = Modifier
                        .background(color = Color.Blue)
                        .padding(vertical = 20.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
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
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        Text(text = "Week")
                    }

                    Button(
                        onClick = {
                            currentWeekStartDate = LocalDate.now().startOfWeek()
                            viewModel.fetchDailyConsumptionStats(currentWeekStartDate)
                            currentTimeInterval = TimeInterval.DAYS
                        },
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        Text(text = "Days")
                    }


                    Button(
                        onClick = {
                            currentWeekStartDate = LocalDate.now()
                            viewModel.fetchHourlyConsumptionsData(currentWeekStartDate)
                            currentTimeInterval = TimeInterval.HOURS
                        },
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        Text(text = "Hours")
                    }

                }
            }
        }
    }
}