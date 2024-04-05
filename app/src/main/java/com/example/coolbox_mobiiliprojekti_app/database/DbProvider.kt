package com.example.coolbox_mobiiliprojekti_app.database

import android.content.Context
import androidx.room.Room


object DbProvider {
    lateinit var db: AccountDatabase

    fun provide(context: Context) {
        db = Room.databaseBuilder(
            context,
            AccountDatabase::class.java,
            "account.db"
        ).build()
    }
}