package com.example.coolbox_mobiiliprojekti_app.database

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase


@Entity(tableName = "access_tokens")
data class AccessToken(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val accessToken: String
)

@Dao
abstract class AccessTokenDao {
    @Insert
    abstract suspend fun insertToken(token: AccessToken)

    // Change order from query result, just in case so it selects the most recent token.
    @Query("SELECT * FROM access_tokens ORDER BY id DESC LIMIT 1")
    abstract suspend fun getAccessToken(): AccessToken?

    @Query("DELETE FROM access_tokens")
    abstract suspend fun clearAccessToken()
}

@Database(
    entities = [AccessToken::class],
    version = 1,
    exportSchema = false
)
abstract class AccountDatabase : RoomDatabase() {
    abstract fun accessTokenDao(): AccessTokenDao
}
