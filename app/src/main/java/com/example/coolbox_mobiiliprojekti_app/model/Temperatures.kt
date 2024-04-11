package com.example.coolbox_mobiiliprojekti_app.model

import com.google.gson.annotations.SerializedName

// Tämä luokka määrittelee lämpötilagraafin tilan ja sisältää tiedot latauksen tilasta, mahdollisista virheistä ja akun varaustilasta.
data class TemperaturesChartState(
    // Boolean-arvo, joka ilmaisee, ovatko graafin tiedot parhaillaan latautumassa. Oletusarvo on `false`, eli ei lataudu.
    val loading: Boolean = false,
    // Merkkijono, joka sisältää tiedon mahdollisesta virheestä graafin datan latauksessa. `null` tarkoittaa, ettei virhettä ole.
    val error: String? = null,
)

// Luokka määrittelee lämpötiladataa sisältävän vastauksen rakenteen. Tämä vastaus koostuu listasta, joka voi sisältää mitä tahansa objekteja.
data class TemperaturesStatsResponse(
    // Lista, joka sisältää lämpötiladatan. Jokainen listan alkio voi olla minkä tahansa tyyppinen objekti.
    val data: List<Any>
)

// Luokka määrittelee yksittäisen lämpötila-anturin datan. Sisältää anturin nimen ja lämpötilan arvon.
data class TemperaturesStatsData(
    // Merkkijono, joka kuvaa lämpötila-anturin nimeä.
    val sensor: String,
    // Liukuluku, annotaatio `SerializedName` määrittää, että JSON-vastaavuus on "C". Tämä kenttä kuvaa anturin mittaamaa lämpötilaa celsiusasteina.
    @SerializedName("C")
    val c: Float
)
