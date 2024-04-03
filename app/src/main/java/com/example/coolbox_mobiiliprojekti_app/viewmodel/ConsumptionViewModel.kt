package com.example.coolbox_mobiiliprojekti_app.viewmodel

import android.graphics.Paint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coolbox_mobiiliprojekti_app.api.consumptionApiService
import com.example.coolbox_mobiiliprojekti_app.model.ConsumptionStatsResponse
import com.example.coolbox_mobiiliprojekti_app.model.TemperatureStatsResponse
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ConsumptionChartViewModel : ViewModel() {

    // Määritä consumptionStatsData tilamuuttujaksi
    var consumptionStatsData by mutableStateOf<Map<String, Float>?>(null)
        private set

    var temperatureStatsData by mutableStateOf<Map<String, Float>?>(null)
        private set

    // Hae kulutustilastot viimeiseltä seitsemältä päivältä annetusta päivämäärästä
    fun fetchConsumptionStatsFromLastSevenDays(date: LocalDate) {
        val formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        viewModelScope.launch {
            try {
                val response =
                    consumptionApiService.getDailyConsumptionsDataFromLastSevenDaysByHours(
                        formattedDate
                    )
                handleConsumptionStatsResponse(response)
            } catch (e: Exception) {
                Log.d("fetchConsumptionStatsFromLastSevenDays Error", e.toString())
            }
        }
    }

    // Hae tunnittaiset kulutustiedot annetusta päivämäärästä
    fun fetchHourlyConsumptionsData(date: LocalDate) {
        val formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        viewModelScope.launch {
            try {
                val response = consumptionApiService.getHourlyConsumptionsData(formattedDate)
                handleConsumptionStatsResponse(response)
            } catch (e: Exception) {
                Log.d("fetchHourlyConsumptionsData Error", e.toString())
            }
        }
    }

    // Hae päivittäiset kulutustilastot annetusta päivämäärästä
    fun fetchDailyConsumptionStats(date: LocalDate) {
        val formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        viewModelScope.launch {
            try {

                val response = consumptionApiService.getDailyConsumptionsData(formattedDate)
                Log.d("Dorian", "fetchDailyConsumptionStats response $response")

                handleConsumptionStatsResponse(response)
            } catch (e: Exception) {
                Log.d("fetchDailyConsumptionStats Error", e.toString())
            }
        }
    }

    // Hae viikoittaiset kulutustiedot annetusta päivämäärästä
    fun fetchWeeklyConsumptionsData(date: LocalDate) {
        val formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        viewModelScope.launch {
            try {
                val response = consumptionApiService.getWeeklyConsumptionsData(formattedDate)
                handleConsumptionStatsResponse(response)
            } catch (e: Exception) {
                Log.d("fetchWeeklyConsumptionsData Error", e.toString())
            }
        }
    }

    // Hae tunnittaiset lämpötilatiedot annetusta päivämäärästä
    fun fetchHourlyTemperaturesData(date: LocalDate) {
        val formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        viewModelScope.launch {
            try {
                val response = consumptionApiService.getHourlyTemperaturesData(formattedDate)
                Log.d("Dorian", "fetchHourlyTemperaturesData response $response")
                handleTemperatureStatsResponse(response)
            } catch (e: Exception) {
                Log.d("fetchHourlyTemperaturesData Error", e.toString())
            }
        }
    }

    // Käsittele kulutustilastojen vastaus
    private fun handleConsumptionStatsResponse(response: ConsumptionStatsResponse) {
        if (response.data.all { it.date != null }) {
            consumptionStatsData = response.data.associate { it.date.toString() to it.totalKwh }
        } else if (response.data.all { it.hour != null }) {
            consumptionStatsData = response.data.associate { it.hour.toString() to it.totalKwh }
        } else if (response.data.all { it.day != null }) {
            consumptionStatsData = response.data.associate { it.day.toString() to it.totalKwh }
        } else {
            Log.d("Error", "handleConsumptionStatsResponse didn't receive the right data, suspicious of a name change :(")
        }
    }

    // Käsittele lämpötilatilastojen vastaus
    private fun handleTemperatureStatsResponse(response: TemperatureStatsResponse) {
        temperatureStatsData = response.data.associate { it.hour.toString() to it.temperature }
    }

    @Composable
    fun ConsumptionColumnChart(
        consumptionStatsData: Map<String, Float?>?,
        maxValue: Float = 10f,
    ) {

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp, 10.dp, 50.dp, 400.dp)
        ) {
            drawAxes(maxValue)
            drawData(consumptionStatsData, maxValue)

        }
    }

    fun DrawScope.drawAxes(
        maxValue: Float
    ) {
        // Piirrä pystyakseli (arvot)
        drawLine(
            color = Color.Black,
            start = Offset(50f, size.height),
            end = Offset(50f, 0f)
        )
        // Piirrä vaaka-akseli (päivämäärät)
        drawLine(
            color = Color.Black,
            start = Offset(50f, size.height),
            end = Offset(size.width, size.height)
        )
        // Piirrä arvomerkinnät
        for (i in 0..10) {
            val y = size.height - (i.toFloat() / 10f) * size.height
            drawLine(
                color = Color.Black,
                start = Offset(45f, y),
                end = Offset(50f, y)
            )
            drawIntoCanvas { canvas ->
                val paint = Paint().apply {
                    color = Color.Black.toArgb()
                    textSize = 12.sp.toPx()
                    textAlign = Paint.Align.RIGHT
                }
                val text = (maxValue * (i.toFloat() / 10f)).toString()
                canvas.nativeCanvas.drawText(
                    text,
                    40f, // Säädä tekstin X-asema
                    size.height - (i.toFloat() / 10f) * size.height + 6.dp.toPx(), // Säädä tekstin Y-asema
                    paint
                )
            }

        }
    }

    fun DrawScope.drawData(
        consumptionStatsData: Map<String, Float?>?,
        maxValue: Float
    ) {
        consumptionStatsData?.let { data ->
            val columnCount = data.size
            val columnWidth = (size.width - 50f) / columnCount
            var columnSpacing = 1.dp.toPx()
            var currentX = 50f + columnSpacing
            var drawDate = true // Lippu ilmaisemaan, pitäisikö päivämäärä piirtää

            for ((index, entry) in data.entries.withIndex()) {
                val (day, value) = entry
                val columnHeight = (value?.div(maxValue))?.times(size.height)
                val startY = size.height - columnHeight!!
                drawRect(
                    color = Color.Blue,
                    topLeft = Offset(currentX, startY),
                    size = Size(columnWidth - columnSpacing * 2, columnHeight)
                )
                drawIntoCanvas { canvas ->
                    val paint = Paint().apply {
                        color = Color.Black.toArgb()
                        textSize =
                            if (columnCount > 7) {
                                14.sp.toPx()
                            } else {
                                12.sp.toPx()
                            }
                        textAlign = Paint.Align.CENTER
                    }
                    val textWidth = paint.measureText(day.toString())
                    val textX = currentX + (columnWidth - columnSpacing * 2) / 2
                    val textY =
                        size.height + 20.dp.toPx() // Säädä pystysuuntainen asento palkkien alapuolelle

                    if (drawDate) {
                        // Katkaise päivämäärämerkkijono, jos se on liian pitkä mahtuakseen sarakkeen leveyteen
                        val truncatedDay = if (day.length == 10) {
                            val monthOnly = day.substring(5, 7) // Poimi vain kuukausi
                            val dayOnly = day.substring(8, 10) // Poimi vain päivä
                            "$dayOnly/$monthOnly"
                        } else {
                            day
                        }
                        canvas.nativeCanvas.drawText(
                            truncatedDay,
                            textX,
                            textY,
                            paint
                        )
                    }

                }
                if (columnCount > 7) {
                    // Käännä drawDate-lippu seuraavaa iteraatiota varten, jos columnCount on suurempi kuin 7
                    drawDate = !drawDate
                }

                currentX += columnWidth + columnSpacing
            }
        }
    }
}