package com.example.coolbox_mobiiliprojekti_app.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coolbox_mobiiliprojekti_app.api.productionApiService
import com.example.coolbox_mobiiliprojekti_app.model.ProductionChartState
import com.example.coolbox_mobiiliprojekti_app.model.ProductionStatsResponse
import com.example.coolbox_mobiiliprojekti_app.model.SolarStatsResponse
import com.example.coolbox_mobiiliprojekti_app.model.WindStatsResponse
import com.example.coolbox_mobiiliprojekti_app.view.ProductionTypeInterval
import com.example.coolbox_mobiiliprojekti_app.view.TimeInterval
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductionViewModel(

) : ViewModel() {

    // Sisäinen muuttuja näkymän tilan seurantaan
    private val _productionChartState = mutableStateOf(ProductionChartState())
    val productionChartState: State<ProductionChartState> = _productionChartState

    // Tuottodataa seuraava tilamuuttuja
    var productionStatsData by mutableStateOf<Map<String, Float>?>(null)
        private set

    // Refreshaukseen liittyvät apumuuttujat:
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    // Haetaan dataa valitun aikavälin perusteella
    fun fetchTotalProductionData(interval: TimeInterval, date: Any) {
        viewModelScope.launch {
            try {
                _productionChartState.value = _productionChartState.value.copy(loading = true)
                _isLoading.value = true
                when (interval) {
                    TimeInterval.HOURS -> {
                        fetchHourlyProductionsData(date.toString())
                    }
                    TimeInterval.DAYS -> {
                        fetchDailyProductionsData(date.toString())
                    }
                    TimeInterval.WEEKS -> {
                        fetchWeeklyProductionsData(date.toString())
                    }
                    TimeInterval.MONTHS -> {
                        fetchMonthlyProductionsData(date.toString())
                    }
                    TimeInterval.MAIN -> {
                        fetch7DayProductionData(date.toString())
                    }
                }
            } catch (e: Exception) {
                Log.d("Error", e.toString())
            } finally {
                _productionChartState.value = _productionChartState.value.copy(loading = false)
                _isLoading.value = false
            }
        }
    }

    // Funktiot päivittäisen Tuottodatan hakemiseen
    private suspend fun fetchDailyProductionsData(date: String) {
        Log.d("Dorian", "fetchDailyProductionsData is called")

        val response = productionApiService.getDailyProductionsData(date)
        handleProductionStatsResponse(response)
    }

    // Funktiot viikoittaisen Tuottodatan hakemiseen
    private suspend fun fetchWeeklyProductionsData(date: String) {
        val response = productionApiService.getWeeklyProductionsData(date)
        handleProductionStatsResponse(response)
    }

    // Funktiot tunneittaisen Tuottodatan hakemiseen
    private suspend fun fetchHourlyProductionsData(date: String) {
        val response = productionApiService.getHourlyProductionsData(date)
        handleProductionStatsResponse(response)
    }

    // Funktio kuukausittaisen Tuottodatan hakemiseen
    private suspend fun fetchMonthlyProductionsData(date: String) {
        val response = productionApiService.getMonthlyProductionsData(date)
        handleProductionStatsResponse(response)
    }

    // Funktio 7 päivän kokonaistuottodatan hakemiseen
    private suspend fun fetch7DayProductionData(date: String) {
        val response = productionApiService.getSevenDayProductionsData(date)
        handleProductionStatsResponse(response)
    }

    // Käsittelee Tuottotilastojen vastauksen ja päivittää Tuottodatan
    private fun handleProductionStatsResponse(response: ProductionStatsResponse) {
        if (response.data.all { it.date != null }) {
            productionStatsData = response.data.associate { it.date.toString() to it.totalKwh }
        }
        else if (response.data.all { it.hour != null }) {
            productionStatsData = response.data.associate { it.hour.toString() to it.totalKwh }
        }
        else if (response.data.all { it.day != null }) {
            productionStatsData = response.data.associate { it.day.toString() to it.totalKwh }
        }
        else if (response.data.all { it.month != null }) {
            productionStatsData = response.data.associate { it.month.toString() to it.totalKwh }
        }
        else {
            Log.d(
                "Error",
                "handleProductionStatsResponse didn't receive the right data, suspicious of a name change :("
            )
        }
    }

    // Tuulidataa seuraava tilamuuttuja
    var windStatsData by mutableStateOf<Map<String, Float>?>(null)
        private set
    
    // Haetaan dataa valitun aikavälin perusteella
    fun fetchWindData(interval: TimeInterval, date: Any) {
        viewModelScope.launch {
            try {
                _productionChartState.value = _productionChartState.value.copy(loading = true)
                when (interval) {
                    TimeInterval.HOURS -> {
                        fetchHourlyWindsData(date.toString())
                    }
                    TimeInterval.DAYS -> {
                        fetchDailyWindData(date.toString())
                    }
                    TimeInterval.WEEKS -> {
                        fetchWeeklyWindsData(date.toString())
                    }
                    TimeInterval.MONTHS -> {
                        fetchMonthlyWindsData(date.toString())
                    }
                    TimeInterval.MAIN -> {
                        fetch7DayWindData(date.toString())
                    }
                }
            } catch (e: Exception) {
                Log.d("Error", e.toString())
            } finally {
                _productionChartState.value = _productionChartState.value.copy(loading = false)
            }
        }
    }

    // Funktiot päivittäisen Tuulidatan hakemiseen
    private suspend fun fetchDailyWindData(date: String) {
        Log.d("Dorian", "fetchDailyWindData is called")
        val response = productionApiService.getDailyWindProductionsData(date)

        handleWindStatsResponse(response)
    }

    // Funktiot viikoittaisen Tuulidatan hakemiseen
    private suspend fun fetchWeeklyWindsData(date: String) {
        val response = productionApiService.getWeeklyWindProductionsData(date)
        handleWindStatsResponse(response)
    }

    // Funktiot tunneittaisen Tuulidatan hakemiseen
    private suspend fun fetchHourlyWindsData(date: String) {
        val response = productionApiService.getHourlyWindProductionsData(date)
        handleWindStatsResponse(response)
    }

    // Funktio kuukausittaisen Tuulidatan hakemiseen
    private suspend fun fetchMonthlyWindsData(date: String) {
        val response = productionApiService.getMonthlyWindProductionsData(date)
        handleWindStatsResponse(response)
    }

    // Funktio 7 päivän kokonaisTuulidatan hakemiseen
    private suspend fun fetch7DayWindData(date: String) {
        val response = productionApiService.getMonthlyWindProductionsData(date)

        handleWindStatsResponse(response)
    }

    // Käsittelee Tuulitilastojen vastauksen ja päivittää Tuulidatan
    private fun handleWindStatsResponse(response: WindStatsResponse) {
        if (response.data.all { it.date != null }) {
            windStatsData = response.data.associate { it.date.toString() to it.totalKwh }
        }
        else if (response.data.all { it.hour != null }) {
            windStatsData = response.data.associate { it.hour.toString() to it.totalKwh }
        }
        else if (response.data.all { it.day != null }) {
            windStatsData = response.data.associate { it.day.toString() to it.totalKwh }
        }
        else if (response.data.all { it.month != null }) {
            windStatsData = response.data.associate { it.month.toString() to it.totalKwh }
        }
        else {
            Log.d(
                "Error",
                "handleWindStatsResponse didn't receive the right data, suspicious of a name change :("
            )
        }
    }

    // Muuttuja solar datalle
    var solarStatsData by mutableStateOf<Map<String, Float>?>(null)
        private set

    // Haetaan dataa valitun aikavälin perusteella
    fun fetchSolarData(interval: TimeInterval, date: Any) {
        viewModelScope.launch {
            try {
                _productionChartState.value = _productionChartState.value.copy(loading = true)
                when (interval) {
                    TimeInterval.HOURS -> {
                        fetchHourlySolarData(date.toString())
                    }
                    TimeInterval.DAYS -> {
                        fetchDailyByWeekSolarData(date.toString())
                    }
                    TimeInterval.WEEKS -> {
                        fetchDailyByMonthSolarData(date.toString())
                    }
                    TimeInterval.MONTHS -> {
                        fetchMonthlySolarData(date.toString())
                    }
                    TimeInterval.MAIN -> {
                        fetchSevenDaySolarData(date.toString())
                    }
                }
            } catch (e: Exception) {
                Log.d("Error", e.toString())
            } finally {
                _productionChartState.value = _productionChartState.value.copy(loading = false)
            }
        }
    }

    private suspend fun fetchSevenDaySolarData(date: String) {
        val response = productionApiService.getSevenDaySolarProductionData(date)
        handleSolarStatsResponse(response)
    }

    private suspend fun fetchHourlySolarData(date: String) {
        val response = productionApiService.getHourlySolarProductionData(date)
        handleSolarStatsResponse(response)
    }

    private suspend fun fetchDailyByWeekSolarData(date: String) {
        val response = productionApiService.getDailyByWeekSolarProductionData(date)
        handleSolarStatsResponse(response)
    }

    private suspend fun fetchDailyByMonthSolarData(date: String) {
        val response = productionApiService.getDailyByMonthSolarProductionData(date)
        handleSolarStatsResponse(response)
    }

    private suspend fun fetchMonthlySolarData(date: String) {
        val response = productionApiService.getMonthlySolarProductionData(date)
        handleSolarStatsResponse(response)
    }

    private fun handleSolarStatsResponse(response: SolarStatsResponse) {
        if (response.data.all { it.date != null }) {
            solarStatsData = response.data.associate { it.date to it.totalKwh }
        }
        else if (response.data.all { it.hour != null }) {
            solarStatsData = response.data.associate { it.hour to it.totalKwh }
        }
        else if (response.data.all { it.day != null }) {
            solarStatsData = response.data.associate { it.day to it.totalKwh }
        }
        else if (response.data.all { it.month != null }) {
            solarStatsData = response.data.associate { it.month to it.totalKwh }
        }
        else {
            Log.d(
                "Error",
                "handleSolarStatsResponse didn't receive the right data, suspicious of a name change :("
            )
        }
    }

    val currentProductionType: ProductionTypeInterval = ProductionTypeInterval.Total

    fun fetchData(interval: TimeInterval, date: Any) {
        viewModelScope.launch {
            _productionChartState.value = _productionChartState.value.copy(loading = true)
            try {
                when (currentProductionType) {
                    ProductionTypeInterval.Wind -> fetchWindData(interval, date.toString())
                    ProductionTypeInterval.Total -> fetchTotalProductionData(interval, date.toString()) // Assume this function exists
                    ProductionTypeInterval.Solar -> fetchSolarData(interval, date.toString())
                }
            } catch (e: Exception) {
                Log.d("Error", "Fetching data failed: ${e.message}")
            } finally {
                _productionChartState.value = _productionChartState.value.copy(loading = false)
            }
        }
    }
}