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

class ConsumptionViewModel : ViewModel() {

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
}