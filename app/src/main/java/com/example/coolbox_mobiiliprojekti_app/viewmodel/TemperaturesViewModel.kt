package com.example.coolbox_mobiiliprojekti_app.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coolbox_mobiiliprojekti_app.api.batteryApiService
import com.example.coolbox_mobiiliprojekti_app.api.consumptionApiService
import com.example.coolbox_mobiiliprojekti_app.api.temperaturesApiService
import com.example.coolbox_mobiiliprojekti_app.model.BatteryChartState
import com.example.coolbox_mobiiliprojekti_app.model.ConsumptionStatsResponse
import com.example.coolbox_mobiiliprojekti_app.model.TemperatureStatsResponse
import com.example.coolbox_mobiiliprojekti_app.model.TemperaturesChartState
import com.example.coolbox_mobiiliprojekti_app.model.TemperaturesStatsData
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TemperaturesViewModel : ViewModel() {

    private val _temperaturesChartState = mutableStateOf(TemperaturesChartState())
    val temperaturesChartState: MutableState<TemperaturesChartState> = _temperaturesChartState

    var temperaturesStatsData by mutableStateOf<Map<String, Float>?>(null)
        private set

    var lastFetchTime by mutableStateOf<String?>(null)
        private set

    init {
        // Luokan alustaja, joka kutsuu lämpötilatietojen noutamista palvelimelta.
        fetchTemperaturesData()
    }

    private fun fetchTemperaturesData() {
        // Käynnistää koroutiinin ViewModel-skoopissa.
        viewModelScope.launch {
            try {
                // Asettaa graafin tilan lataavaksi.
                _temperaturesChartState.value = _temperaturesChartState.value.copy(loading = true)
                // Tekee pyynnön palvelimelle ja odottaa vastausta.
                val response = temperaturesApiService.getMostRecentValuesFrom4DifferentTemperatures()

                // Saadaan "Last Updated" arvo, eli milloin data on haettu viimeksi
                lastFetchTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))


                // Tarkistaa, että vastaus sisältää tarvittavan datan.
                if (response.data.size >= 2 && response.data[1] is List<*>) {
                    val temperatureData = mutableListOf<TemperaturesStatsData>()
                    val temperaturesList = response.data[1] as List<Map<String, Any>>
                    // Käsittelee jokaisen anturin datan.
                    temperaturesList.forEach {
                        val fullSensorName = it["sensor"] as String
                        val sensor = fullSensorName.split(" ")[0] // Ottaa vain anturin nimen ensimmäisen sanan.
                        val c = (it["C"] as Double).toFloat() // Muuntaa Double-arvon Float-tyyppiseksi.
                        // Lisää uuden lämpötilatiedon listaan.
                        temperatureData.add(TemperaturesStatsData(sensor, c))
                    }
                    // Tallentaa käsitellyt lämpötilatiedot jäsenmuuttujaan.
                    temperaturesStatsData = temperatureData.associate { it.sensor to it.c }
                    // Lokitetaan käsitelty data.
                    Log.d("Dorian", "Parsed Temperature Data: $temperatureData")
                }
            } catch (e: Exception) {
                // Jos virhe ilmenee, päivitetään graafin tila virhetilaksi ja lokitetaan virhe.
                _temperaturesChartState.value = _temperaturesChartState.value.copy(error = e.toString())
                Log.e("Dorian", "Error in fetching/parsing temperatures", e)
            } finally {
                // Lopuksi asetetaan graafin tila ei-lataavaksi.
                _temperaturesChartState.value = _temperaturesChartState.value.copy(loading = false)
            }
        }
    }

}