package com.example.coolbox_mobiiliprojekti_app.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coolbox_mobiiliprojekti_app.api.batteryApiService
import com.example.coolbox_mobiiliprojekti_app.model.BatteryChartState
import kotlinx.coroutines.launch

class BatteryViewModel : ViewModel() {
    // Jäsenmuuttuja:
    private val _batteryChartState = mutableStateOf(BatteryChartState())

    // Getteri jäsenmuuttujan arvolle:
    val batteryChartState: State<BatteryChartState> = _batteryChartState

    init {
        fetchBatteryCharge()
    }

    // Haetaan valitun ajankohdan akunvaraustieto:
    private fun fetchBatteryCharge() {
        try {
            viewModelScope.launch {
                _batteryChartState.value = _batteryChartState.value.copy(loading = true)
                val response = batteryApiService.getMostRecentValuesFromBattery()
                _batteryChartState.value = _batteryChartState.value.copy(
                    soc = response.currentBatteryStats[0].value,
                    temp = response.currentBatteryStats[1].value,
                    voltage = response.currentBatteryStats[2].value
                )
            }
        } catch (e: Exception) {
            _batteryChartState.value = _batteryChartState.value.copy(error = e.toString())
        } finally {
            _batteryChartState.value = _batteryChartState.value.copy(loading = false)
        }
    }

}