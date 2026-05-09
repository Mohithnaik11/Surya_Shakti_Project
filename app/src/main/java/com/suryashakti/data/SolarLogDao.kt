package com.suryashakti.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.suryashakti.data.SolarLog
import kotlinx.coroutines.flow.Flow

@Dao
interface SolarLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(solarLog: SolarLog)

    @Query("SELECT * FROM solar_logs WHERE date = :date")
    fun getLogByDate(date: String): Flow<SolarLog?>

    @Query("SELECT * FROM solar_logs ORDER BY date DESC LIMIT 90")
    fun getRecentLogs(): Flow<List<SolarLog>>
}
