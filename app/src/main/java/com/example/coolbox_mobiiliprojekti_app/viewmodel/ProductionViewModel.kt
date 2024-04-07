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
import com.example.datachartexample2.tests.test3.TimeInterval
import kotlinx.coroutines.launch

class ProductionViewModel : ViewModel() {
    // Privaatti muuttuja näkymän tilan seurantaan
    private val _productionChartState = mutableStateOf(ProductionChartState())
    val productionChartState: State<ProductionChartState> = _productionChartState

    // Kokonaistuottodataa seuraava tilamuuttuja
    var productionStatsData by mutableStateOf<Map<String, Float>?>(null)

    // Haetaan dataa valitun aikavälin perusteella
    fun fetchData(interval: TimeInterval, date: Any) {
        viewModelScope.launch {
            try {
                _productionChartState.value = _productionChartState.value.copy(loading = true)
                if (interval == TimeInterval.DAYS) {
                    fetch7DayProductionData(date.toString())
                }
            }
            catch (e: Exception) {
                Log.d("Error", e.toString())
            }
            finally {
                _productionChartState.value = _productionChartState.value.copy(loading = false)
            }
        }
    }

    // Funktio 7 päivän kokonaistuottodatan hakemiseen
    private suspend fun fetch7DayProductionData(date: String) {
        val response = productionApiService.getSevenDayProductionsData(date)
        handleProductionStatsResponse(response)
    }

    // Käsittelee tuottotilastojen vastauksen ja päivittää kulutusdatan
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
}