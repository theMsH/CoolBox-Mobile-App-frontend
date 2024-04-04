package com.example.coolbox_mobiiliprojekti_app.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coolbox_mobiiliprojekti_app.api.consumptionApiService
import com.example.coolbox_mobiiliprojekti_app.model.ChartState
import com.example.coolbox_mobiiliprojekti_app.model.ConsumptionStatsResponse
import com.example.coolbox_mobiiliprojekti_app.model.TemperatureStatsResponse
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class ConsumptionViewModel : ViewModel() {

    private val _chartState = mutableStateOf(ChartState())
    val chartState: State<ChartState> = _chartState

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
                _chartState.value = _chartState.value.copy(loading = true)

                val response =
                    consumptionApiService.getDailyConsumptionsDataFromLastSevenDaysByHours(
                        formattedDate
                    )
                handleConsumptionStatsResponse(response)
            }
            catch (e: Exception) {
                Log.d("fetchConsumptionStatsFromLastSevenDays Error", e.toString())
            }
            finally {
                _chartState.value = _chartState.value.copy(loading = false)
            }
        }
    }

    // Hae tunnittaiset kulutustiedot annetusta päivämäärästä
    fun fetchHourlyConsumptionsData(date: LocalDate) {
        val formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        viewModelScope.launch {
            try {
                _chartState.value = _chartState.value.copy(loading = true)

                val response = consumptionApiService.getHourlyConsumptionsData(formattedDate)
                handleConsumptionStatsResponse(response)
            }
            catch (e: Exception) {
                Log.d("fetchHourlyConsumptionsData Error", e.toString())
            }
            finally {
                _chartState.value = _chartState.value.copy(loading = false)
            }
        }
    }

    // Hae päivittäiset kulutustilastot annetusta päivämäärästä
    fun fetchDailyConsumptionStats(date: LocalDate) {
        val formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        viewModelScope.launch {
            try {
                _chartState.value = _chartState.value.copy(loading = true)

                val response = consumptionApiService.getDailyConsumptionsData(formattedDate)
                handleConsumptionStatsResponse(response)
            }
            catch (e: Exception) {
                Log.d("fetchDailyConsumptionStats Error", e.toString())
            }
            finally {
                _chartState.value = _chartState.value.copy(loading = false)
            }

        }
    }

    // Hae viikoittaiset kulutustiedot annetusta päivämäärästä
    fun fetchWeeklyConsumptionsData(date: LocalDate) {
        val formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        viewModelScope.launch {
            try {
                _chartState.value = _chartState.value.copy(loading = true)
                val response = consumptionApiService.getWeeklyConsumptionsData(formattedDate)
                handleConsumptionStatsResponse(response)
            }
            catch (e: Exception) {
                Log.d("fetchWeeklyConsumptionsData Error", e.toString())
            }
            finally {
                _chartState.value = _chartState.value.copy(loading = false)
            }
        }
    }

    // Hae tunnittaiset lämpötilatiedot annetusta päivämäärästä
    fun fetchHourlyTemperaturesData(date: LocalDate) {
        val formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        viewModelScope.launch {
            try {
                _chartState.value = _chartState.value.copy(loading = true)
                val response = consumptionApiService.getHourlyTemperaturesData(formattedDate)
                Log.d("Dorian", "fetchHourlyTemperaturesData response $response")
                handleTemperatureStatsResponse(response)
            }
            catch (e: Exception) {
                Log.d("fetchHourlyTemperaturesData Error", e.toString())
            }
            finally {
                _chartState.value = _chartState.value.copy(loading = false)
            }
        }
    }

    // Käsittele kulutustilastojen vastaus
    private fun handleConsumptionStatsResponse(response: ConsumptionStatsResponse) {
        if (response.data.all { it.date != null }) {
            consumptionStatsData = response.data.associate { it.date.toString() to it.totalKwh }
        }
        else if (response.data.all { it.hour != null }) {
            consumptionStatsData = response.data.associate { it.hour.toString() to it.totalKwh }
        }
        else if (response.data.all { it.day != null }) {
            consumptionStatsData = response.data.associate { it.day.toString() to it.totalKwh }
        }
        else {
            Log.d(
                "Error",
                "handleConsumptionStatsResponse didn't receive the right data, suspicious of a name change :("
            )
        }
    }

    // Käsittele lämpötilatilastojen vastaus
    private fun handleTemperatureStatsResponse(response: TemperatureStatsResponse) {
        temperatureStatsData = response.data.associate { it.hour.toString() to it.temperature }
    }
}