package com.example.coolbox_mobiiliprojekti_app.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coolbox_mobiiliprojekti_app.api.batteryApiService
import com.example.coolbox_mobiiliprojekti_app.model.BatteryChartState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BatteryViewModel : ViewModel() {
    // Jäsenmuuttuja:
    private val _batteryChartState = mutableStateOf(BatteryChartState())

    // Getteri jäsenmuuttujan arvolle:
    val batteryChartState: State<BatteryChartState> = _batteryChartState

    // Refreshaukseen liittyvät apumuuttujat:
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        fetchBatteryCharge()
    }

    // Haetaan valitun ajankohdan akunvaraustieto:
    fun fetchBatteryCharge() {
        try {
            viewModelScope.launch {
                _batteryChartState.value = _batteryChartState.value.copy(loading = true)
                _isLoading.value = true
//                delay(2000)
                val response = batteryApiService.getMostRecentValuesFromBattery()
                _batteryChartState.value = _batteryChartState.value.copy(
                    soc = response.currentBatteryStats[0].value
                )
//                _batteryChartState.value = _batteryChartState.value.copy(loading = false)
            }
        } catch (e: Exception) {
            _batteryChartState.value = _batteryChartState.value.copy(error = e.toString())
        } finally {
            _batteryChartState.value = _batteryChartState.value.copy(loading = false)
            _isLoading.value = false
        }
    }

}