package com.example.coolbox_mobiiliprojekti_app.viewmodel
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coolbox_mobiiliprojekti_app.api.consumptionApiService
import com.example.coolbox_mobiiliprojekti_app.model.ConsumptionChartState
import com.example.coolbox_mobiiliprojekti_app.model.ConsumptionStatsResponse
import com.example.coolbox_mobiiliprojekti_app.model.TemperatureStatsResponse
import com.example.coolbox_mobiiliprojekti_app.view.TimeInterval
import kotlinx.coroutines.launch

class ConsumptionViewModel : ViewModel() {

    // Sisäinen muuttuja näkymän tilan seurantaan
    private val _consumptionChartState = mutableStateOf(ConsumptionChartState())
    val consumptionChartState: State<ConsumptionChartState> = _consumptionChartState

    // Kulutusdataa seuraava tilamuuttuja
    var consumptionStatsData by mutableStateOf<Map<String, Float>?>(null)
        private set

    // Lämpötiladataa seuraava tilamuuttuja
    var temperatureStatsData by mutableStateOf<Map<String, Float>?>(null)
        private set

    // Haetaan dataa valitun aikavälin perusteella
    fun consumptionFetchData(interval: TimeInterval, date: Any) {
        viewModelScope.launch {
            try {
                _consumptionChartState.value = _consumptionChartState.value.copy(loading = true)
                when (interval) {
                    TimeInterval.HOURS -> {
                        fetchHourlyConsumptionsData(date.toString())
                        fetchHourlyTemperatureData(date.toString())
                    }
                    TimeInterval.DAYS -> {
                        fetchDailyConsumptionsData(date.toString())
                        fetchDailyTemperatureData(date.toString())
                    }
                    TimeInterval.WEEKS -> {
                        fetchWeeklyConsumptionsData(date.toString())
                        fetchWeeklyTemperatureData(date.toString())
                    }
                    TimeInterval.MONTHS -> {
                        fetchMonthlyConsumptionsData(date.toString())
                        fetchMonthlyTemperatureData(date.toString())
                    }
                    TimeInterval.MAIN -> {
                        fetch7DayConsumptionData(date.toString())
                        fetch7DayTemperatureData(date.toString())
                    }
                }
            } catch (e: Exception) {
                Log.d("Error", e.toString())
            } finally {
                _consumptionChartState.value = _consumptionChartState.value.copy(loading = false)
            }
        }
    }

    // Funktiot päivittäisen kulutusdatan hakemiseen
    private suspend fun fetchDailyConsumptionsData(date: String) {
        val response = consumptionApiService.getDailyConsumptionsData(date)
        handleConsumptionStatsResponse(response)
    }

    // Funktiot viikoittaisen kulutusdatan hakemiseen
    private suspend fun fetchWeeklyConsumptionsData(date: String) {
        val response = consumptionApiService.getWeeklyConsumptionsData(date)
        handleConsumptionStatsResponse(response)
    }

    // Funktiot tunneittaisen kulutusdatan hakemiseen
    private suspend fun fetchHourlyConsumptionsData(date: String) {
        val response = consumptionApiService.getHourlyConsumptionsData(date)
        handleConsumptionStatsResponse(response)
    }

    // Funktiot päivittäisen lämpötiladatan hakemiseen
    private suspend fun fetchDailyTemperatureData(date: String) {
        val response = consumptionApiService.getDailyTemperaturesData(date)
        handleTemperatureStatsResponse(response)
    }

    // Funktiot viikoittaisen lämpötiladatan hakemiseen
    private suspend fun fetchWeeklyTemperatureData(date: String) {
        val response = consumptionApiService.getWeeklyTemperaturesData(date)
        handleTemperatureStatsResponse(response)
    }

    // Funktiot tunneittaisen lämpötiladatan hakemiseen
    private suspend fun fetchHourlyTemperatureData(date: String) {
        val response = consumptionApiService.getHourlyTemperaturesData(date)
        handleTemperatureStatsResponse(response)
    }

    // Funktio kuukausittaisen kulutusdatan hakemiseen
    private suspend fun fetchMonthlyConsumptionsData(date: String) {
        val response = consumptionApiService.getMonthlyConsumptionsData(date)
        handleConsumptionStatsResponse(response)
    }

    // Funktio kuukausittaisen lämpötiladatan hakemiseen
    private suspend fun fetchMonthlyTemperatureData(date: String) {
        val response = consumptionApiService.getMonthlyTemperaturesData(date)
        handleTemperatureStatsResponse(response)
    }
    // Funktio 7 päivän kokonaiskulujen hakemiseen
    private suspend fun fetch7DayConsumptionData(date: String) {
        val response = consumptionApiService.getSevenDayConsumptionsData(date)
        Log.d("Dorian", "response of fetch7Day ConsumptionData $response")
        handleConsumptionStatsResponse(response)
    }
    // Funktio 7 päivän lämpötieto hakemiseen
    private suspend fun fetch7DayTemperatureData(date: String) {
        val response = consumptionApiService.getSevenDayTemperaturesData(date)
        Log.d("Dorian", "response of fetch7Day TemperatureData $response")
        handleTemperatureStatsResponse(response)
    }

    // Käsittelee kulutustilastojen vastauksen ja päivittää kulutusdatan
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
        else if (response.data.all { it.month != null }) {
            consumptionStatsData = response.data.associate { it.month.toString() to it.totalKwh }
        }
        else {
            Log.d(
                "Error",
                "handleConsumptionStatsResponse didn't receive the right data, suspicious of a name change :("
            )
        }
    }

    // Käsittelee lämpötilatilastojen vastauksen ja päivittää lämpötiladatan
    private fun handleTemperatureStatsResponse(response: TemperatureStatsResponse) {
        if (response.data.all { it.date != null }) {
            temperatureStatsData = response.data.associate { it.date.toString() to it.temperature }
        }
        else if (response.data.all { it.hour != null }) {
            temperatureStatsData = response.data.associate { it.hour.toString() to it.temperature }
        }
        else if (response.data.all { it.day != null }) {
            temperatureStatsData = response.data.associate { it.day.toString() to it.temperature }
        }
        else if (response.data.all { it.month != null }) {
            temperatureStatsData = response.data.associate { it.month.toString() to it.temperature }
        }
        else {
            Log.d(
                "Error",
                "handleConsumptionStatsResponse didn't receive the right data, suspicious of a name change :("
            )
        }
    }
}
