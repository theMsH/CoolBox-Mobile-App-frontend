package com.example.coolbox_mobiiliprojekti_app

import android.app.Application
import com.example.coolbox_mobiiliprojekti_app.database.DbProvider


class CustomApp : Application() {
    override fun onCreate() {
        super.onCreate()
        DbProvider.provide(this)
    }
}