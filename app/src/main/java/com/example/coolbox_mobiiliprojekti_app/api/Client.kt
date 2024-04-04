package com.example.coolbox_mobiiliprojekti_app.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val retrofit = clientCreater()

// Funktio luo ja palauttaa Retrofit-asiakkaan käyttäen annettua perus-URL-osoitetta.
fun clientCreater(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8000/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}